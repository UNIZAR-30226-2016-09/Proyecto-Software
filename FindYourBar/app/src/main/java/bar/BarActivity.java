package bar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import database.ConjuntoBares;

/**
 * Pantalla que muestra la informacion de un bar
 */
public class BarActivity extends AppCompatActivity {
    protected static final String BAR_ELEGIDO = "com.findyourbar.bar_elegido";
    protected static String mNombreBarElegido;

    /**
     * Crea un nuevo intent con la informacion necesaria para el comienzo de esta actividad
     *
     * @param context    contexto
     * @param barElegido nombre del bar elegido
     * @return un intent con la informacion sobre el bar que se ha elegido
     */
    public static Intent newIntent(Context context, String barElegido) {
        Intent i = new Intent(context, BarActivity.class);
        i.putExtra(BAR_ELEGIDO, barElegido);
        return i;
    }

    public static Bar getNombreBar() {
        return ConjuntoBares.getInstance().getBarExact(mNombreBarElegido);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.sliderTabs);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mNombreBarElegido = getIntent().getCharSequenceExtra(BAR_ELEGIDO).toString();
        Bar bar = getNombreBar();
        setTitle(bar.getNombre());

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        List<Fragment> l = new ArrayList<>();
        l.add(new InformationTab());
        l.add(new ContactMapTab());
        l.add(new EventTab());
        CharSequence[] titles = getResources().getStringArray(R.array.pager_titles);
        pager.setAdapter(new MyAdapter(getSupportFragmentManager(), l, titles));
        mTabLayout.setupWithViewPager(pager);
    }


    /**
     * Adapter con los fragments de las pestañas
     */
    public static class MyAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragmentList;
        private CharSequence[] mTitles;

        public MyAdapter(FragmentManager fm, List<Fragment> fragmentList, CharSequence[] titles) {
            super(fm);
            mFragmentList = fragmentList;
            mTitles = titles;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}


