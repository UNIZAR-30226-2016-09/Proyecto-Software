package bar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.HashMap;

/**
 * Pantalla que pide al usuario los filtros por los que deseea filtrar los bares
 */
public class FiltersActivity extends AppCompatActivity {

    private static final String MUSIC_SELECTED = "MUSIC_SELECTED";
    private static final String AGE_SELECTED = "AGE_SELECTED";
    private static final String CLOSE_SELECTED = "CLOSE_SELECTED";
    private static final String OPEN_SELECTED = "OPEN_SELECTED";
    private static final String NEW_LIST = "NEW_LIST";
    // Valores de las distinas posiciones de los spinner
    private static final String[] EDAD = {"all", "16", "18", "21"}; // all -> 21
    private static final String[] HORA_CIERRE = {"all", "3", "4", "5", "6.3"}; // all -> 3
    private static final String[] HORA_APERTURA = {"all", "22", "23", "0"}; // all -> 0
    private static final String[] MUSIC = {"all", "Pop/musica comercial", "Rap", "Rock/Heavy",
            "Latina", "Indie", "Electronica", "Años 60-80"};
    private static int[] mPreviousSpinnerPositions = {0, 0, 0, 0};
    private Spinner mSpinnerMusic;
    private Spinner mSpinnerAge;
    private Spinner mSpinnerClose;
    private Spinner mSpinnerOpen;

    /**
     * Usado para la creacion del intent que para comunicarse con esta actividad
     */
    public static Intent newIntent(Context context, boolean isNewList) {
        Intent intent = new Intent(context, FiltersActivity.class);
        intent.putExtra(NEW_LIST, isNewList);
        return intent;
    }

    /**
     * Devuelve un hasmap con los valores de los filtors selecionados. Las llaves son: "Musica",
     * "Edad",
     * "HoraCierre" y "HoraApertura". Un valor de "all" significa que al usuario le da igual ese
     * parametro
     * de filtro
     */
    public static HashMap<String, String> whatFiltersWhereSelected(Intent intent) {
        HashMap<String, String> aux = new HashMap<>();
        aux.put("Musica", intent.getStringExtra(MUSIC_SELECTED));
        aux.put("Edad", intent.getStringExtra(AGE_SELECTED));
        aux.put("HoraCierre", intent.getStringExtra(CLOSE_SELECTED));
        aux.put("HoraApertura", intent.getStringExtra(OPEN_SELECTED));
        return aux;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_list);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mSpinnerMusic = (Spinner) findViewById(R.id.spinner_music);
        mSpinnerAge = (Spinner) findViewById(R.id.spinner_age);
        mSpinnerClose = (Spinner) findViewById(R.id.spinner_close);
        mSpinnerOpen = (Spinner) findViewById(R.id.spinner_open);
        setAdapterSpinner(mSpinnerMusic, R.array.music_genre);
        setAdapterSpinner(mSpinnerAge, R.array.age_groups);
        setAdapterSpinner(mSpinnerClose, R.array.closing_schedule);
        setAdapterSpinner(mSpinnerOpen, R.array.opening_schedule);
        boolean isNewList = getIntent().getBooleanExtra(NEW_LIST, true);
        if (isNewList) {
            setSpinnerPositionsToDefault();
        } else {
            setSpinnerPositionToPrevious();
        }
        Button buttonApply = (Button) findViewById(R.id.filters_button_apply);
        Button buttonCancel = (Button) findViewById(R.id.filters_button_cancel);

        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreviousSpinnerPositions();
                setFiltersSelected();
                finish();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }

    /**
     * Pone los spinner a la selecion por defecto
     */
    public void setSpinnerPositionsToDefault() {
        mSpinnerMusic.setSelection(0);
        mSpinnerAge.setSelection(0);
        mSpinnerClose.setSelection(0);
        mSpinnerOpen.setSelection(0);
    }

    /**
     * Pone la posicion de los spinner a la posicion anterior
     */
    public void setSpinnerPositionToPrevious() {
        mSpinnerMusic.setSelection(mPreviousSpinnerPositions[0]);
        mSpinnerAge.setSelection(mPreviousSpinnerPositions[1]);
        mSpinnerClose.setSelection(mPreviousSpinnerPositions[2]);
        mSpinnerOpen.setSelection(mPreviousSpinnerPositions[3]);

    }

    /**
     * Guarda la posicion actual de los spinner
     */
    public void setPreviousSpinnerPositions() {
        mPreviousSpinnerPositions[0] = mSpinnerMusic.getSelectedItemPosition();
        mPreviousSpinnerPositions[1] = mSpinnerAge.getSelectedItemPosition();
        mPreviousSpinnerPositions[2] = mSpinnerClose.getSelectedItemPosition();
        mPreviousSpinnerPositions[3] = mSpinnerOpen.getSelectedItemPosition();
    }

    /**
     * Crea un intent con los filtos selecionados para comunicarse con la actividad padre
     */
    public void setFiltersSelected() {
        Intent data = new Intent();
        data.putExtra(MUSIC_SELECTED, MUSIC[mSpinnerMusic.getSelectedItemPosition()]);
        data.putExtra(AGE_SELECTED, EDAD[mSpinnerAge.getSelectedItemPosition()]);
        data.putExtra(CLOSE_SELECTED, HORA_CIERRE[mSpinnerClose.getSelectedItemPosition()]);
        data.putExtra(OPEN_SELECTED, HORA_APERTURA[mSpinnerOpen.getSelectedItemPosition()]);
        setResult(RESULT_OK, data);
    }


    /**
     * TODO: considerar coger los datos de la base de datos y no simples valores estaticos de XMl
     * Añade un adapter a un spinner
     *
     * @param spinner     Spinner al cual queremos añadir el adapter
     * @param stringArray id del recurso string array de XML
     */
    private void setAdapterSpinner(Spinner spinner, int stringArray) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                stringArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.quitar_filtros) {
            setSpinnerPositionsToDefault();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
