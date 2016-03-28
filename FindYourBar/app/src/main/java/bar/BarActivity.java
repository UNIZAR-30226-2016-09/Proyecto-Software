package bar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Pantalla para la informacion de un solo bar
 */
public class BarActivity extends AppCompatActivity {

    private static final String BAR_ELEGIDO = "com.findyourbar.bar_elegido";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        TextView tituloBar = (TextView) findViewById(R.id.bar_nombre_bar);
        TextView descripcionBar = (TextView) findViewById(R.id.bar_descripcion_bar);
        String nombreBarElegido = getIntent().getCharSequenceExtra(BAR_ELEGIDO).toString();
        Bar bar = ConjuntoBares.getInstance().getBar(nombreBarElegido);
        tituloBar.setText(bar.getNombre());
        descripcionBar.setText(bar.getDescripcion());


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
}


