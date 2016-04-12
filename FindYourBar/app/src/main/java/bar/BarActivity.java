package bar;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import sliderTab.InformationTab;
import sliderTab.SlidingTabLayout;
import sliderTab.ViewPagerAdapter;


public class BarActivity extends ActionBarActivity {
    private static final String BAR_ELEGIDO = "com.findyourbar.bar_elegido";

    // Declaring Your View and Variables
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Información","Contacto", "Eventos"};
    int Numboftabs =3;

    static TextView dirBar;
    static TextView tlfBar;
    static TextView emailfBar;
    static TextView fbBar;
    static String nombreBarElegido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);

        dirBar = (TextView) findViewById(R.id.bar_direccion);
        tlfBar = (TextView) findViewById(R.id.bar_telefono);
        emailfBar = (TextView) findViewById(R.id.bar_email);
        fbBar = (TextView) findViewById(R.id.bar_facebook);

        nombreBarElegido = getIntent().getCharSequenceExtra(BAR_ELEGIDO).toString();
        Bar bar = getNombreBar();

        setTitle(bar.getNombre());

     /*   dirBar.setText(bar.getDireccion());
        tlfBar.setText(tlfBar.getText() + " " +  bar.getTelefono());
        emailfBar.setText(emailfBar.getText() + " " + bar.getEmail());
        fbBar.setText(fbBar.getText() + " " + bar.getFacebook());*/

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.sliderTabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.actionbar_background);
            }
        });

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

    public static String getDirBar (){
        return dirBar.getText().toString();
    }

    public static String getEmailBar() {
        return emailfBar.getText().toString();
    }

    public static String getTelefonoBar() {
        return tlfBar.getText().toString();
    }

    public static String getFacebookBar() {
        return fbBar.getText().toString();
    }

    public static Bar getNombreBar() {
        return ConjuntoBares.getInstance().getBar(nombreBarElegido);
    }
}


