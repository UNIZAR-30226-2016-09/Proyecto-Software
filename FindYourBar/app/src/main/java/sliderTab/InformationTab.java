package sliderTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bar.Bar;
import bar.BarActivity;
import bar.R;

/**
 * Created by Ana on 12/04/2016.
 */

public class InformationTab extends Fragment {

    private Bar bar = BarActivity.getNombreBar();
    private ImageView imgBar, right, left;
    private List<String> imagenesBar;
    private int pos = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.information_tab, container, false);

        TextView tituloBar = (TextView) v.findViewById(R.id.bar_nombre_bar);
        TextView descripcionBar = (TextView) v.findViewById(R.id.bar_descripcion_bar);
        tituloBar.setText(bar.getNombre());
        String horaApertura = String.format("%.2f h", bar.getHoraApertura());
        horaApertura = horaApertura.replace(",", ":");
        String horaCierre = String.format("%.2f h", bar.getHoraCierre());
        horaCierre = horaCierre.replace(",", ":");
        String musica = "";
        for (int i = 0; i < bar.getMusica().size(); i++) {
            if (i < bar.getMusica().size() - 1)
                musica += bar.getMusica().get(i) + ", ";
            else
                musica += bar.getMusica().get(i);
        }
        descripcionBar.setText(bar.getDescripcion() + "\n\nEdad: " + bar.getEdad() + " años"
                + "\nMúsica: " + musica + "\nHora de apertura: " + horaApertura
                + "\nHora de cierre: " + horaCierre);
        imagenesBar = new ArrayList<>();
        imagenesBar.add(bar.getPrincipal());
        imagenesBar.addAll(bar.getSecundaria());
        imgBar = (ImageView) v.findViewById(R.id.imageView);
        Log.e("tamaño lista imagenes", "onCreateView: " + imagenesBar.size());
        if (imagenesBar.size() > 0) {
            Log.d("TAG", "onCreateView: " + imagenesBar.get(0));
            if (!imagenesBar.get(0).isEmpty()) {
                Picasso.with(getContext()).load(imagenesBar.get(0)).into(imgBar);
            }
        }


        right = (ImageView) v.findViewById(R.id.swipe_right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(1);
            }
        });

        left = (ImageView) v.findViewById(R.id.swipe_left);
        left.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                changeImage(-1);
            }
        });
        updateLeftRightChevron();
        return v;
    }

    /**
     * Muestra o no las flechas para cambiar de imagen
     */
    private void updateLeftRightChevron() {
        left.setVisibility(View.VISIBLE);
        right.setVisibility(View.VISIBLE);
        if (pos == 0) {
            left.setVisibility(View.GONE);
        }
        // si no hay mas imagenes a la derecha
        if (pos == imagenesBar.size() - 1 || imagenesBar.size() == 0) {
            right.setVisibility(View.GONE);
        }
    }

    /**
     * Cambia la imagen a mostrar
     *
     * @param direction si negativo cambia a la imagen que hay a la izquierda, si positivo cambia
     *                  a la imagen que hay a la derecha
     */
    private void changeImage(int direction) {
        if (direction < 0) {
            if (pos > 0) {
                Picasso.with(getContext()).load(imagenesBar.get(--pos)).into(imgBar);
            }
        } else if (direction > 0) {
            if (pos < imagenesBar.size() - 1) {
                Picasso.with(getContext()).load(imagenesBar.get(++pos)).into(imgBar);
            }
        }
        updateLeftRightChevron();
    }
}
