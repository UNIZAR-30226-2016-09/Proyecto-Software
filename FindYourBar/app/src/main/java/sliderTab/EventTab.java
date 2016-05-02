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

import java.util.List;

import bar.Bar;
import bar.BarActivity;
import bar.R;

/**
 * Created by Ana on 12/04/2016.
 */
public class EventTab extends Fragment {
    private Bar mBar = BarActivity.getNombreBar();
    private ImageView mEventbar, mRight, mLeft;
    private int mPos = 0;
    private List<String> mImagenesEventos;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.event_tab, container, false);
        mImagenesEventos = mBar.getEventos();
        Log.e("tamaño lista eventos", "onCreateView: " + mImagenesEventos.size());
        mEventbar = (ImageView) v.findViewById(R.id.eventView);
        TextView textoEvento = (TextView) v.findViewById(R.id.evento_texto);
        mRight = (ImageView) v.findViewById(R.id.swipe_rightE);
        mLeft = (ImageView) v.findViewById(R.id.swipe_leftE);

        if (mImagenesEventos.size() > 0) {
            Picasso.with(getContext()).load(mImagenesEventos.get(0)).into(mEventbar);
            textoEvento.setVisibility(View.GONE);
            mEventbar.setVisibility(View.VISIBLE);
        } else {
            mRight.setVisibility(View.GONE);
            mLeft.setVisibility(View.GONE);
        }
        mRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(1);
            }
        });
        mLeft.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                changeImage(-1);
            }
        });
        updateLeftRightChevron();
        return v;
    }

    // TODO: Reutilizar codigo de InformationTab y no copiar y pegar

    /**
     * Muestra o no las flechas para cambiar de imagen
     */
    private void updateLeftRightChevron() {
        mLeft.setVisibility(View.VISIBLE);
        mRight.setVisibility(View.VISIBLE);
        if (mPos == 0) {
            mLeft.setVisibility(View.GONE);
        }
        // si no hay mas imagenes a la derecha
        if (mPos == mImagenesEventos.size() - 1 || mImagenesEventos.size() == 0) {
            mRight.setVisibility(View.GONE);
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
            if (mPos > 0) {
                Picasso.with(getContext()).load(mImagenesEventos.get(--mPos)).into(mEventbar);
            }
        } else if (direction > 0) {
            if (mPos < mImagenesEventos.size() - 1) {
                Picasso.with(getContext()).load(mImagenesEventos.get(++mPos)).into(mEventbar);
            }
        }
        updateLeftRightChevron();
    }
}
