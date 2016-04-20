package bar;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.AsyncTask;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import database.ConjuntoBares;


public class SearchBar extends AppCompatActivity {

    private RecyclerView mList;
    private BarAdapter mAdapter;
    private ProgressBar mProgress;
    private static final int CHOOSE_FILTERS = 1;
    private List<Bar> mBares;
    private boolean isNewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNewList = true;
        setContentView(R.layout.activity_search);
        setTitle(R.string.titulo_bares);
        mList = (RecyclerView) findViewById(R.id.recyclerlist);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mProgress = (ProgressBar) findViewById(R.id.progressbar);
        mProgress.setVisibility(View.VISIBLE);
        handleIntent(getIntent());
        Log.e("onc", "create");
        ArrayList<String> parametros = new ArrayList<String>();
        parametros.add("pepe");
        Log.e("onc",parametros.get(0));
        new getListaBares().execute(parametros);
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
                isNewList = true;
                List<Bar> aux = ConjuntoBares.getInstance().getLocalBarList();
                mBares = aux;
                if (mAdapter == null) {
                    mAdapter = new BarAdapter(aux);
                    mList.setAdapter(mAdapter);
                } else {
                    mAdapter.setBares(aux);
                    mAdapter.notifyDataSetChanged();
                }
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

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            isNewList = true;
            String query = intent.getStringExtra(SearchManager.QUERY);
            List<Bar> bares = ConjuntoBares.getInstance().getBar(query);
            mBares = bares;
            if (mAdapter == null) {
                mAdapter = new BarAdapter(bares);
                mList.setAdapter(mAdapter);
            } else {
                mAdapter.setBares(bares);
                mAdapter.notifyDataSetChanged();
            }
        }
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.filterIcon) {
            getFilters();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getFilters() {
        Log.d("hi" + isNewList, "adf"+ isNewList);
        Intent intent = FiltersActivity.newIntent(this, isNewList);
        startActivityForResult(intent, CHOOSE_FILTERS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHOOSE_FILTERS) {
            isNewList = false;
            if (resultCode == RESULT_OK) {
                HashMap<String, String> map = FiltersActivity.whatFiltersWhereSelected(data);
                //Log.e("edad",map.get("Edad"));
                ArrayList<String> parametros = new ArrayList<String>();
                parametros.add("filtro");
                parametros.add(map.get("Musica"));
                parametros.add(map.get("Edad"));
                parametros.add(map.get("HoraCierre"));
                parametros.add(map.get("HoraApertura"));

                new getListaBares().execute(parametros);
                //List<Bar> baresFiltrados = filtrar(map.get("Musica"),
                  //      map.get("Edad"), map.get("HoraCierre"), map.get("HoraApertura"));
                /*new getListaBares.execute();
                mAdapter.setBares();
                mAdapter.notifyDataSetChanged();*/
            }
        }
    }
/*
    // TODO: mejorar este metodo
    public List<Bar> filtrar(String musica, String edad, String horaCierre, String horaApertura){
        List<Bar> aux = new ArrayList<>();
        try {
            Log.e("musicaElegida",musica);
            aux = ConjuntoBares.getInstance().filtrarBares(musica, edad, horaCierre, horaApertura);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
*/

    private class getListaBares extends AsyncTask<ArrayList<String>, Void, List<Bar>> {
        /*private String musica;
        private String edad;
        private String hCierre;
        private String hApertura;
        private Boolean filtro;

        public getListaBares(Boolean filtro,String musica, String edad, String hCierre, String hApertura){
            this.musica=musica;
            this.edad=edad;
            this.hCierre=hCierre;
            this.hApertura=hApertura;
            this.filtro=filtro;
        }*/
        @Override
        protected List<Bar> doInBackground(ArrayList<String>... parametros) {
            Log.e("paramsBack",parametros[1].toString());
            if(parametros[0].toString()=="filtro") {
                List<Bar> aux = new ArrayList<>();
                try {
                    //Log.e("musicaElegida", musica);
                    aux = ConjuntoBares.getInstance().filtrarBares(parametros[1].toString(),parametros[2].toString(),parametros[3].toString(),parametros[4].toString());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return aux;
            }else {
                List<Bar> aux = new ArrayList<>();
                try {
                    aux = ConjuntoBares.getInstance().getBares();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return aux;
            }
        }

        @Override
        protected void onPostExecute(List<Bar> result) {
            mBares = result;
            mProgress.setVisibility(View.GONE);
            if (mAdapter == null) {
                mAdapter = new BarAdapter(result);
                mList.setAdapter(mAdapter);
            } else {
                mAdapter.setBares(result);
                mAdapter.notifyDataSetChanged();
            }
        }

       /* public List<Bar> filtrar(String musica, String edad, String horaCierre, String horaApertura) {
            List<Bar> aux = new ArrayList<>();
            try {
                Log.e("musicaElegida", musica);
                aux = ConjuntoBares.getInstance().filtrarBares(musica, edad, horaCierre, horaApertura);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return aux;
        }*/

    }


    private class BarHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mNombre;
        public ImageView mImagen;

        public BarHolder(View item) {
            super(item);
            mNombre = (TextView) item.findViewById(R.id.list_nombre_bar);
            mImagen = (ImageView) item.findViewById(R.id.list_imagen_bar);
            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            startActivity(BarActivity.newIntent(SearchBar.this, mNombre.getText().toString()));
        }
    }

    private static final String baseUrl = "http://ps1516.ddns.net/images";

    private class BarAdapter extends RecyclerView.Adapter<BarHolder> {
        private List<Bar> bares;

        public BarAdapter(List<Bar> bares) {
            this.bares = bares;
        }

        @Override
        public BarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(SearchBar.this);
            // TODO: crear un layout para mostrar tambein una imagen del bar junto al nombre
            View view = li.inflate(R.layout.bar_list_item, parent, false);
            return new BarHolder(view);
        }

        @Override
        public void onBindViewHolder(BarHolder holder, int position) {
            Bar b = bares.get(position);
            holder.mNombre.setText(b.getNombre());
            Picasso.with(SearchBar.this).load(baseUrl + b.getPrincipal()).into(holder.mImagen);
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