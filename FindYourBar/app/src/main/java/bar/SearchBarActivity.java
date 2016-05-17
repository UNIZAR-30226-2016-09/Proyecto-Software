package bar;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import database.ConjuntoBares;

/**
 * Muestra todos los bares y permite la busqueda y el filtrado de los mismos
 */
public class SearchBarActivity extends AppCompatActivity {

    protected static final int CHOOSE_FILTERS = 1;
    protected RecyclerView mList;
    protected BarAdapter mAdapter;
    protected ProgressBar mProgress;
    protected List<Bar> mBares;
    protected boolean mIsNewList;
    protected View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    /**
     * Inicializa la actividad
     */
    protected void init() {
        mIsNewList = true;
        LayoutInflater inflater = getLayoutInflater();
        v = inflater.inflate(R.layout.activity_search, null);
        setContentView(v);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setTitle(R.string.titulo_bares);
        mList = (RecyclerView) findViewById(R.id.recyclerlist);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.setItemAnimator(new DefaultItemAnimator());
        mProgress = (ProgressBar) findViewById(R.id.progressbar);
        mProgress.setVisibility(View.VISIBLE);
        //handleIntent(getIntent());
        new DescargarListaBares().execute();
    }

    protected void updateAdapter(List<Bar> bares) {
        if (mAdapter == null) {
            mAdapter = new BarAdapter(bares, SearchBarActivity.this);
            mList.setAdapter(mAdapter);
        } else {
            mAdapter.setBares(bares);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.searchIcon);
        MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mIsNewList = true;
                List<Bar> aux = ConjuntoBares.getInstance().getLocalBarList();
                mBares = aux;
                updateAdapter(aux);
                return true;
            }
        });
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    /**
     * Se encarga de procesar el intent de la busquda
     *
     * @param intent intent
     */
    protected void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mIsNewList = true;
            String query = intent.getStringExtra(SearchManager.QUERY);
            List<Bar> bares = ConjuntoBares.getInstance().getBar(query);
            mBares = bares;
            updateAdapter(bares);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.filterIcon) {
            getFilters();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Crea la actividad FiltersActivity para coger los filtros que el usuario elija
     */
    public void getFilters() {
        Intent intent = FiltersActivity.newIntent(this, mIsNewList);
        startActivityForResult(intent, CHOOSE_FILTERS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHOOSE_FILTERS) {
            mIsNewList = false;
            if (resultCode == RESULT_OK) {
                HashMap<String, String> map = FiltersActivity.whatFiltersWhereSelected(data);
                //String[] p = new String[]{map.get("Musica"), map.get("Edad"), map.get("HoraCierre"),
                //        map.get("HoraApertura")};
                //new DescargarBaresFiltrados().execute(p);
                List<Bar> baresFiltrados = filtrar(mBares, map.get("Musica"), map.get("Edad"),
                        map.get("HoraCierre"), map.get("HoraApertura"));
                updateAdapter(baresFiltrados);
            }
        }
    }

    /**
     * Descarga la lista de bares de la base de datos remota y los muestra
     */
    public class DescargarListaBares extends AsyncTask<Void, Void, List<Bar>> {

        private Exception e = null;

        public DescargarListaBares() {

        }

        @Override
        protected List<Bar> doInBackground(Void... params) {
            List<Bar> aux = new ArrayList<>();
            try {
                aux = ConjuntoBares.getInstance().getBares();
            } catch (IOException e) {
                this.e = e;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return aux;
        }

        @Override
        protected void onPostExecute(List<Bar> result) {
            mBares = result;
            mProgress.setVisibility(View.GONE);
            // TODO: usar instance of para distinguir de que tipo de excepcion se trata
            if (e != null) {
                Snackbar.make(v, R.string.error_conexion, Snackbar.LENGTH_INDEFINITE).
                        setAction(R.string.reintentar, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mProgress.setVisibility(View.VISIBLE);
                                new DescargarListaBares().execute();
                            }
                        }).show();
            } else {
                updateAdapter(result);
            }
        }
    }

    // TODO: reutilizar el codigo de asyntask DescargarListaBares mejorando diseño, metiendolo en su propio ficheor java

    /**
     * Descarga la lista de bares filtrados de la base de datos remota y los muestra
     */
    protected class DescargarBaresFiltrados extends AsyncTask<String, Void, List<Bar>> {

        private Exception e = null;


        @Override
        protected List<Bar> doInBackground(String... parametros) {
            List<Bar> aux = new ArrayList<>();
            try {
                aux = ConjuntoBares.getInstance().filtrarBares(parametros[0], parametros[1],
                        parametros[2], parametros[3]);
            } catch (IOException e) {
                this.e = e;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return aux;
        }

        @Override
        protected void onPostExecute(List<Bar> result) {
            mBares = result;
            mProgress.setVisibility(View.GONE);

            if (e != null) {
                Snackbar.make(mProgress, R.string.error_conexion, Snackbar.LENGTH_INDEFINITE).
                        setAction(R.string.reintentar, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mProgress.setVisibility(View.VISIBLE);
                                new DescargarListaBares().execute();
                            }
                        }).show();
            } else {
                updateAdapter(result);
            }

        }
    }


    public static List<Bar> filtrar(List<Bar> bares, String musica, String edad, String horaCierre,
                                    String horaApertura) {
        List<Bar> aux = bares;
        List<Bar> aux1 = new ArrayList<>();
        if (!musica.equals("all")) {
            for (Bar b : aux) {
                if (b.hasMusicGenre(musica)) {
                    aux1.add(b);
                }
            }
            aux = aux1;
            aux1 = new ArrayList<>();
        }
        if (!edad.equals("all")) {
            int edadI = Integer.parseInt(edad);
            for (Bar b : aux) {
                if (b.getEdad() >= edadI) {
                    aux1.add(b);

                }
            }
            aux = aux1;
            aux1 = new ArrayList<>();
        }

        if (!horaCierre.equals("all")) {
            float hc = Float.parseFloat(horaCierre);
            for (Bar b : aux) {
                if (b.getHoraCierre() >= hc) {
                    aux1.add(b);
                }
            }
            aux = aux1;
            aux1 = new ArrayList<>();
        }

        if (!horaApertura.equals("all")) {
            float ha = Float.parseFloat(horaApertura);
            for (Bar b : aux) {
                if (ha == 0) {
                    if ((b.getHoraApertura() <= 24 && b.getHoraApertura() > 18) || b.getHoraApertura() == 0) {
                        aux1.add(b);
                    }
                } else {
                    if (b.getHoraApertura() <= ha && b.getHoraApertura() > 18) {
                        aux1.add(b);
                    }
                }

            }
            aux = aux1;
        }

        return aux;
    }

    public static class BarHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mNombre;
        public ImageView mImagen;
        public Context mContext;

        public BarHolder(View item, Context context) {
            super(item);
            mNombre = (TextView) item.findViewById(R.id.list_nombre_bar);
            mImagen = (ImageView) item.findViewById(R.id.list_imagen_bar);
            item.setOnClickListener(this);
            mContext = context;
        }

        @Override
        public void onClick(View v) {
            mContext.startActivity(BarActivity.newIntent(mContext, mNombre.getText().toString()));
        }
    }

    public static class BarAdapter extends RecyclerView.Adapter<BarHolder> {
        private List<Bar> bares;
        private Context mContext;

        public BarAdapter(List<Bar> bares, Context context) {
            this.bares = bares;
            mContext = context;
        }

        @Override
        public BarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(mContext);
            View view = li.inflate(R.layout.bar_list_item, parent, false);
            return new BarHolder(view, mContext);
        }

        @Override
        public void onBindViewHolder(BarHolder holder, int position) {
            Bar b = bares.get(position);
            holder.mNombre.setText(b.getNombre());
            if (!b.getPrincipal().isEmpty()) {
                Picasso.with(mContext).load(b.getPrincipal()).into(holder.mImagen);
            } else {
                holder.mImagen.setImageDrawable(null);
            }
        }

        @Override
        public int getItemCount() {
            return bares.size();
        }

        public void setBares(List<Bar> bares) {
            this.bares = bares;
        }
    }
}