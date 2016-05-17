package utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bar.R;

/**
 * Un ViewFlipper con mas funcionalidades
 */
public class ImageFlipper extends LinearLayout {

    private static final String TAG = "ImageFlipper";

    private ViewFlipper mViewFlipper;
    private ImageView mChevronLeft;
    private ImageView mChevronRight;
    private Context mContext;
    private Button mAddButton;
    private ImageView mRemoveImage;
    private List<String> mListImagenes;


    public ImageFlipper(Context context) {
        super(context);
        init(context);

    }

    public ImageFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Inicializacion del objeto
     *
     * @param context contexto actual
     */
    private void init(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.image_flipper, this);
        mListImagenes = new ArrayList<>();
        mRemoveImage = (ImageView) v.findViewById(R.id.remove);
        mRemoveImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                removeCurrentImage();
            }
        });
        mViewFlipper = (ViewFlipper) v.findViewById(R.id.view_flipper);
        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out));
        mAddButton = (Button) v.findViewById(R.id.boton_añadir_imagen);
        mChevronLeft = (ImageView) v.findViewById(R.id.swipe_left);
        mChevronRight = (ImageView) v.findViewById(R.id.swipe_right);
        mChevronRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showRightImage();
            }
        });

        mChevronLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showLeftImage();
            }
        });
        updateChevrons();
    }

    /**
     * Muestra la imagen que hay a la derecha
     */
    private void showRightImage() {
        mViewFlipper.showNext();
        updateChevrons();
    }

    /**
     * Muestra la imagen que hay a la izquierda
     */
    private void showLeftImage() {
        mViewFlipper.showPrevious();
        updateChevrons();
    }

    /**
     * Muestra o no las fechas para pasar a la imagen de la izquierda o la derecha en funcion de si
     * hay mas imagenes a esos lados
     */
    private void updateChevrons() {
        if (mViewFlipper.getDisplayedChild() == mViewFlipper.getChildCount() - 1 ||
                mViewFlipper.getChildCount() == 0) {
            mChevronRight.setVisibility(INVISIBLE);
        } else {
            mChevronRight.setVisibility(VISIBLE);
        }
        if (mViewFlipper.getDisplayedChild() == 0) {
            mChevronLeft.setVisibility(INVISIBLE);
            mRemoveImage.setVisibility(INVISIBLE);
        } else {
            mChevronLeft.setVisibility(VISIBLE);
            mRemoveImage.setVisibility(VISIBLE);

        }
    }

    /**
     * Añade una imagen a mostrar al ViewFlipper
     *
     * @param url URL de la imagen a mostrar
     */
    public void addImage(String url) {
        ImageView iv = new ImageView(mContext);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setLayoutParams(mViewFlipper.getLayoutParams());
        if (!url.isEmpty()) {
            Picasso.with(getContext()).load(url).into(iv);
            mListImagenes.add(url);
            mViewFlipper.addView(iv);
            updateChevrons();
        }
    }

    /**
     * Añade el boton de añadir mas imagenes al ViewFlipper para que el usuario pueda añadir imagnes
     *
     * @param mOnClickListener listener del boton
     */
    public void addAddButon(OnClickListener mOnClickListener) {
        mAddButton.setOnClickListener(mOnClickListener);
        mAddButton.setVisibility(VISIBLE);
        updateChevrons();
    }

    /**
     * Deja de mostrar la imagen actual
     */
    public void removeCurrentImage() {
        int pos = mViewFlipper.getDisplayedChild();
        Log.e(TAG, "removeCurrentImage: " + pos);
        mViewFlipper.removeViewAt(pos);
        mListImagenes.remove(--pos);
        updateChevrons();
    }

    /**
     * Devuelve la lista de URL de las imagenes
     *
     * @return Lista de cadenas con las imagenes de las URL
     */
    public List<String> getImagesList() {
        return mListImagenes;
    }
}
