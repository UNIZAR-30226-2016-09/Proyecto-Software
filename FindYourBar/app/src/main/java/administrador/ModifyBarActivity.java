package administrador;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bar.Bar;
import bar.BarActivity;
import bar.R;
import database.ConjuntoBares;


public class ModifyBarActivity extends AppCompatActivity {

    private InformationAdminTab mInformation;
    private ContactMapAdminTab mContactMap;
    private EventAdminTab mEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_bar_admin);
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.sliderTabs);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        mInformation = new InformationAdminTab();
        mContactMap = new ContactMapAdminTab();
        mEvent = new EventAdminTab();
        List<Fragment> l = new ArrayList<>();
        l.add(mInformation);
        l.add(mContactMap);
        l.add(mEvent);
        CharSequence[] titles = getResources().getStringArray(R.array.pager_titles);
        pager.setAdapter(new BarActivity.MyAdapter(getSupportFragmentManager(), l, titles));
        pager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crear_bar_admin, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cancelar_creacion_bar) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.crear_bar) {
            Bar bar = new Bar(mInformation.getNombreBar(), "", "", "", "", "", "",
                    new ArrayList<String>(), new ArrayList<String>(), 0, 0, 0, new ArrayList<String>());
            new AnadirBar().execute(bar);
            finish();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public class AnadirBar extends AsyncTask<Bar, Void, Void> {

        @Override
        protected Void doInBackground(Bar... params) {
            try {
                ConjuntoBares.getInstance().addBar(params[0]);
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


