package bar;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import bar.EnvioDatos;

/**
 * Dialogo sobre el nombre del bar
 */
public class SearchNameDialogFragment extends DialogFragment {
    private OnBarNameSelectedListener mListener;
    EditText editText;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_namemenu, null);
        builder.setView(v);
        builder.setTitle(R.string.menu_name_title);
        editText = (EditText) v.findViewById(R.id.bar_name);
        builder.setPositiveButton(R.string.acept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Quitarlo para no probar con la base de datos
                //EnvioDatos.enviarBares("Cafe da Luxe");
                mListener.onBarNameSelected(editText.getText().toString());
                Log.d("TESTING",editText.getText().toString());
            }
        });
        return builder.create();
    }

    public interface OnBarNameSelectedListener {
        public void onBarNameSelected(String nombre);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnBarNameSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " ha de implementar onBarNameSelected()");
        }
    }


}
