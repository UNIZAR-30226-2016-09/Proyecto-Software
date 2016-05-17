package administrador;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bar.Bar;
import bar.BarActivity;
import bar.R;
import database.ConjuntoBares;

public class ModifyBarActivity extends CreateBarActivity {

    protected Bar bar;
    protected static final String NOMBRE_BAR = "NOMBRE_BAR";
    protected InformationModifyAdminTab mInformation;
    protected ContactMapModifyAdminTab mContactMap;
    protected EventModifyAdminTab mEvent;

    @Override
    protected void init() {
        Intent intent = getIntent();
        String nombreBar = intent.getStringExtra(NOMBRE_BAR);
        bar = ConjuntoBares.getInstance().getBarExact(nombreBar);
        setContentView(R.layout.activity_modify_bar_admin);
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.sliderTabs);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        mInformation = new InformationModifyAdminTab();
        mInformation.setBar(bar);
        mContactMap = new ContactMapModifyAdminTab();
        mContactMap.setBar(bar);
        mEvent = new EventModifyAdminTab();
        mEvent.setBar(bar);
        List<Fragment> l = new ArrayList<>();
        l.add(mInformation);
        l.add(mContactMap);
        l.add(mEvent);
        CharSequence[] titles = getResources().getStringArray(R.array.pager_titles);
        pager.setAdapter(new BarActivity.MyAdapter(getSupportFragmentManager(), l, titles));
        pager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(pager);
    }


    public Bar getBar() {
        return bar;
    }

    public static Intent newIntent(Context context, String nombreBar) {
        Intent intent = new Intent(context, ModifyBarActivity.class);
        intent.putExtra(NOMBRE_BAR, nombreBar);
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cancelar_creacion_bar) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.crear_bar) {
            if (mInformation.validarEntrada() & mContactMap.validarEntrada()) {
                List<String> imagenes = mInformation.getListImagenes();
                String imgPrincipal = "";
                List<String> imgSecundarias = new ArrayList<>();
                if (imagenes.size() == 1) {
                    imgPrincipal = imagenes.get(0);
                } else if (imagenes.size() > 1) {
                    imgPrincipal = imagenes.get(0);
                    for (int i = 1; i < imagenes.size(); i++) {
                        imgSecundarias.add(imagenes.get(i));
                    }
                }
                Log.e("TAG", "onOptionsItemSelected: " + imgPrincipal);
                Bar bar = new Bar(mInformation.getNombreBar(), mInformation.getDescripcionBar(),
                        mContactMap.getDireccion(), mContactMap.getPhone(), mContactMap.getEmail(),
                        mContactMap.getFacebook(), imgPrincipal, imgSecundarias, mEvent.getListImagenes(),
                        mInformation.getEdad(), mInformation.getHoraCierre(), mInformation.getHoraApertura(),
                        mInformation.getListMusica());
                if (mInformation.getListMusica()==null) {
                    Log.e("TAG", "onOptionsItemSelected: " + " nulsdakfj45");
                }
                new BorrarListaBares().execute(bar.getNombre());
                new AnadirBar().execute(bar);
            } else {
                Snackbar.make(getCurrentFocus(), R.string.corrija_lor_errores, Snackbar.LENGTH_LONG).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    public class AnadirBar extends AsyncTask<Bar, Void, Void> {

        private Exception e = null;

        @Override
        protected Void doInBackground(Bar... params) {
            try {
                ConjuntoBares.getInstance().addBar(params[0]);
            } catch (IOException e) {
                this.e = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (e != null) {
                Snackbar.make(getCurrentFocus(), R.string.error_conexion, Snackbar.LENGTH_INDEFINITE).
                        setAction(R.string.reintentar, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new AnadirBar().execute();
                            }
                        }).show();
            } else {
                finish();
            }
        }
    }

}
