package bar;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchBar extends AppCompatActivity {

    private RecyclerView mList;
    private BarAdapter mAdapter;
    // Identificadores para los fragmentos
    private static final String menu_name = "MENU_NAME";
    private static final String menu_open = "MENU_OPEN";
    private static final String menu_close = "MENU_CLOSE";
    private static final String menu_music = "MENU_MUSIC";
    private static final String menu_age = "MENU_AGE";

    private String jsonResult;
    private String url = "http://ps1516.ddns.net:80/getBares.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        accessWebService();
        mList = (RecyclerView) findViewById(R.id.recyclerlist);
        mList.setLayoutManager(new LinearLayoutManager(this));
        fillData();
        ConjuntoBares.verBares();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_name:
                DialogFragment name = new SearchNameDialogFragment();
                name.show(getSupportFragmentManager(), menu_name);
                return true;
            case R.id.menu_open:
                DialogFragment opening = new SearchOpeningDialogFragment();
                opening.show(getSupportFragmentManager(), menu_open);
                return true;
            case R.id.menu_close:
                DialogFragment closing = new SearchClosingDialogFragment();
                closing.show(getSupportFragmentManager(), menu_close);
                return true;
            case R.id.menu_music:
                DialogFragment music = new SearchMusicDialogFragment();
                music.show(getSupportFragmentManager(), menu_music);
                return true;
            case R.id.menu_age:
                DialogFragment age = new SearchAgeDialogFragment();
                age.show(getSupportFragmentManager(), menu_age);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        return true;
    }


    /**
     * Rellena el la lista de bares
     */
    private void fillData() {
        mAdapter = new BarAdapter(ConjuntoBares.getInstance().getBares());
        mList.setAdapter(mAdapter);
    }

    private class BarHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mNombre;

        public BarHolder(View item) {
            super(item);
            mNombre = (TextView) item.findViewById(R.id.list_nombre_bar);
            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            startActivity(BarActivity.newIntent(SearchBar.this, mNombre.getText().toString()));
        }
    }

    private class BarAdapter extends RecyclerView.Adapter<BarHolder> {
        private List<Bar> bares;

        public BarAdapter(List<Bar> bares) {
            this.bares = bares;
        }

        @Override
        public BarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(SearchBar.this);
            // TODO: crear un layout para mostrar tambein una imagen del bar junto al nombre
            View view = li.inflate(R.layout.bar_list_item, parent, false);
            return new BarHolder(view);
        }

        @Override
        public void onBindViewHolder(BarHolder holder, int position) {
            Bar b = bares.get(position);
            holder.mNombre.setText(b.getNombre());
        }

        @Override
        public int getItemCount() {
            return bares.size();
        }
    }

    public void accessWebService() {
        JsonReadTask task = new JsonReadTask();
        // passes values for the urls string array
        task.execute(new String[]{url});
    }

    // Async Task to access the web
    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
            try {
                HttpResponse response = httpclient.execute(httppost);
                jsonResult = inputStreamToString(
                        response.getEntity().getContent()).toString();
                Log.e("jsonResult", jsonResult);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            try {
                while ((rLine = rd.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                // e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Error..." + e.toString(), Toast.LENGTH_LONG).show();
            }
            return answer;
        }

        @Override
        protected void onPostExecute(String result) {
            ListDrwaer();
            fillData();
        }
    }// end async task

    public void ListDrwaer() {
        //List<Map<String, String>> BarList = new ArrayList<Map<String, String>>();

        try {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("Bar");
            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                String name = jsonChildNode.optString("nombre");
                String des = jsonChildNode.optString("descripcion");
                String dir = jsonChildNode.optString("direccion");
                String ed = jsonChildNode.optString("edad");
                String ha = jsonChildNode.optString("horarioApertura");
                String hc = jsonChildNode.optString("horarioCierre");
                String e = jsonChildNode.optString("email");
                String fb = jsonChildNode.optString("facebook");
                String tl = jsonChildNode.optString("telefono");

                /*String number = jsonChildNode.optString("employee no");
                String outPut = name + "-" + number;*/
                Log.e("nombre", name);

                ConjuntoBares.addBar(new Bar(name, des));
                //BarList.add(createBar("nombre", name));
            }
            ConjuntoBares.verBares();

        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }
        private HashMap<String, String> createBar (String name, String number){
            HashMap<String, String> bar = new HashMap<String, String>();
            bar.put(name, number);
            return bar;
        }

}

/*
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, BarList,
                android.R.layout.simple_list_item_1,
                new String[]{"bares"}, new int[]{android.R.id.text1});
        listView.setAdapter(simpleAdapter);
*/