package sliderTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bar.Bar;
import bar.BarActivity;
import bar.ConjuntoBares;
import bar.R;

/**
 * Created by Ana on 12/04/2016.
 */

public class InformationTab extends Fragment{
    Bar bar = BarActivity.getNombreBar();
    TextView tituloBar, descripcionBar;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.information_tab,container,false);

        tituloBar = (TextView) v.findViewById(R.id.bar_nombre_bar);
        descripcionBar = (TextView) v.findViewById(R.id.bar_descripcion_bar);

        tituloBar.setText(bar.getNombre());
        descripcionBar.setText(bar.getDescripcion());

        return v;
    }
}
