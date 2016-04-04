package bar;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que va a guardar la lista de bares.
 */
public class ConjuntoBares {

    private static ConjuntoBares sConjuntoBares;

    private static List<Bar> mBares;

    private ConjuntoBares() {
        mBares = new ArrayList<>();
    }

    public static void addBar(Bar b){
        mBares.add(b);
    }

    public static void verBares(){
        for (Bar b : mBares) {
            Log.e("nombreBar",b.getNombre());
        }
    }

    public static ConjuntoBares getInstance() {
        if (sConjuntoBares == null) {
            sConjuntoBares = new ConjuntoBares();
        }
        return sConjuntoBares;
    }

    public List<Bar> getBares() {
        return mBares;
    }

    public Bar getBar(String nombre) {
        for (Bar b : mBares) {
            if (b.getNombre().equals(nombre)) {
                return b;
            }
        }
        return null;
    }
}
