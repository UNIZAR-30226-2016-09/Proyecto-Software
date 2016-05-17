package administrador;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import bar.R;

/**
 * Dialogo para que el administrador seleccione los generos de musica de un bar
 */
public class MusicSelectionDialog extends DialogFragment {
    private String[] mGenerosMusica;
    private boolean[] mSeleccion;
    private OnMusicSelected mOnMusicSelected;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.selecciona_generos_musica);
        builder.setMultiChoiceItems(mGenerosMusica, mSeleccion, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                mSeleccion[i] = b;
            }
        });
        builder.setPositiveButton(R.string.acept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mOnMusicSelected.onMusicSelected(mGenerosMusica, mSeleccion);
            }
        });
        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return builder.create();
    }

    public interface OnMusicSelected {
        void onMusicSelected(String[] musica, boolean[] seleccion);
    }

    /**
     * Pone los datos a mostrar en el dialogo. IMPORTANTE: llamar al metodo antes de mostrar el
     * dialogo
     *
     * @param generosMusica array de cadenas con los generos musicales
     */
    public void setGenerosMusica(String[] generosMusica) {
        mGenerosMusica = generosMusica;
        mSeleccion = new boolean[generosMusica.length];
    }

    public void setSeleccion(boolean[] seleccion) {
        mSeleccion = seleccion;
    }

    /**
     * Pone listener para cuando el usuario haya seleccinoado los generos de musica
     *
     * @param onMusicSelected callback
     */
    public void setOnMusicSelected(OnMusicSelected onMusicSelected) {
        mOnMusicSelected = onMusicSelected;
    }
}
