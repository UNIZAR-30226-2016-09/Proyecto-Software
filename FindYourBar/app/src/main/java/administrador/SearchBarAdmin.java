package administrador;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bar.Bar;
import bar.BarActivity;
import bar.FiltersActivity;
import bar.R;
import database.ConjuntoBares;


public class SearchBarAdmin extends AppCompatActivity implements BarSelectionAdapter.BarHolder.ClickListener {

    private RecyclerView mList;
    private BarSelectionAdapter mAdapter;
    private ProgressBar mProgress;
    private static final int CHOOSE_FILTERS = 1;
    private List<Bar> mBares;
    private boolean isNewList;
    private ActionMode mActionMode;
    private ActionMode.Callback mActionModeCallback = new MyActionMode();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNewList = true;
        setContentView(R.layout.activity_search);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setTitle(R.string.titulo_bares);
        mList = (RecyclerView) findViewById(R.id.recyclerlist);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.setItemAnimator(new DefaultItemAnimator());
        mProgress = (ProgressBar) findViewById(R.id.progressbar);
        mProgress.setVisibility(View.VISIBLE);
        handleIntent(getIntent());
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
                    mAdapter = new BarSelectionAdapter(aux, SearchBarAdmin.this);
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
                mAdapter = new BarSelectionAdapter(bares, this);
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
                String[] p = new String[]{map.get("Musica"), map.get("Edad"), map.get("HoraCierre"), map.get("HoraApertura")};
                new getBaresFiltrados().execute(p);
            }
        }
    }

    @Override
    public void onBarClicked(int position) {
        if (mActionMode != null) {
            toggleSelection(position);
        } else {
            startActivity(BarActivity.newIntent(SearchBarAdmin.this, mAdapter.getBarName(position)));
        }
    }

    @Override
    public boolean onBarLongClicked(int position) {
        if (mActionMode == null) {
            mActionMode = startSupportActionMode(mActionModeCallback);
        }

        toggleSelection(position);

        return true;

    }

    private void toggleSelection(int position) {
        mAdapter.toggleSelection(position);
        int count = mAdapter.getSelectedItemCount();
        if (count == 0) {
            mActionMode.finish();
        } else {
            mActionMode.getMenu().findItem(R.id.edit_bar).setVisible(count == 1);
            mActionMode.setTitle(String.valueOf(count));
            mActionMode.invalidate();

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
                mAdapter = new BarSelectionAdapter(result, SearchBarAdmin.this);
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

    private class MyActionMode implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                // TODO: añadir pantalla edicion bar
                case R.id.edit_bar:
                    return true;
                // TODO: borrar los bares de la base de datos remota
                case R.id.delete_bar:
                    mAdapter.removeListBares(mAdapter.getSelectedItems());
                    mActionMode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAdapter.clearSelection();
            mActionMode = null;
        }
    }
}