package bar;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que va a guardar la lista de bares.
 * TODO: cambiar cuando se añada la interfaz para el acceso a la base de datos
 */
public class ConjuntoBares {

    private static ConjuntoBares sConjuntoBares;

    private static List<Bar> mBares;

    private ConjuntoBares() {
        mBares = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mBares.add(new Bar("Bar " + i, "Descripcion " + i));
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
