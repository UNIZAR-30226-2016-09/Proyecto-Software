package utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;

import bar.R;

public class ImageFlipper extends LinearLayout {
    private ViewFlipper mViewFlipper;
    private ImageView mChevronLeft;
    private ImageView mChevronRight;
    private Context mContext;
    private Button mAddButton;
    private ImageView mRemoveImage;

    public ImageFlipper(Context context) {
        super(context);
        init(context);

    }

    public ImageFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.image_flipper, this);
        mRemoveImage = (ImageView) v.findViewById(R.id.remove);
        mViewFlipper = (ViewFlipper) v.findViewById(R.id.view_flipper);
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

    private void showRightImage() {
        mViewFlipper.showNext();
        updateChevrons();
    }

    private void showLeftImage() {
        mViewFlipper.showPrevious();
        updateChevrons();
    }


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

    public void addImage(String url) {
        ImageView iv = new ImageView(mContext);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setLayoutParams(mViewFlipper.getLayoutParams());
        Picasso.with(getContext()).load(url).into(iv);
        mViewFlipper.addView(iv);
        updateChevrons();
    }

    public void addAddButon(OnClickListener mOnClickListener) {
        mAddButton.setOnClickListener(mOnClickListener);
        mAddButton.setVisibility(VISIBLE);
        updateChevrons();
    }

    public void removeCurrentImage() {
        mViewFlipper.removeViewAt(mViewFlipper.getDisplayedChild());
    }


}
