package bar;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que va a guardar la lista de bares.
 * TODO: cambiar cuando se añada la interfaz para el acceso a la base de datos
 */
public class ConjuntoBares {

    private static ConjuntoBares sConjuntoBares;
    private String jsonResult;
    //private String url = "http://192.168.1.38/p.php";
    private String url = "http://10.0.2.2:5107/p2.php";

    private static List<Bar> mBares;

    private ConjuntoBares() {
        mBares = new ArrayList<>();
        /*for (int i = 0; i < 100; i++) {
            mBares.add(new Bar("Bar " + i, "Descripcion " + i));
        }*/
    }

    public static void addBar(Bar b){
        mBares.add(b);
    }

    public static void verBares(){
        for (Bar b : mBares) {
            Log.e("nombreBar",b.getNombre());
        }
    }

    public static ConjuntoBares getInstance() {
        if (sConjuntoBares == null) {
            sConjuntoBares = new ConjuntoBares();
        }
        return sConjuntoBares;
    }

    public List<Bar> getBares() {
        return mBares;
    }

    public Bar getBar(String nombre) {
        for (Bar b : mBares) {
            if (b.getNombre().equals(nombre)) {
                return b;
            }
        }
        return null;
    }
}


/*
    // Async Task to access the web
    public class JsonReadTask extends AsyncTask<String, Void, String> {
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
        }
    }// end async task

    public void ListDrwaer() {
        List<Map<String, String>> BarList = new ArrayList<Map<String, String>>();

        try {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("Bar");
            Log.e("BARES", "n");
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
 /*               Log.e("nombre", name);
                Log.e("des", des);
                Log.e("dir", dir);
                Log.e("ed", ed);
                Log.e("ha", ha);
                Log.e("hc", hc);
                Log.e("e", e);
                Log.e("fb", fb);
                Log.e("tl", tl);

                BarList.add(createBar("nombre", name));
            }
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


}*/
