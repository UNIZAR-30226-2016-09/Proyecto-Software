package bar;

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

/**
 * Pestaña que muestra la informacion general de un bar
 */
public class InformationTab extends Fragment {

    private Bar mBar = BarActivity.getNombreBar();
    private ImageView mImgBar;
    private ImageView mRight;
    private ImageView mLeft;
    private List<String> mImagenesBar;
    private int mPos = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.information_tab, container, false);

        TextView tituloBar = (TextView) v.findViewById(R.id.bar_nombre_bar);
        TextView descripcionBar = (TextView) v.findViewById(R.id.bar_descripcion_bar);
        tituloBar.setText(mBar.getNombre());
        String horaApertura = String.format("%.2f h", mBar.getHoraApertura());
        horaApertura = horaApertura.replace(",", ":");
        String horaCierre = String.format("%.2f h", mBar.getHoraCierre());
        horaCierre = horaCierre.replace(",", ":");
        String musica = "";
        for (int i = 0; i < mBar.getMusica().size(); i++) {
            if (i < mBar.getMusica().size() - 1)
                musica += mBar.getMusica().get(i) + ", ";
            else
                musica += mBar.getMusica().get(i);
        }
        descripcionBar.setText(mBar.getDescripcion() + "\n\nEdad: " + mBar.getEdad() + " años"
                + "\nMúsica: " + musica + "\nHora de apertura: " + horaApertura
                + "\nHora de cierre: " + horaCierre);
        mImagenesBar = new ArrayList<>();
        Log.e("agenesasdfadfasdf", "onCreateView: " + mBar.getPrincipal());
        if (!mBar.getPrincipal().isEmpty()) {
            mImagenesBar.add(mBar.getPrincipal());
        }
        for (String s : mBar.getSecundaria())
            Log.e("dunno", "dunno" + s);
        mImagenesBar.addAll(mBar.getSecundaria());
        mImgBar = (ImageView) v.findViewById(R.id.imageView);
        Log.e("tamaño lista imagenes", "onCreateView: " + mImagenesBar.size());
        if (mImagenesBar.size() > 0) {
            Log.d("TAG", "onCreateView: " + mImagenesBar.get(0));
            if (!mImagenesBar.get(0).isEmpty()) {
                Picasso.with(getContext()).load(mImagenesBar.get(0)).into(mImgBar);
            }
        }


        mRight = (ImageView) v.findViewById(R.id.swipe_right);
        mRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(1);
            }
        });

        mLeft = (ImageView) v.findViewById(R.id.swipe_left);
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
        if (mPos == mImagenesBar.size() - 1 || mImagenesBar.size() == 0) {
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
                Picasso.with(getContext()).load(mImagenesBar.get(--mPos)).into(mImgBar);
            }
        } else if (direction > 0) {
            if (mPos < mImagenesBar.size() - 1) {
                Picasso.with(getContext()).load(mImagenesBar.get(++mPos)).into(mImgBar);
            }
        }
        updateLeftRightChevron();
    }
}
