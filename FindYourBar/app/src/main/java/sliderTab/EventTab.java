package sliderTab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import bar.R;

/**
 * Created by Ana on 12/04/2016.
 */
public class EventTab extends Fragment {
    ImageView eventBar, right, left;
    int pos=0;
    public static final String[] IMAGE_NAME = {"cartel1", "cartel2", "cartel3", "cartel"};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.event_tab,container,false);

        eventBar = (ImageView) v.findViewById(R.id.eventView);

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
            public void onClick (View v){
                changeImage(-1);
            }
        });
        int resID = getResources().getIdentifier(IMAGE_NAME[0] , "drawable", getContext().getPackageName());
        eventBar.setImageResource(resID);
        return v;
    }

    private void changeImage (int direction) {
        String image;
        if(pos+direction < IMAGE_NAME.length)
            pos+=direction;
        else
            pos=0;
        image = IMAGE_NAME[pos];
        int resID = getResources().getIdentifier(image , "drawable", getContext().getPackageName());
        eventBar.setImageResource(resID);
    }
}
