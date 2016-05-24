package pruebas;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import bar.Bar;
import database.ConjuntoBares;

public class Test extends AppCompatActivity {

    private static Test sTest;

    //Test.getInstance().pruebaCrearBar();
    //Test.getInstance().pruebaBorrarBar();

    /**
     * Prueba los diferentes casos de las inserciones de bares
     */
    public void pruebaCrearBar(){
        try {
            List<String> s = new ArrayList<String>(); s.add("url1");
            List<String> e = new ArrayList<String>(); e.add("url2");
            List<String> m = new ArrayList<String>(); m.add("Indie");
            Bar b = new Bar("bar1", "en el centro", "independencia", "976976976", "facebook.com/bar1", "perico@gmail.com", "img", s, e, 21, (float) 4.00, (float) 22.00, m);
            new ComprobarCreado().execute(b);
            Log.d("TEST Crear bar caso1", "CREADO");
        }catch (Throwable ex){
            Log.d("TEST Crear bar caso1", "NO CREADO");
        }
        try {
            List<String> s = new ArrayList<String>(); s.add("url1");
            List<String> e = new ArrayList<String>(); e.add("url2");
            List<String> m = new ArrayList<String>(); m.add("Indie");
            Bar b = new Bar(null, "en el centro", "independencia", "976976976", "facebook.com/bar1", "perico@gmail.com", "img", s, e, 21, (float) 4.00, (float) 22.00, m);
            new ComprobarCreado().execute(b);
            Log.d("TEST Crear bar caso2", "CREADO");
        }catch (Throwable ex){
            Log.d("TEST Crear bar caso2", "NO CREADO");
        }
        try {
            List<String> s = new ArrayList<String>(); s.add("url1");
            List<String> e = new ArrayList<String>(); e.add("url2");
            List<String> m = new ArrayList<String>(); m.add("Indie");
            Bar b = new Bar("bar2", "en el centro", "independencia", "976", "facebook.com/bar1", "perico@gmail.com", "img", s, e, 21, (float) 4.00, (float) 22.00, m);
            new ComprobarCreado().execute(b);
            Log.d("TEST Crear bar caso3", "CREADO");
        }catch (Throwable ex){
            Log.d("TEST Crear bar caso3", "NO CREADO");
        }
        try {
            List<String> s = new ArrayList<String>(); s.add("url1");
            List<String> e = new ArrayList<String>(); e.add("url2");
            List<String> m = new ArrayList<String>(); m.add("Indie");
            Bar b = new Bar("bar3", "en el centro", "independencia", "9769769769", "facebook.com/bar1", "perico@gmail.com", "img", s, e, 21, (float) 4.00, (float) 22.00, m);
            new ComprobarCreado().execute(b);
            Log.d("TEST Crear bar caso4", "CREADO");
        }catch (Throwable ex){
            Log.d("TEST Crear bar caso4", "NO CREADO");
        }
        try {
            List<String> s = new ArrayList<String>(); s.add("url1");
            List<String> e = new ArrayList<String>(); e.add("url2");
            List<String> m = new ArrayList<String>(); m.add("Indie");
            Bar b = new Bar("bar4", "en el centro", "independencia", "aa", "facebook.com/bar1", "perico@gmail.com", "img", s, e, 21, (float) 4.00, (float) 22.00, m);
            new ComprobarCreado().execute(b);
            Log.d("TEST Crear bar caso5", "CREADO");
        }catch (Throwable ex){
            Log.d("TEST Crear bar caso5", "NO CREADO");
        }
        try {
            List<String> s = new ArrayList<String>(); s.add("url1");
            List<String> e = new ArrayList<String>(); e.add("url2");
            List<String> m = new ArrayList<String>(); m.add("aa");
            Bar b = new Bar("bar5", "en el centro", "independencia", "976976976", "facebook.com/bar1", "perico@gmail.com", "img", s, e, 21, (float) 4.00, (float) 22.00, m);
            new ComprobarCreado().execute(b);
            Log.d("TEST Crear bar caso6", "CREADO");
        }catch (Throwable ex) {
            Log.d("TEST Crear bar caso6", "NO CREADO");
        }
    }

    public static Test getInstance() {
        if (sTest == null) {
            sTest = new Test();
        }
        return sTest;
    }

    /**
     * Prueba los diferentes casos del borrado de bares
     */
    public void pruebaBorrarBar() {
        try {
            List<String> s = new ArrayList<String>(); s.add("url1");
            List<String> e = new ArrayList<String>(); e.add("url2");
            List<String> m = new ArrayList<String>(); m.add("Indie");
            Bar b = new Bar("bar1", "en el centro", "independencia", "976976976", "facebook.com/bar1", "perico@gmail.com", "img", s, e, 21, (float) 4.00, (float) 22.00, m);
            new ComprobarBorrado().execute(b);
            Log.d("TEST Borrar bar caso1", "BORRADO");
        } catch (Throwable ex) {
            Log.d("TEST Borrar bar caso1", "NO BORRADO");
        }
        try {
            List<String> s = new ArrayList<String>(); s.add("url1");
            List<String> e = new ArrayList<String>(); e.add("url2");
            List<String> m = new ArrayList<String>(); m.add("Indie");
            Bar b = new Bar(null, "en el centro", "independencia", "976976976", "facebook.com/bar1", "perico@gmail.com", "img", s, e, 21, (float) 4.00, (float) 22.00, m);
            new ComprobarBorrado().execute(b);
            Log.d("TEST Borrar bar caso2", "BORRADO");
        } catch (Throwable ex) {
            Log.d("TEST Borrar bar caso2", "NO BORRADO");
        }
    }

    public class ComprobarCreado extends AsyncTask<Bar, Void, Void> {

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
    }

    public class ComprobarBorrado extends AsyncTask<Bar, Void, Void> {

        private Exception e = null;

        @Override
        protected Void doInBackground(Bar... params) {
            try {
                ConjuntoBares.getInstance().removeBar(params[0].getNombre());
            } catch (IOException e) {
                this.e = e;
            }
            return null;
        }
    }
}
