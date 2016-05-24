package pruebas;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import bar.Bar;
import database.ConjuntoBares;

public class Test extends AppCompatActivity {

    private static Test sTest;

    //Test.getInstance().pruebaCrearBar();

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
            Bar b = new Bar("bar5", "en el cent", "independencia", "976976976", "facebook.com/bar1", "perico@gmail.com", "img", s, e, 21, (float) 4.00, (float) 22.00, m);
            new ComprobarCreado().execute(b);
            Log.d("TEST Crear bar caso6", "CREADO");
        }catch (Throwable ex) {
            Log.d("TEST Crear bar caso6", "NO CREADO");
        }
    }

    /**
     * Prueba los diferentes casos de las posibles modificaciones de bares
     */
    public void pruebaModificarBar(){
        try {
            List<String> s = new ArrayList<String>(); s.add("url1");
            List<String> e = new ArrayList<String>(); e.add("url2");
            List<String> m = new ArrayList<String>(); m.add("Indie");
            Bar b = new Bar("bar1", "cerca de todos", "independencia", "976976976", "facebook.com/bar1", "perico@gmail.com", "otrafoto", s, e, 21, (float) 4.00, (float) 22.00, m);
            new ComprobarModificacion().execute(b);
            Log.d("TEST Mod bar caso1", "MODIFICADO");
        }catch (Throwable ex){
            Log.d("TEST Mod bar caso1", "NO MODIFICADO");
        }
        try {
            List<String> s = new ArrayList<String>(); s.add("url1");
            List<String> e = new ArrayList<String>(); e.add("url2");
            List<String> m = new ArrayList<String>(); m.add("Indie");
            Bar b = new Bar(null, "en el centro", "independencia", "976976976", "facebook.com/bar1", "perico@gmail.com", "img", s, e, 21, (float) 4.00, (float) 22.00, m);
            new ComprobarModificacion().execute(b);
            Log.d("TEST Mod bar caso2", "MODIFICADO");
        }catch (Throwable ex){
            Log.d("TEST Mod bar caso2", "NO MODIFICADO");
        }
        try {
            List<String> s = new ArrayList<String>(); s.add("url1");
            List<String> e = new ArrayList<String>(); e.add("url2");
            List<String> m = new ArrayList<String>(); m.add("Indie");
            Bar b = new Bar("bar1", "en el centro", "independencia", "976", "facebook.com/bar1", "perico@gmail.com", "img", s, e, 21, (float) 4.00, (float) 22.00, m);
            new ComprobarModificacion().execute(b);
            Log.d("TEST Mod bar caso3", "MODIFICADO");
        }catch (Throwable ex){
            Log.d("TEST Mod bar caso3", "NO MODIFICADO");
        }
        try {
            List<String> s = new ArrayList<String>(); s.add("url1");
            List<String> e = new ArrayList<String>(); e.add("url2");
            List<String> m = new ArrayList<String>(); m.add("Indie");
            Bar b = new Bar("bar1", "en el centro", "independencia", "9769769769", "facebook.com/bar1", "perico@gmail.com", "img", s, e, 21, (float) 4.00, (float) 22.00, m);
            new ComprobarModificacion().execute(b);
            Log.d("TEST Mod bar caso4", "MODIFICADO");
        }catch (Throwable ex){
            Log.d("TEST Mod bar caso4", "NO MODIFICADO");
        }
        try {
            List<String> s = new ArrayList<String>(); s.add("url1");
            List<String> e = new ArrayList<String>(); e.add("url2");
            List<String> m = new ArrayList<String>(); m.add("Indie");
            Bar b = new Bar("bar1", "en el centro", "independencia", "aa", "facebook.com/bar1", "perico@gmail.com", "img", s, e, 21, (float) 4.00, (float) 22.00, m);
            new ComprobarModificacion().execute(b);
            Log.d("TEST Mod bar caso5", "MODIFICADO");
        }catch (Throwable ex){
            Log.d("TEST Mod bar caso5", "NO MODIFICADO");
        }
        try {
            List<String> s = new ArrayList<String>(); s.add("url1");
            List<String> e = new ArrayList<String>(); e.add("url2");
            List<String> m = new ArrayList<String>(); m.add("aa");
            Bar b = new Bar("bar1", "en el centro", "independencia", "976976976", "facebook.com/bar1", "perico@gmail.com", "img", s, e, 21, (float) 4.00, (float) 22.00, m);
            new ComprobarModificacion().execute(b);
            Log.d("TEST Mod bar caso6", "MODIFICADO");
        }catch (Throwable ex){
            Log.d("TEST Mod bar caso6", "NO MODIFICADO");
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

    /**
     * Realiza una prueba de volumen sobre la base de datos
     */
    public void pruebaVolumen(){
        for (int i=1;i<=1000;i++){
            try {
                List<String> s = new ArrayList<String>(); s.add(null);
                Bar b = new Bar("bar"+i, null, null, null,null,null,null, s, s, 21, (float) 4.00, (float) 22.00, s);
                new ComprobarCreado().execute(b);
                Log.d("TEST Volumen", "CREADO "+ b.getNombre());
            }catch (Throwable ex){
                Log.d("TEST Volumen", "NO CREADO bar"+ i);
            }
        }
    }

    /**
     * Realiza una prueba de sobrecarga para comprobar la maxima longitud posible de los campos de los bares
     */
    public void pruebaSobrecarga(){
        boolean fin = true;
        int i=1;
        int length = 0;
        while (fin && length<100000){
            try {
                length = i*200;
                List<String> s = new ArrayList<String>(); s.add(null);
                Bar b = new Bar("barSobrecarga"+i,random(length),null,null,null,null,null, s, s, 21, (float) 4.00, (float) 22.00, s);
                new ComprobarCreado().execute(b);
                Log.d("TEST Sobrecarga", "CREADO "+ b.getNombre()+ " con "+length+ " caracteres");
                i++;
            }catch (Throwable ex){
                Log.d("TEST Sobrecarga", "NO CREADO bar"+ i+ " con "+length+ " caracteres");
                fin=false;
            }
        }
    }

    /**
     * Crea un string aleatorio de longitud length
     */
    private static String random(int length) {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(length);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
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

    public class ComprobarModificacion extends AsyncTask<Bar, Void, Void> {

        private Exception e = null;

        @Override
        protected Void doInBackground(Bar... params) {
            try {
                ConjuntoBares.getInstance().updateBar(params[0]);
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
