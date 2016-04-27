package sliderTab;

import android.content.Context;
import android.media.Image;
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

import org.w3c.dom.Text;

import java.util.List;

import bar.Bar;
import bar.BarActivity;
import bar.R;

/**
 * Created by Ana on 12/04/2016.
 */
public class EventTab extends Fragment {
    Bar bar = BarActivity.getNombreBar();
    ImageView eventBar, right, left;
    TextView textoEvento;
    int pos = 0;
    private List<String> imagenesEventos;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.event_tab, container, false);
        imagenesEventos = bar.getEventos();
        Log.e("tamaño lista eventos", "onCreateView: " + imagenesEventos.size());
        eventBar = (ImageView) v.findViewById(R.id.eventView);
        textoEvento = (TextView) v.findViewById(R.id.evento_texto);
        right = (ImageView) v.findViewById(R.id.swipe_rightE);
        left = (ImageView) v.findViewById(R.id.swipe_leftE);

        if (imagenesEventos.size() > 0) {
            Picasso.with(getContext()).load(imagenesEventos.get(0)).into(eventBar);
            textoEvento.setVisibility(View.GONE);
            eventBar.setVisibility(View.VISIBLE);
        } else {
            right.setVisibility(View.GONE);
            left.setVisibility(View.GONE);
        }
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(1);
            }
        });
        left.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                changeImage(-1);
            }
        });
        return v;
    }

    private void changeImage(int direction) {
        if (direction < 0) {
            if (pos > 0) {
                Picasso.with(getContext()).load(imagenesEventos.get(--pos)).into(eventBar);
            }
        } else if (direction > 0) {
            if (pos < imagenesEventos.size() - 1) {
                Picasso.with(getContext()).load(imagenesEventos.get(++pos)).into(eventBar);
            }
        }
    }
}
