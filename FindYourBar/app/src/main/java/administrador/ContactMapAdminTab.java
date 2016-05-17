package administrador;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bar.R;

public class ContactMapAdminTab extends Fragment {

    protected TextInputEditText mDireccion;
    protected TextInputEditText mTelefono;
    protected TextInputEditText mEmail;
    protected TextInputEditText mFacebook;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contact_map_admin_tab, container, false);
        mDireccion = (TextInputEditText) v.findViewById(R.id.direccion__bar_admin);
        mTelefono = (TextInputEditText) v.findViewById(R.id.telefono_bar_admin);
        mEmail = (TextInputEditText) v.findViewById(R.id.email_bar_admin);
        mFacebook = (TextInputEditText) v.findViewById(R.id.facebook_bar_admin);
        return v;
    }

    public boolean validarEntrada() {
        return validarTelefono();
    }

    public boolean validarTelefono() {
        int length = mTelefono.getText().toString().length();
        if (length > 0 && length != 9) {
            mTelefono.setError(getResources().getString(R.string.telefono_tiene_9_cifras));
            return false;
        } else {
            return true;
        }
    }

    public String getDireccion() {
        return mDireccion.getText().toString();
    }

    public String getPhone() {
        return mTelefono.getText().toString();
    }

    public String getEmail() {
        return mEmail.getText().toString();
    }

    public String getFacebook() {
        return mFacebook.getText().toString();
    }
}
