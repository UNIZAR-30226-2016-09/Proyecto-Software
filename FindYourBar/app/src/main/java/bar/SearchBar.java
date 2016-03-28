package bar;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SearchBar extends AppCompatActivity {

    private RecyclerView mList;
    private BarAdapter mAdapter;
    // Identificadores para los fragmentos
    private static final String menu_name = "MENU_NAME";
    private static final String menu_open = "MENU_OPEN";
    private static final String menu_close = "MENU_CLOSE";
    private static final String menu_music = "MENU_MUSIC";
    private static final String menu_age = "MENU_AGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mList = (RecyclerView) findViewById(R.id.recyclerlist);
        mList.setLayoutManager(new LinearLayoutManager(this));
        fillData();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_name:
                DialogFragment name = new SearchNameDialogFragment();
                name.show(getSupportFragmentManager(), menu_name);
                return true;
            case R.id.menu_open:
                DialogFragment opening = new SearchOpeningDialogFragment();
                opening.show(getSupportFragmentManager(), menu_open);
                return true;
            case R.id.menu_close:
                DialogFragment closing = new SearchClosingDialogFragment();
                closing.show(getSupportFragmentManager(), menu_close);
                return true;
            case R.id.menu_music:
                DialogFragment music = new SearchMusicDialogFragment();
                music.show(getSupportFragmentManager(), menu_music);
                return true;
            case R.id.menu_age:
                DialogFragment age = new SearchAgeDialogFragment();
                age.show(getSupportFragmentManager(), menu_age);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        return true;
    }


    /**
     * Rellena el la lista de bares
     */
    private void fillData() {
        mAdapter = new BarAdapter(ConjuntoBares.getInstance().getBares());
        mList.setAdapter(mAdapter);
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
            startActivity(BarActivity.newIntent(SearchBar.this, mNombre.getText().toString()));
        }
    }

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
        }

        @Override
        public int getItemCount() {
            return bares.size();
        }
    }
}
