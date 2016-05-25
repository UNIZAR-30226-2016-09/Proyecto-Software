package administrador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import bar.R;

/**
 * Pantalla para hacer login al administrador
 */
public class LoginActivity extends AppCompatActivity {

    private EditText mContrasena;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button botonLogin = (Button) findViewById(R.id.boton_login);
        mContrasena = (EditText) findViewById(R.id.et_contraseña);
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarEntrada()) {
                    startActivity(new Intent(LoginActivity.this, SearchBarAdminActivity.class));
                    finish();
                }
            }
        });

    }

    /**
     * Valida la entrada del admin
     *
     * @return true si la contraseña es valida, false en caso contrario
     */
    private boolean validarEntrada() {
        String str = mContrasena.getText().toString();
        Log.e("TAG", "validarEntrada: " + str);
        if (!authAdmin(str)) {
            mContrasena.setError(getResources().getString(R.string.error_contraseña_igualdad));
            return false;
        } else {
            mContrasena.setError(null);
            return true;
        }
    }

    /**
     * TODO: cambiar la contraseña o el metodo de autentificacion. Hashing de la contraseña para mayor seguridad
     * Autentifica al administrador
     *
     * @param password contraseña a autentificar
     * @return true si la contraseña es valida, false en caso contrario
     */
    private boolean authAdmin(String password) {
        return password.equals("f<XSHV-]9JN_=9\\g&LeV");
    }
}
