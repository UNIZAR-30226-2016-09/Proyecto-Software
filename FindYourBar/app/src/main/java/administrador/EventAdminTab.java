package administrador;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bar.R;
import utils.ImageFlipper;

public class EventAdminTab extends Fragment implements UrlDialog.OnUrlSelectedListener {
    private static final String URL_DIALOG_FRAGMENT = "URL_DIALOG_FRAGMENT";
    private ImageFlipper mImageFlipper;
    private List<String> mListImagenes;


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
                urlDialog.setTargetFragment(EventAdminTab.this, -1);
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

    public List<String> getListImagenes() {
        return mListImagenes;
    }
}
