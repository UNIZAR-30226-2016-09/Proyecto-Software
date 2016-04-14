package sliderTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import bar.Bar;
import bar.BarActivity;
import bar.ConjuntoBares;
import bar.R;

/**
 * Created by Ana on 12/04/2016.
 */

public class InformationTab extends Fragment{ //implements View.OnTouchListener {
    public static final String[] IMAGE_NAME = {"bar1", "bar2", "bar3", "bar"};

    Bar bar = BarActivity.getNombreBar();
    TextView tituloBar, descripcionBar;
    ImageView imgBar, right, left;
    int pos = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.information_tab,container,false);

        tituloBar = (TextView) v.findViewById(R.id.bar_nombre_bar);
        descripcionBar = (TextView) v.findViewById(R.id.bar_descripcion_bar);
        tituloBar.setText(bar.getNombre());
        descripcionBar.setText(bar.getDescripcion());
        imgBar = (ImageView) v.findViewById(R.id.imageView);
        //imgBar.setOnTouchListener(this);

        right  = (ImageView) v.findViewById(R.id.swipe_right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(1);
            }
        });

        left=(ImageView)v.findViewById(R.id.swipe_left);
        left.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                changeImage(-1);
            }
        });
        int resID = getResources().getIdentifier(IMAGE_NAME[0] , "drawable", getContext().getPackageName());
        imgBar.setImageResource(resID);
        return v;
    }
/*
    public boolean onTouch(View v, MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                // Here u can write code which is executed after the user touch on the screen
                imgBar.setImageResource(R.drawable.bar1);
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                // Here u can write code which is executed after the user release the touch on the screen
                imgBar.setImageResource(R.drawable.bar2);
                break;
            }
            case MotionEvent.ACTION_MOVE:
            {
                // Here u can write code which is executed when user move the finger on the screen
                imgBar.setImageResource(R.drawable.bar3);
                break;
            }
        }
        return true;
    }*/

    private void changeImage (int direction) {
        String image;
        if(pos+direction < IMAGE_NAME.length)
            pos+=direction;
        else
            pos=0;
        image = IMAGE_NAME[pos];
        int resID = getResources().getIdentifier(image , "drawable", getContext().getPackageName());
        imgBar.setImageResource(resID);
    }
}
