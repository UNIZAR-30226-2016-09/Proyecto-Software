package bar;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * TODO: cambiar esta clase para mejorar la reutilizacion de codigo con lo que hhay en SearchBar.java
 */
public class SearchResultsActivity extends AppCompatActivity {
    private RecyclerView mList;
    private BarAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = null;
        setContentView(R.layout.activity_search_results);
        mList = (RecyclerView) findViewById(R.id.recyclerlist);
        mList.setLayoutManager(new LinearLayoutManager(this));
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            setTitle("Resultados de \"" + query + "\"");
            if (mAdapter == null) {
                mAdapter = new BarAdapter(ConjuntoBares.getInstance().getBar(query));
                mList.setAdapter(mAdapter);
            } else {
                mAdapter.setBares(ConjuntoBares.getInstance().getBar(query));
                mAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchIcon).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.filterIcon) {
            Intent intent = new Intent(this, FiltersActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private class BarHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mNombre;

        public BarHolder(View item) {
            super(item);
            mNombre = (TextView) item.findViewById(R.id.list_nombre_bar);
            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            startActivity(BarActivity.newIntent(SearchResultsActivity.this, mNombre.getText().toString()));
        }
    }

    private class BarAdapter extends RecyclerView.Adapter<BarHolder> {
        private List<Bar> bares;

        public BarAdapter(List<Bar> bares) {
            this.bares = bares;
        }

        @Override
        public BarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(SearchResultsActivity.this);
            // TODO: crear un layout para mostrar tambein una imagen del bar junto al nombre
            View view = li.inflate(R.layout.bar_list_item, parent, false);
            return new BarHolder(view);
        }

        @Override
        public void onBindViewHolder(BarHolder holder, int position) {
            Bar b = bares.get(position);
            holder.mNombre.setText(b.getNombre());
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
