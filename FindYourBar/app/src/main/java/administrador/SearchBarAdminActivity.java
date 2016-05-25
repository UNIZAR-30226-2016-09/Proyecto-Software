package administrador;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import bar.Bar;
import bar.BarActivity;
import bar.R;
import bar.SearchBarActivity;
import database.ConjuntoBares;


/**
 * Pantalla que muestra todos los bares y permite la busqueda y el filtrado de los mismos; permite ademas añadir
 * un nuevo bar
 */
public class SearchBarAdminActivity extends SearchBarActivity implements BarSelectionAdapter.BarHolder.ClickListener {

    private ActionMode mActionMode;
    private ActionMode.Callback mActionModeCallback = new MyActionMode();
    private BarSelectionAdapter mAdapter;

    @Override
    protected void init() {
        mQuery = "";
        mMapFilters = new HashMap<>();
        mMapFilters.put("Musica", "all");
        mMapFilters.put("Edad", "all");
        mMapFilters.put("HoraCierre", "all");
        mMapFilters.put("HoraApertura", "all");
        mIsNewList = true;
        LayoutInflater inflater = getLayoutInflater();
        v = inflater.inflate(R.layout.activity_search_bar_admin, null);
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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_bar);
        v = fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchBarAdminActivity.this, CreateBarActivity.class);
                if (mActionMode != null) {
                    mActionMode.finish();
                }
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Bar> baresFiltrados = filtrar(ConjuntoBares.getInstance().getLocalBarList(), mQuery, mMapFilters.get("Musica"), mMapFilters.get("Edad"),
                mMapFilters.get("HoraCierre"), mMapFilters.get("HoraApertura"));
        updateAdapter(baresFiltrados);
    }

    @Override
    protected void updateAdapter(List<Bar> bares) {
        if (mAdapter == null) {
            mAdapter = new BarSelectionAdapter(bares, this);
            mList.setAdapter(mAdapter);
        } else {
            mAdapter.setBares(bares);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBarClicked(int position) {
        if (mActionMode != null) {
            toggleSelection(position);
        } else {
            startActivity(BarActivity.newIntent(SearchBarAdminActivity.this, mAdapter.getBarName(position)));
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

    /**
     * Clase que representa la barra contextual que se muestra cuando el usuario seleeciona un
     * bar para su edicion o borrado
     */
    private class MyActionMode implements ActionMode.Callback {
        private static final String CONFIRMATION_DIALOG = "CONFIRMATION_DIALOG";

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
                case R.id.edit_bar:
                    Intent i = ModifyBarActivity.newIntent(SearchBarAdminActivity.this,
                            mAdapter.getBarName(mAdapter.getSelectedItems().get(0)));
                    mActionMode.finish();
                    startActivity(i);
                    return true;
                case R.id.delete_bar:
                    ConfirmationDialog confirmationDialog = new ConfirmationDialog();
                    confirmationDialog.setOnYesClickListener(new ConfirmationDialog.OnYesClickListener() {
                        @Override
                        public void onYesClickListener() {
                            borrarBares();
                            mAdapter.removeListBares(mAdapter.getSelectedItems());
                            mActionMode.finish();
                        }
                    });
                    confirmationDialog.show(getSupportFragmentManager(), CONFIRMATION_DIALOG);
                    return true;
                default:
                    return false;
            }
        }

        /**
         * Borra los barres seleecionados por el usuario
         */
        private void borrarBares() {
            List<Integer> aux = mAdapter.getSelectedItems();
            String[] nombreBares = new String[mAdapter.getSelectedItemCount()];
            Log.e("TAG", "borrarBares: " + nombreBares.length);
            for (int i = 0; i < aux.size(); i++) {
                nombreBares[i] = mAdapter.getBarName(aux.get(i));
            }
            new BorrarListaBares().execute(nombreBares);
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAdapter.clearSelection();
            mActionMode = null;
        }
    }

    /**
     * Hilo que borra del servidor los bares seleccionados por el usuario
     */
    public class BorrarListaBares extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                for (String param : params) {
                    ConjuntoBares.getInstance().removeBar(param);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

        }
    }
}