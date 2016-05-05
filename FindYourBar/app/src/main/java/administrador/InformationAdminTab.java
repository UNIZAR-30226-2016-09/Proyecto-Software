package administrador;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bar.R;
import utils.ImageFlipper;


public class InformationAdminTab extends Fragment implements UrlDialog.OnUrlSelectedListener {

    private static final String URL_DIALOG_FRAGMENT = "URL_DIALOG_FRAGMENT";
    private ImageFlipper mImageFlipper;
    private TextInputEditText mNombreEditText;
    private TextInputEditText mDecripcionEditText;
    private List<String> mListImagenes;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("TAG", "onCreateView: shier");
        mListImagenes = new ArrayList<>();
        View v = inflater.inflate(R.layout.information_admin_tab, container, false);
        mNombreEditText = (TextInputEditText) v.findViewById(R.id.nombre_bar_admin);
        mDecripcionEditText = (TextInputEditText) v.findViewById(R.id.descripcion_bar_admin);
        mImageFlipper = (ImageFlipper) v.findViewById(R.id.image_flipper_imagenes_bar);
        mImageFlipper.addAddButon(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UrlDialog urlDialog = new UrlDialog();
                urlDialog.setTargetFragment(InformationAdminTab.this, -1);
                urlDialog.show(getFragmentManager(), URL_DIALOG_FRAGMENT);
            }
        });
        return v;
    }

    @Override
    public void onUrlSelected(String url) {
        mListImagenes.add(url);
        mImageFlipper.addImage(url);
    }

    public String getNombreBar() {
        return mNombreEditText.getText().toString();
    }

    public String getDescripcionBar() {
        return mDecripcionEditText.getText().toString();
    }

    public List<String> getListImagenes() {
        return mListImagenes;
    }
}
