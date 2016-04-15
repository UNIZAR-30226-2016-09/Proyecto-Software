package bar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class FiltersActivity extends AppCompatActivity {

    private Spinner mSpinnerMusic;
    private Spinner mSpinnerAge;
    private Spinner mSpinnerClose;
    private Spinner mSpinnerOpen;

    private Button mButtonApply;
    private Button mButtonCancel;

    private static final String MUSIC_SELECTED = "MUSIC_SELECTED";
    private static final String AGE_SELECTED = "AGE_SELECTED";
    private static final String CLOSE_SELECTED = "CLOSE_SELECTED";
    private static final String OPEN_SELECTED = "OPEN_SELECTED";

    private static final String[] edad = {"all", "16", "18", "21"};

    private static final String[] horaCierre = {"all", "3", "4", "5", "7"};

    private static final String[] horaApertura = {"all", "22", "23", "0"};

    private static final String[] music = {"all"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_list);

        mSpinnerMusic = (Spinner) findViewById(R.id.spinner_music);
        mSpinnerAge = (Spinner) findViewById(R.id.spinner_age);
        mSpinnerClose = (Spinner) findViewById(R.id.spinner_close);
        mSpinnerOpen = (Spinner) findViewById(R.id.spinner_open);
        setAdapterSpinner(mSpinnerMusic, R.array.music_genre);
        setAdapterSpinner(mSpinnerAge, R.array.age_groups);
        setAdapterSpinner(mSpinnerClose, R.array.closing_schedule);
        setAdapterSpinner(mSpinnerOpen, R.array.opening_schedule);
        mButtonApply = (Button) findViewById(R.id.filters_button_apply);
        mButtonCancel = (Button) findViewById(R.id.filters_button_cancel);

        mButtonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFiltersSelected();
                finish();
            }
        });

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }

    public static HashMap<String, String> whatFiltersWhereSelected(Intent intent) {
        HashMap<String, String> aux = new HashMap<>();
        aux.put("Musica", intent.getStringExtra(MUSIC_SELECTED));
        aux.put("Edad", intent.getStringExtra(AGE_SELECTED));
        aux.put("HoraCierre", intent.getStringExtra(CLOSE_SELECTED));
        aux.put("HoraApertura", intent.getStringExtra(OPEN_SELECTED));
        return aux;
    }

    public void setFiltersSelected() {
        Intent data = new Intent();
        data.putExtra(MUSIC_SELECTED, edad[mSpinnerMusic.getSelectedItemPosition()]);
        data.putExtra(AGE_SELECTED, edad[mSpinnerAge.getSelectedItemPosition()]);
        data.putExtra(CLOSE_SELECTED, horaCierre[mSpinnerClose.getSelectedItemPosition()]);
        data.putExtra(OPEN_SELECTED, horaApertura[mSpinnerOpen.getSelectedItemPosition()]);
        setResult(RESULT_OK, data);
    }


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
            mSpinnerMusic.setSelection(0);
            mSpinnerAge.setSelection(0);
            mSpinnerClose.setSelection(0);
            mSpinnerOpen.setSelection(0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
