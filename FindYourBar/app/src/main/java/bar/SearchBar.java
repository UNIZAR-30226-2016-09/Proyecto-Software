package bar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ana on 15/03/2016.
 */

public class SearchBar extends AppCompatActivity {

    private ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mList = (ListView)findViewById(R.id.list);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchIcon:
                //Show side menu
                fillData();
                return true;
            default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    /**
     * Show bars (stored in data base order alphabetically)
     */
    private void fillData() {
        mList.setVisibility(View.VISIBLE);
/*
        // Create an array to specify the fields we want to display in the list (only TITLE)
        ArrayList<String> bar = new ArrayList();
        bar.add("Bar1");
        bar.add("Bar2");

        // Create an array to specify the fields we want to display in the list (only TITLE)
        ArrayList<Integer> barImage = new ArrayList();
/*        ImageView bar1 = (ImageView)findViewById(R.id.image);
        bar1.setImageResource(R.mipmap.drink);

        ImageView bar2 = (ImageView)findViewById(R.id.image);
        bar2.setImageResource(R.mipmap.tool);

        barImage.add(R.mipmap.drink);
        barImage.add(R.mipmap.tool);

        // Now create an array adapter and set it to display using our row
        ArrayAdapter<String> bars =
                new ArrayAdapter(this, R.layout.activity_search, R.id.text, bar);
        ArrayAdapter<Integer> barsImage =
                new ArrayAdapter(this, R.layout.activity_search, R.id.image, barImage);
        mList.setAdapter(bars);
        mList.setAdapter(barsImage);*/

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
