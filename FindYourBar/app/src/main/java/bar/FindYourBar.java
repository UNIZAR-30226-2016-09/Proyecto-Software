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

    /**
     * LLamado cuando la actividad es creada
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
     * Callback para cuando se haga click
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bSearch:
                startSearchBarActivity();
                break;
        }
    }

    /**
     * Crea una actividad que muestra todos los bares y permite buscar
     */
    private void startSearchBarActivity() {
        Intent intent = new Intent(this, SearchBarActivity.class);
        startActivity(intent);
    }
}
