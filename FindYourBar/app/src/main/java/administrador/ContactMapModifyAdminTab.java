package administrador;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bar.Bar;

/**
 * Pestaña con la informacion de contacto de un bar y donde se puede modificar esa informacion
 */
public class ContactMapModifyAdminTab extends ContactMapAdminTab {


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
     * Rellena los widgets con la informacion de contactio
     */
    private void setInformation() {
        mDireccion.setText(mBar.getDireccion());
        mTelefono.setText(mBar.getTelefono());
        mEmail.setText(mBar.getEmail());
        mFacebook.setText(mBar.getFacebook());

    }

}
