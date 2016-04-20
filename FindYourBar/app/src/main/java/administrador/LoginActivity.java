package administrador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import bar.R;

public class LoginActivity extends AppCompatActivity {

    private EditText contrasena;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        Button botonLogin = (Button) findViewById(R.id.boton_login);
        contrasena = (EditText) findViewById(R.id.et_contraseña);
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarEntrada()) {
                    startActivity(new Intent(LoginActivity.this, SearchBarAdmin.class));
                }
            }
        });

    }

    private boolean validarEntrada() {
        String str = contrasena.getText().toString();
        if (!str.equals("password")) {
            contrasena.setError(getResources().getString(R.string.error_contraseña_igualdad));
            return false;
            //} else if (str.length() != 7) {
            //    contrasena.setError(getResources().getString(R.string.error_contraseña_longitud));
            //    return false;
        } else {
            contrasena.setError(null);
            return true;
        }
    }
}
