package bar;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


/**
 * Created by Ana on 15/03/2016.
 */

public class SearchBar extends AppCompatActivity {

    private ListView mList;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mList = (ListView)findViewById(R.id.list);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_name:
                dialog = new Dialog(this);
                //Open dialog to allow filter by name
                dialog.setTitle(R.string.menu_name);
                dialog.setContentView(R.layout.dialog_namemenu);
                dialog.show();
                return true;
            case R.id.nameAcept:
                dialog.dismiss();
            case R.id.menu_open:
                dialog = new Dialog(this);
                //Open dialog to allow filter by open hour
                dialog.setTitle(R.string.menu_open);
                dialog.setContentView(R.layout.dialog_openmenu);
                dialog.show();
                return true;
            case R.id.menu_close:
                dialog = new Dialog(this);
                //Open dialog to allow filter by close hour
                dialog.setTitle(R.string.menu_close);
                dialog.setContentView(R.layout.dialog_closemenu);
                dialog.show();
                return true;
            case R.id.menu_music:
                dialog = new Dialog(this);
                //Open dialog to allow filter by music genre
                dialog.setTitle(R.string.menu_music);
                dialog.setContentView(R.layout.dialog_musicmenu);
                dialog.show();
                return true;
            case R.id.menu_age:
                dialog = new Dialog(this);
                //Open dialog to allow filter by legal age to access
                dialog.setTitle(R.string.menu_age);
                dialog.setContentView(R.layout.dialog_agemenu);
                dialog.show();
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
     * Show bars (stored in data base order alphabetically)
     */
    private void fillData() {
        mList.setVisibility(View.VISIBLE);

        final String[] web = {
                "Bar1",
                "Bar2",
                "Bar3"
        } ;
        Integer[] imageId = {
                R.mipmap.drink,
                R.mipmap.drink,
                R.mipmap.drink
        };

        CustomList adapter = new
                CustomList(SearchBar.this, web, imageId);
        mList = (ListView)findViewById(R.id.list);
        mList.setAdapter(adapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(SearchBar.this, "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();

            }
        });
    }
}
