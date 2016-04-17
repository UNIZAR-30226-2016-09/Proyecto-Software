package sliderTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bar.Bar;
import bar.BarActivity;
import bar.ConjuntoBares;
import bar.R;

/**
 * Created by Ana on 12/04/2016.
 */

public class InformationTab extends Fragment { //implements View.OnTouchListener {

    Bar bar = BarActivity.getNombreBar();
    private static final String baseUrl = "http://ps1516.ddns.net/images";
    TextView tituloBar, descripcionBar;
    ImageView imgBar, right, left;
    List<String> imagenesBar;
    int pos = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.information_tab, container, false);

        tituloBar = (TextView) v.findViewById(R.id.bar_nombre_bar);
        descripcionBar = (TextView) v.findViewById(R.id.bar_descripcion_bar);
        tituloBar.setText(bar.getNombre());
        String horaApertura = String.format("%.2f h", bar.getHoraApertura());
        horaApertura = horaApertura.replace(",",":");
        String horaCierre = String.format("%.2f h", bar.getHoraCierre());
        horaCierre = horaCierre.replace(",",":");
        String musica = "";
        for (int i=0; i<bar.getMusica().size(); i++){
            if(i<bar.getMusica().size()-1)
                musica += bar.getMusica().get(i) + ", ";
            else
                musica += bar.getMusica().get(i);
        }
        descripcionBar.setText(bar.getDescripcion()+"\n\nEdad: "+bar.getEdad()+" años"
                +"\nMúsica: "+ musica+"\nHora de apertura: "+horaApertura
                +"\nHora de cierre: "+horaCierre);
        imagenesBar = new ArrayList<>();
        imagenesBar.add(bar.getPrincipal());
        imagenesBar.addAll(bar.getSecundaria());
        imgBar = (ImageView) v.findViewById(R.id.imageView);
        Log.e("tamaño lista imagenes", "onCreateView: " + imagenesBar.size());
        if (imagenesBar.size() > 0) {
            Picasso.with(getContext()).load(baseUrl + imagenesBar.get(0)).into(imgBar);
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
        return v;
    }
/*
    public boolean onTouch(View v, MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                // Here u can write code which is executed after the user touch on the screen
                imgBar.setImageResource(R.drawable.bar1);
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                // Here u can write code which is executed after the user release the touch on the screen
                imgBar.setImageResource(R.drawable.bar2);
                break;
            }
            case MotionEvent.ACTION_MOVE:
            {
                // Here u can write code which is executed when user move the finger on the screen
                imgBar.setImageResource(R.drawable.bar3);
                break;
            }
        }
        return true;
    }*/

    private void changeImage(int direction) {
        if (direction < 0) {
            if (pos > 0) {
                Picasso.with(getContext()).load(baseUrl + imagenesBar.get(--pos)).into(imgBar);
            }
        } else if (direction > 0) {
            if (pos < imagenesBar.size() - 1) {
                Picasso.with(getContext()).load(baseUrl + imagenesBar.get(++pos)).into(imgBar);
            }
        }
    }
}
