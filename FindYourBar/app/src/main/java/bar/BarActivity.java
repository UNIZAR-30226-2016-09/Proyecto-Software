package bar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        TextView dirBar = (TextView) findViewById(R.id.bar_direccion);
        TextView tlfBar = (TextView) findViewById(R.id.bar_telefono);
        TextView emailfBar = (TextView) findViewById(R.id.bar_email);
        TextView fbBar = (TextView) findViewById(R.id.bar_facebook);

        String nombreBarElegido = getIntent().getCharSequenceExtra(BAR_ELEGIDO).toString();
        Bar bar = ConjuntoBares.getInstance().getBar(nombreBarElegido);
        setTitle(bar.getNombre());
        tituloBar.setText(bar.getNombre());
        descripcionBar.setText(bar.getDescripcion());
        dirBar.setText(bar.getDireccion());
        tlfBar.setText(tlfBar.getText() + " " +  bar.getTelefono());
        emailfBar.setText(emailfBar.getText() + " " + bar.getEmail());
        fbBar.setText(fbBar.getText() + " " + bar.getFacebook());
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


