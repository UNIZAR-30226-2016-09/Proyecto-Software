package administrador;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import bar.R;

/**
 * Dialogo sobre el nombre del bar
 */
public class UrlDialog extends DialogFragment {
    private EditText mEditText;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.url_dialog, null);
        mEditText = (EditText) v.findViewById(R.id.url);
        builder.setView(v);
        builder.setTitle(R.string.url);
        builder.setPositiveButton(R.string.acept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OnUrlSelectedListener listener = (OnUrlSelectedListener) getTargetFragment();
                listener.onUrlSelected(mEditText.getText().toString());
            }
        });
        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return builder.create();
    }


    public interface OnUrlSelectedListener {
        void onUrlSelected(String url);
    }
}
