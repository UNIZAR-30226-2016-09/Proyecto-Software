package bar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import bar.EnvioDatos;

/**
 * Dialogo sobre el nombre del bar
 */
public class SearchNameDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_namemenu, null));
        builder.setTitle(R.string.menu_name_title);
        builder.setPositiveButton(R.string.acept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Quitarlo para no probar con la base de datos
                EnvioDatos.enviarBares("Cafe da Luxe");
            }
        });
        return builder.create();
    }
}
