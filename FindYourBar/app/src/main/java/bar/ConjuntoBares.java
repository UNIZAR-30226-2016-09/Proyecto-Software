package bar;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import java.util.ArrayList;
import java.util.HashMap;
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

    public List<Bar> getLocalBarList() {
        return mBares;
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

    public List<Bar> getBares() throws IOException, JSONException {
        String url = "http://ps1516.ddns.net:80/getBares.php";
        String json = getJson(new String[]{url, "all"});
        mBares = parseJson(json);
        return mBares;
    }

    /**
     * Devuelve una lista con bares cuyos nombres contienen el parametro "nombre"
     *
     * @param nombre String por la que se buscan los bares
     * @return lista de bares
     */
    public List<Bar> getBar(String nombre) {
        List<Bar> aux = new ArrayList<>();
        for (Bar b : mBares) {
            if (b.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                aux.add(b);
            }
        }
        return aux;
    }

    /**
     * Devuelve el bar que tiene el nombre igual que el parametro "nombre"
     *
     * @param nombre String, nombre del bar
     * @return null o Bar
     */
    public Bar getBarExact(String nombre) {
        for (Bar b : mBares) {
            if (b.getNombre().equals(nombre)) {
                return b;
            }
        }
        return null;
    }

    public List<Bar> enviarBares(String dato) throws IOException, JSONException {
        String url = "http://ps1516.ddns.net:80/getNames.php";
        String json = getJson(new String[]{url, dato});
        return parseJson(json);
    }

    /**
     * Pide al servidor el json con la informacion de los bares
     *
     * @param params array con la url del servidor y que informacion pedir
     * @return json con la infomacion del servidor
     * @throws IOException
     */
    private String getJson(String... params) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(params[0]);
        if (!params[1].equals("all")) {
            List<NameValuePair> postValues = new ArrayList<NameValuePair>(2);
            postValues.add(new BasicNameValuePair("nombre", params[1]));
            //Encapsulamos
            httppost.setEntity(new UrlEncodedFormEntity(postValues));
        }
        HttpResponse response = httpclient.execute(httppost);
        String jsonResult = inputStreamToString(
                response.getEntity().getContent()).toString();
        return jsonResult;
    }


    /**
     * Parse el json para
     *
     * @param json cadena a parsear
     * @return lista de los bares
     * @throws JSONException
     */
    private List<Bar> parseJson(String json) throws JSONException {
        List<Bar> bares = new ArrayList<>();
        List<String> arrayImagenes = new ArrayList<>();
        List<String> arrayEventos = new ArrayList<>();
        List<String> arrayMusica = new ArrayList<>();
        JSONObject jsonResponse = new JSONObject(json);
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
                Log.e("ruta", ruta);
                arrayImagenes.add(ruta);
            }
            JSONArray eventosArray = jsonChildNode.optJSONArray("eventos");
            for (int j = 0; j < eventosArray.length(); j++) {
                String evento = (String) eventosArray.getString(j);
                Log.e("evento", evento);
                arrayEventos.add(evento);
            }
            JSONArray musicaArray = jsonChildNode.optJSONArray("musica");
            for (int k = 0; k < musicaArray.length(); k++) {
                String musica = (String) musicaArray.getString(k);
                Log.e("musica", musica);
                arrayMusica.add(musica);
            }
            Log.e("nombre", name);
            Log.e("imagen", imaPrincipal);
            bares.add(new Bar(name, des, dir, tl, e, fb, imaPrincipal, arrayImagenes, arrayEventos,
                    Integer.parseInt(ed), Float.parseFloat(hc), Float.parseFloat(ha), arrayMusica));
        }
        return bares;

    }

    /**
     * Transforma un InputStream a una string
     *
     * @param is InputStream a transformar
     * @return String del InputStream
     */
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
