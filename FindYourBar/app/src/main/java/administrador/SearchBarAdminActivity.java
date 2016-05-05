package administrador;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.List;

import bar.Bar;
import bar.BarActivity;
import bar.R;
import bar.SearchBarActivity;
import database.ConjuntoBares;


public class SearchBarAdminActivity extends SearchBarActivity implements BarSelectionAdapter.BarHolder.ClickListener {

    private ActionMode mActionMode;
    private ActionMode.Callback mActionModeCallback = new MyActionMode();
    private BarSelectionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsNewList = true;
        LayoutInflater inflater = getLayoutInflater();
        v = inflater.inflate(R.layout.activity_search_bar_admin, null);
        setContentView(v);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setTitle(R.string.titulo_bares);
        mList = (RecyclerView) findViewById(R.id.recyclerlist);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_bar);
        v = fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchBarAdminActivity.this, ModifyBarActivity.class);
                startActivity(intent);
            }
        });
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.setItemAnimator(new DefaultItemAnimator());
        mProgress = (ProgressBar) findViewById(R.id.progressbar);
        mProgress.setVisibility(View.VISIBLE);
        handleIntent(getIntent());
        new DescargarListaBares().execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAdapter(ConjuntoBares.getInstance().getLocalBarList());

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
                case R.id.delete_bar:
                    borrarBares();
                    mAdapter.removeListBares(mAdapter.getSelectedItems());
                    mActionMode.finish();
                    return true;
                default:
                    return false;
            }
        }


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