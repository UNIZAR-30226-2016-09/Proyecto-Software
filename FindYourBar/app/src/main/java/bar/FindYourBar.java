package bar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Ana on 15/03/2016.
 */


public class FindYourBar extends AppCompatActivity implements View.OnClickListener {
    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findyourbar);

        Button searchWindowButton = (Button)findViewById(R.id.bSearch);
        searchWindowButton.setOnClickListener(this);
  }

    /**
     * Listen to search button click events
     */
   public void onClick(View v) {
       switch (v.getId()) {
           case R.id.bSearch: {
               //Call method to open search window at the app
               searchWindow();
               break;
           }
       }
   }

    /**
     * Create an activity of SearchBar to visualize all the bar and let search by options
     */
    private void searchWindow(){
        Intent intent = new Intent(this, SearchBar.class); // creamos el intent
        startActivity(intent);
        finish();

    }
}
