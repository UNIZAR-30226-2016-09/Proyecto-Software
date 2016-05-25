package administrador;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bar.R;
import utils.ImageFlipper;

/**
 * Pestaña que pide al usuario la informacion general del bar
 */
public class InformationAdminTab extends Fragment implements UrlDialog.OnUrlSelectedListener, MusicSelectionDialog.OnMusicSelected {

    protected static final String URL_DIALOG_FRAGMENT = "URL_DIALOG_FRAGMENT";
    protected static final String MUSIC_DIALOG_FRAGMENT = "MUSIC_DIALOG_FRAGMENT";

    // TODO: pedir los datos de la base de datos
    protected static final String[] MUSIC = {"Pop/musica comercial", "Rap", "Rock/Heavy",
            "Latina", "Indie", "Electronica", "Años 60-80"};
    protected List<String> mMusicSelection;

    protected ImageFlipper mImageFlipper;
    protected TextInputEditText mNombre;
    protected TextInputEditText mDecripcion;
    protected TextInputEditText mEdad;
    protected TextInputEditText mHoraCierre;
    protected TextInputEditText mHoraApertura;
    protected TextInputEditText mMusica;
    protected TextView mNombreTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mMusicSelection = new ArrayList<>();
        View v = inflater.inflate(R.layout.information_admin_tab, container, false);
        mNombreTextView = (TextView) v.findViewById(R.id.nombre_bar_mostrar_admin);
        mNombre = (TextInputEditText) v.findViewById(R.id.nombre_bar_admin);
        mNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                OnTitleChanged listener = (OnTitleChanged) getActivity();
                listener.onTitleChanged(charSequence);
                mNombreTextView.setText(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mEdad = (TextInputEditText) v.findViewById(R.id.edad_bar_admin);
        mMusica = (TextInputEditText) v.findViewById(R.id.musica_bar_admin);
        mDecripcion = (TextInputEditText) v.findViewById(R.id.descripcion_bar_admin);
        mHoraApertura = (TextInputEditText) v.findViewById(R.id.hora_apertura_bar_admin);
        mHoraCierre = (TextInputEditText) v.findViewById(R.id.hora_cierre_bar_admin);
        Button mAddButton = (Button) v.findViewById(R.id.añadir_genero_musica);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicSelectionDialog musicSelectionDialog = new MusicSelectionDialog();
                musicSelectionDialog.setGenerosMusica(MUSIC);
                musicSelectionDialog.setOnMusicSelected(InformationAdminTab.this);
                musicSelectionDialog.show(getFragmentManager(), MUSIC_DIALOG_FRAGMENT);
            }
        });
        mImageFlipper = (ImageFlipper) v.findViewById(R.id.image_flipper_imagenes_bar);
        mImageFlipper.addAddButon(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogoUrl();
            }
        });
        return v;
    }

    /**
     * Valida toda la informacion que el usuario pueda introducir
     *
     * @return verdadero si todos los campos introducidos son validos falso si alguno esta mal puesto
     */
    public boolean validarEntrada() {
        return validarNombre() & validarEdad() & validarHoras();
    }

    /**
     * Valida que el nombre es valido
     *
     * @return verdadero si el nombre no esta vacio y false en caso contrario
     */
    public boolean validarNombre() {
        String nombre = mNombre.getText().toString();
        if (nombre.isEmpty()) {
            mNombre.setError(getResources().getString(R.string.no_puede_estar_vacio));
            return false;
        } else {
            return true;
        }
    }

    /**
     * Valida que la edad introducida es valida
     *
     * @return verdadero si la edad es >= 15 y false en caso contrario
     */
    public boolean validarEdad() {
        String edadStr = mEdad.getText().toString();
        if (edadStr.isEmpty()) {
            mEdad.setError(getResources().getString(R.string.no_puede_estar_vacio));
            return false;
        } else {
            int edad = Integer.parseInt(edadStr);
            if (edad < 15) {
                mEdad.setError(getResources().getString(R.string.edad_mayor_15_años));
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * Valida que las horas introducidas son validas
     *
     * @return verdadero si las horas son validas(estan entre 0 y 23) y false en caso contrario
     */
    public boolean validarHoras() {
        TextInputEditText horas[] = {mHoraApertura, mHoraCierre};
        boolean ret = true;
        for (TextInputEditText hora1 : horas) {
            if (!hora1.getText().toString().isEmpty()) {
                int hora = Integer.parseInt(hora1.getText().toString());
                if (hora < 0 || hora >= 24) {
                    hora1.setError(getResources().getString(R.string.no_es_hora_valida));
                    ret = false;
                }
            }
        }
        return ret;
    }

    /**
     * Muestra el dialogo que pide la URL de la imagen del bar
     */
    protected void mostrarDialogoUrl() {
        UrlDialog urlDialog = new UrlDialog();
        urlDialog.setTargetFragment(InformationAdminTab.this, -1);
        urlDialog.show(getFragmentManager(), URL_DIALOG_FRAGMENT);
    }

    @Override
    public void onUrlSelected(String url) {
        mImageFlipper.addImage(url);
    }

    @Override
    public void onMusicSelected(String[] musica, boolean[] seleccion) {
        List<String> musicSelected = new ArrayList<>();
        List<String> aux = new ArrayList<>();
        for (int i = 0; i < seleccion.length; i++) {
            if (seleccion[i]) {
                aux.add(musica[i]);
                musicSelected.add(musica[i]);
            }
        }
        mMusica.setText(TextUtils.join(", ", aux));
        mMusicSelection = musicSelected;
    }

    public interface OnTitleChanged {
        void onTitleChanged(CharSequence title);

    }

    public String getNombreBar() {
        return mNombre.getText().toString();
    }

    public String getDescripcionBar() {
        return mDecripcion.getText().toString();
    }

    public int getEdad() {
        return Integer.parseInt(mEdad.getText().toString());
    }

    public float getHoraCierre() {
        String hc = mHoraCierre.getText().toString();
        if (hc.isEmpty()) {
            return -1;
        } else {
            return Float.valueOf(hc);
        }
    }

    public float getHoraApertura() {
        String ha = mHoraApertura.getText().toString();
        if (ha.isEmpty()) {
            return -1;
        } else {
            return Float.valueOf(ha);
        }
    }

    public List<String> getListImagenes() {
        return mImageFlipper.getImagesList();
    }

    public List<String> getListMusica() {
        return mMusicSelection;
    }
}
