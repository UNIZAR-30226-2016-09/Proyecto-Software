package bar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import administrador.LoginActivity;

/**
 * Pantalla inicial de la aplicacion
 */
public class FindYourBar extends AppCompatActivity implements View.OnClickListener {

    private static final String ADMIN_SIGN_IN = "ADMIN_SIGN_IN";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findyourbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));


        Button searchWindowButton = (Button) findViewById(R.id.bSearch);
        Button adminButton = (Button) findViewById(R.id.administracion);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FindYourBar.this, LoginActivity.class));
            }
        });
        searchWindowButton.setOnClickListener(this);
    }

    /**
     * Listen to search button click events
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bSearch:
                //Call method to open search window at the app
                searchWindow();
                break;
        }
    }

    /**
     * Create an activity of SearchBar to visualize all the bar and let search by options
     */
    private void searchWindow() {
        Intent intent = new Intent(this, SearchBar.class); // creamos el intent
        startActivity(intent);
    }
}
