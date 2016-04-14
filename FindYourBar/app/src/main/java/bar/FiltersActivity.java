package bar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class FiltersActivity extends AppCompatActivity {

    private Spinner mSpinnerMusic;
    private Spinner mSpinnerAge;
    private Spinner mSpinnerClose;
    private Spinner mSpinnerOpen;

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
