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
        Log.e("onc", parametros.get(0));
        new getListaBares().execute();
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
        Log.d("hi" + isNewList, "adf" + isNewList);
        Intent intent = FiltersActivity.newIntent(this, isNewList);
        startActivityForResult(intent, CHOOSE_FILTERS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHOOSE_FILTERS) {
            isNewList = false;
            if (resultCode == RESULT_OK) {
                HashMap<String, String> map = FiltersActivity.whatFiltersWhereSelected(data);
                String[] p = new String[]{map.get("Musica"),map.get("Edad"),map.get("HoraCierre"),map.get("HoraApertura")};
                new getBaresFiltrados().execute(p);
            }
        }
    }

    private class getListaBares extends AsyncTask<Void, Void, List<Bar>> {

        @Override
        protected List<Bar> doInBackground(Void... params) {
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
    }

    private class getBaresFiltrados extends AsyncTask<String, Void, List<Bar>> {

        @Override
        protected List<Bar> doInBackground(String... parametros) {
                List<Bar> aux = new ArrayList<>();
                try {
                    aux = ConjuntoBares.getInstance().filtrarBares(parametros[0], parametros[1], parametros[2], parametros[3]);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return aux;
        }

        @Override
        protected void onPostExecute(List<Bar> result) {
            mBares = result;
            mProgress.setVisibility(View.GONE);
            mAdapter.setBares(result);
            mAdapter.notifyDataSetChanged();
        }

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