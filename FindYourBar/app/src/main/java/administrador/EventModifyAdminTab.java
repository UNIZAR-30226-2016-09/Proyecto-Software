package administrador;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import bar.Bar;
import bar.R;
import utils.ImageFlipper;

/**
 * Pestaña con la informacion de los eventos de un bar y donde se puede modificar esa informacion
 */
public class EventModifyAdminTab extends EventAdminTab {

    protected Bar mBar;

    public void setBar(Bar bar) {
        mBar = bar;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.event_admin_tab, container, false);
        mListImagenes = new ArrayList<>();
        mImageFlipper = (ImageFlipper) v.findViewById(R.id.image_flipper_eventos);
        mImageFlipper.addAddButon(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UrlDialog urlDialog = new UrlDialog();
                urlDialog.setTargetFragment(EventModifyAdminTab.this, -1);
                urlDialog.show(getFragmentManager(), URL_DIALOG_FRAGMENT);
            }
        });
        setInformation();
        return v;
    }


    /**
     * Rellena el widget con las imagenes de los eventos
     */
    private void setInformation() {
        for (String url : mBar.getEventos()) {
            mImageFlipper.addImage(url);
        }
    }
}
