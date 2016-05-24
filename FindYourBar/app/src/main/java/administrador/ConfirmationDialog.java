package administrador;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import bar.R;

public class ConfirmationDialog extends DialogFragment {

    private OnYesClickListener mOnYesClickListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle(R.string.borrar_bar);
        builder.setMessage(R.string.esta_seguro);
        builder.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mOnYesClickListener != null) {
                    mOnYesClickListener.onYesClickListener();
                }
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return builder.create();
    }


    public void setOnYesClickListener(OnYesClickListener onYesClickListener) {
        mOnYesClickListener = onYesClickListener;
    }

    public interface OnYesClickListener {
        void onYesClickListener();
    }


}
