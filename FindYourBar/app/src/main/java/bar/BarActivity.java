package bar;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import sliderTab.SlidingTabLayout;
import sliderTab.ViewPagerAdapter;


public class BarActivity extends ActionBarActivity {
    private static final String BAR_ELEGIDO = "com.findyourbar.bar_elegido";

    // Declaring Your View and Variables
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Información","Contacto","Eventos"};
    int Numboftabs =3;

    static String nombreBarElegido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);

        nombreBarElegido = getIntent().getCharSequenceExtra(BAR_ELEGIDO).toString();
        Bar bar = getNombreBar();
        setTitle(bar.getNombre());

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.sliderTabs);
        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
    }

    /**
     * Crea un nuevo intent con la informacion necesaria para el comienzo de esta actividad
     * @param context contexto
     * @param barElegido nombre del bar elegido
     * @return un intent con la informacion sobre el bar que se ha elegido
     */
    public static Intent newIntent(Context context, String barElegido) {
        Intent i = new Intent(context, BarActivity.class);
        i.putExtra(BAR_ELEGIDO, barElegido);
        return i;
    }

    public static Bar getNombreBar() {
        return ConjuntoBares.getInstance().getBar(nombreBarElegido);
    }
}


