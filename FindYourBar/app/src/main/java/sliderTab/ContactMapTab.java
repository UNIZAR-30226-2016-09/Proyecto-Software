package sliderTab;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import bar.*;

/**
 * Created by Ana on 12/04/2016.
 */
public class ContactMapTab extends Fragment{
    Bar bar = BarActivity.getNombreBar();
    TextView dirBar, tlfBar, emailfBar, fbBar;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.contact_map_tab, container, false);

        dirBar = (TextView) v.findViewById(R.id.bar_direccion);
        tlfBar = (TextView) v.findViewById(R.id.bar_telefono);
        tlfBar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                call();}
        });
        emailfBar = (TextView) v.findViewById(R.id.bar_email);
        emailfBar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = bar.getEmail();
                send(email);}
        });
        fbBar = (TextView) v.findViewById(R.id.bar_facebook);
        fbBar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openFacebook();
            }
        });
        Button button = (Button) v.findViewById(R.id.mapaSearch);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
              public void onClick(View v) {
                  openMap();
              }
        });

        dirBar.setText(bar.getDireccion());
        tlfBar.setText(bar.getTelefono());
        emailfBar.setText(bar.getEmail());
        fbBar.setText(bar.getFacebook());
        return v;
    }

    private void openFacebook(){
        String facebook = bar.getFacebook();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(facebook));
        startActivity(i);
    }

    private void call(){
        String telefono = bar.getTelefono();
        if(isTelephonyEnabled()) {
            Intent intentP = new Intent(Intent.ACTION_DIAL);
            intentP.setData(Uri.parse("tel:" + telefono));
            this.startActivity(intentP);
        }
    }

    //Comprueba si hay conectividad telefónica en el dispositivo
    private boolean isTelephonyEnabled(){
        TelephonyManager tm = (TelephonyManager)getContext().getSystemService(getContext().TELEPHONY_SERVICE);
        return tm != null && tm.getSimState()==TelephonyManager.SIM_STATE_READY;
    }

    private void openMap () {
        String direccion = bar.getDireccion();
        Intent intentD = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=" + direccion));
        intentD.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        this.startActivity(intentD);
    }

    private void send (String subject) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,subject);
        emailIntent.setType("text/plain");
        this.startActivity(Intent.createChooser(emailIntent,"Enviando email..."));
    }
}
