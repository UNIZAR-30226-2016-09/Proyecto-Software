package bar;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String NOMBRE_CALLE = "NOMBRE_CALLE";

    private GoogleMap mMap;
    private String calle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        calle = getIntent().getStringExtra(NOMBRE_CALLE);
        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = new SupportMapFragment();
        fm.beginTransaction().add(R.id.map_container, mapFragment).commit();
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> lista = geocoder.getFromLocationName(calle, 1);
            Address lugar = lista.get(0);
            LatLng latLng = new LatLng(lugar.getLatitude(), lugar.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creacion de un intent para la creacion de una actividad MapActivity
     *
     * @param context contexto
     * @param calle   String con el nombre de la calle que queremos mostrar en el mapa
     * @return Intent
     */
    public static Intent newIntent(Context context, String calle) {
        Intent intent = new Intent(context, MapActivity.class);
        intent.putExtra(NOMBRE_CALLE, calle);
        return intent;
    }
}
