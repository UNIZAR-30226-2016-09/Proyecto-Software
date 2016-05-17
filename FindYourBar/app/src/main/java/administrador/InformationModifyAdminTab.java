package administrador;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bar.Bar;

/**
 * Pestaña con la informacion de general de un bar y donde se puede modificar esa informacion
 */
public class InformationModifyAdminTab extends InformationAdminTab {


    protected Bar mBar;

    public void setBar(Bar bar) {
        mBar = bar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        setInformation();
        return v;
    }

    /**
     * Rellena los widgets con la informacion general de un bar
     */
    private void setInformation() {
        mImageFlipper.addImage(mBar.getPrincipal());
        for (String url : mBar.getSecundaria()) {
            mImageFlipper.addImage(url);
        }
        mNombre.setText(mBar.getNombre());
        mDecripcion.setText(mBar.getDescripcion());
        mEdad.setText(String.valueOf(mBar.getEdad()));
        mHoraCierre.setText(String.valueOf(mBar.getHoraCierre()));
        mHoraApertura.setText(String.valueOf(mBar.getHoraApertura()));
        mMusicSelection = mBar.getMusica();
        String musica = "";
        for (String m : mBar.getMusica()) {
            musica += m + ", ";
        }
        mMusica.setText(musica);

    }
}
