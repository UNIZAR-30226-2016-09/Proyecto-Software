package bar;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que va a guardar la lista de bares.
 */
public class ConjuntoBares {

    private static ConjuntoBares sConjuntoBares;

    private static List<Bar> mBares;

    private ConjuntoBares() {
        mBares = new ArrayList<>();
    }

    public void addBar(Bar b) {
        mBares.add(b);
    }

    public static void verBares() {
        for (Bar b : mBares) {
            Log.e("nombreBar", b.getNombre());
        }
    }

    public static ConjuntoBares getInstance() {
        if (sConjuntoBares == null) {
            sConjuntoBares = new ConjuntoBares();
        }
        return sConjuntoBares;
    }

    public List<Bar> getBares() {
        mBares = accessWebService();
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

    private List<Bar> accessWebService() {
        String url = "http://ps1516.ddns.net:80/getBares.php";
        //String url = "http://10.1.62.22:5107/getBares.php";
        return getJsonResult(new String[]{url, "all"});
    }

    public List<Bar> enviarBares(String dato) {
        //String url = "http://10.1.62.22:5107/getBares.php";
        String url = "http://ps1516.ddns.net:80/getNames.php";
        return getJsonResult(new String[]{url, dato});
    }



    private List<Bar> getJsonResult(String... params) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(params[0]);
        List<Bar> bares = new ArrayList<>();
        try {
            if (!params[1].equals("all")) {
                List<NameValuePair> postValues = new ArrayList<NameValuePair>(2);
                postValues.add(new BasicNameValuePair("nombre", params[1]));
                //Encapsulamos
                httppost.setEntity(new UrlEncodedFormEntity(postValues));
            }
            HttpResponse response = httpclient.execute(httppost);
            String jsonResult = inputStreamToString(
                    response.getEntity().getContent()).toString();
            Log.e("jsonResult", jsonResult);

            // Parsing del json con la informacion de los bares

            List<String> arrayImagenes = new ArrayList<>();
            List<String> arrayEventos = new ArrayList<>();
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
                String imaPrincipal = jsonChildNode.optString("imagenId");
                JSONArray imagenesArray = jsonChildNode.optJSONArray("secundaria");
                for (int j = 0; j < imagenesArray.length(); j++) {
                    String ruta = (String) imagenesArray.getString(j);
                    Log.e("ruta",ruta);
                    arrayImagenes.add(ruta);
                }
                JSONArray eventosArray = jsonChildNode.optJSONArray("eventos");
                for (int j = 0; j < eventosArray.length(); j++) {
                    String evento = (String) eventosArray.getString(j);
                    Log.e("evento",evento);
                    arrayEventos.add(evento);
                }
                Log.e("nombre", name);
                Log.e("imagen", imaPrincipal);
                bares.add(new Bar(name, des, dir, tl, e, fb,imaPrincipal, arrayImagenes, arrayEventos));
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bares;
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
        }
        return answer;
    }
}
