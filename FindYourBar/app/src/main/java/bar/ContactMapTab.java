package bar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Pestaña que muestra la informacion de contacto de un bar
 */
public class ContactMapTab extends Fragment {
    private Bar mBar = BarActivity.getNombreBar();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contact_map_tab, container, false);
        TextView dirBar = (TextView) v.findViewById(R.id.bar_direccion);
        Button tlfBar = (Button) v.findViewById(R.id.bar_telefono);
        tlfBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });
        Button emailfBar = (Button) v.findViewById(R.id.bar_email);
        emailfBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send(mBar.getEmail());
            }
        });
        Button fbBar = (Button) v.findViewById(R.id.bar_facebook);
        fbBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFacebook();
            }
        });
        Button button = (Button) v.findViewById(R.id.mapaSearch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });
        dirBar.setText(mBar.getDireccion());
        tlfBar.setText(mBar.getTelefono());
        emailfBar.setText(mBar.getEmail());
        fbBar.setText(mBar.getFacebook());

        if (mBar.getFacebook().isEmpty()) {
            fbBar.setVisibility(View.GONE);
        }

        if (mBar.getTelefono().isEmpty()) {
            tlfBar.setVisibility(View.GONE);
        }

        if (mBar.getEmail().isEmpty()) {
            emailfBar.setVisibility(View.GONE);
        }
        return v;
    }

    /**
     * Abre el navegador o facebook para ver la pagina de facebook del mBar
     */
    private void openFacebook() {
        String facebook = mBar.getFacebook();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(facebook));
        startActivity(i);
    }

    /**
     * Abre la actividad para llamar con el telefono del mBar marcado
     */
    private void call() {
        String telefono = mBar.getTelefono();
        if (isTelephonyEnabled()) {
            Intent intentP = new Intent(Intent.ACTION_DIAL);
            intentP.setData(Uri.parse("tel:" + telefono));
            this.startActivity(intentP);
        }
    }

    /**
     * Comprueba si hay conectividad telefónica en el dispositivo
     */
    private boolean isTelephonyEnabled() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(
                getContext().TELEPHONY_SERVICE);
        return tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY;
    }

    /**
     * Crea la actividad que muestra el mapa con la localizacion del mBar
     */
    private void openMap() {
        startActivity(MapActivity.newIntent(getContext(), mBar.getDireccion() + ", Zaragoza"));
    }

    /**
     * Crea una actividad para abrir el correo
     */
    private void send(String... direcciones) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, direcciones);
        if (emailIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(emailIntent);
        }

    }
}