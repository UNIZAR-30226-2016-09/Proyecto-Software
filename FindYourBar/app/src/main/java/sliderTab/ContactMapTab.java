package sliderTab;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bar.*;

/**
 * Created by Ana on 12/04/2016.
 */
public class ContactMapTab extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.contact_map_tab,container,false);
        return v;
    }

    /**
     * Listen to search button click events
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mapaSearch:
                String direccion = BarActivity.getDirBar();
                Intent intentD = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q="+direccion));
                intentD.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                this.startActivity(intentD);
                break;
            case R.id.bar_telefono:
                String telefono =BarActivity.getTelefonoBar();
                Intent intentP = new Intent(Intent.ACTION_DIAL, null);
                intentP.setData(Uri.parse(telefono));
                this.startActivity(intentP);
                break;
            case R.id.bar_email:
                String email = BarActivity.getEmailBar();
                send(email);
                break;
            case R.id.bar_facebook:
                String facebook = BarActivity.getFacebookBar();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(facebook));
                startActivity(i);
                break;
        }
    }

    private void send (String subject) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,subject);
        this.startActivity(Intent.createChooser(emailIntent,"Enviando email..."));
    }
}
