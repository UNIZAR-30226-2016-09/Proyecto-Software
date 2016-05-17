package database;


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
import java.util.List;

import bar.Bar;

/**
 * Clase que va a guardar la lista de bares.
 */
public class ConjuntoBares {

    private static ConjuntoBares sConjuntoBares;

    private static List<Bar> mBares;

    private ConjuntoBares() {
        mBares = new ArrayList<>();
    }

    public static ConjuntoBares getInstance() {
        if (sConjuntoBares == null) {
            sConjuntoBares = new ConjuntoBares();
        }
        return sConjuntoBares;
    }

    public List<Bar> getLocalBarList() {
        return mBares;
    }

    /**
     * Devuelve una lista de bares de la base de datos remota
     *
     * @return lista con todos los bares
     * @throws IOException   errores de conexion con la base de datos
     * @throws JSONException erroes de parsing de la informacion
     */
    public List<Bar> getBares() throws IOException, JSONException {
        String url = "http://ps1516.ddns.net:80/getBares.php";
        String json = getJson(url, "all");
        mBares = parseJson(json);
        return mBares;
    }

    public List<String> getMusica() throws IOException, JSONException {
        String url = "http://ps1516.ddns.net:80/getTipoMusica.php";
        String json = getJson(url, "all");
        return parseJsonMusic(json);

    }

    private List<String> parseJsonMusic(String json) throws JSONException {
        List<String> generosMusica = new ArrayList<>();
        JSONObject jsonResponse = new JSONObject(json);
        JSONArray jsonMainNode = jsonResponse.optJSONArray("Musica");
        for (int i = 0; i < jsonMainNode.length(); i++) {
            JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
            String genero = jsonChildNode.optString("musica");
            generosMusica.add(genero);
        }
        return generosMusica;

    }


    private String getJsonMusica(String url) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        List<NameValuePair> postValues = new ArrayList<>(2);
        httppost.setEntity(new UrlEncodedFormEntity(postValues));
        HttpResponse response = httpclient.execute(httppost);
        return inputStreamToString(response.getEntity().getContent()).toString();
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
        String json = getJson(url, dato);
        return parseJson(json);
    }

    /**
     * Devuelve una lista de bares de la base de datos remota con los parametros igual a los
     * especificados
     *
     * @param musica       musica del bar
     * @param edad         edad de entrada del bar
     * @param horaCierre   hora de cierre del bar
     * @param horaApertura hora de apertura del bar
     * @return lista con los bares filtrados
     * @throws IOException   errores de conexion
     * @throws JSONException erroes de parsing de la informacion
     */
    public List<Bar> filtrarBares(String musica, String edad, String horaCierre, String horaApertura)
            throws IOException, JSONException {
        String url = "http://ps1516.ddns.net:80/getFiltros.php";
        String json = getJson(url, musica, edad, horaCierre, horaApertura);
        mBares = parseJson(json);
        return mBares;
    }

    public void addBar(Bar bar) throws IOException {
        String url = "http://ps1516.ddns.net:80/addBar.php";
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> postValues = new ArrayList<>();
        postValues.add(new BasicNameValuePair("nombre", bar.getNombre()));
        postValues.add(new BasicNameValuePair("descripcion", bar.getDescripcion()));
        postValues.add(new BasicNameValuePair("direccion", bar.getDireccion()));
        postValues.add(new BasicNameValuePair("edad", String.valueOf(bar.getEdad())));
        postValues.add(new BasicNameValuePair("horarioApertura", String.valueOf(bar.getHoraApertura())));
        postValues.add(new BasicNameValuePair("horarioCierre", String.valueOf(bar.getHoraApertura())));
        for (String s : bar.getMusica()) {
            postValues.add(new BasicNameValuePair("musica[]", s));
        }
        postValues.add(new BasicNameValuePair("imgPrinci", bar.getPrincipal()));
        for (String s : bar.getSecundaria()) {
            Log.e("afd", "addBar: " + s);
            postValues.add(new BasicNameValuePair("imgSecun[]", s));
        }
        postValues.add(new BasicNameValuePair("telefono", bar.getTelefono()));
        postValues.add(new BasicNameValuePair("email", bar.getEmail()));
        for (String s : bar.getEventos()) {
            postValues.add(new BasicNameValuePair("evento[]", s));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(postValues));
        httpClient.execute(httpPost);
        mBares.add(bar);

    }

    public void removeBar(String nombre) throws IOException {
        String url = "http://ps1516.ddns.net:80/deleteBar.php";
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> postValues = new ArrayList<>();
        postValues.add(new BasicNameValuePair("nombre", nombre));
        httpPost.setEntity(new UrlEncodedFormEntity(postValues));
        httpClient.execute(httpPost);
        for (int i = 0; i < mBares.size(); i++) {
            if (mBares.get(i).getNombre().equals(nombre)) {
                mBares.remove(i);
            }
        }

    }

    /**
     * Pide al servidor el json con la informacion de los bares
     *
     * @param params array con la url del servidor y que informacion pedir
     * @return json con la infomacion del servidor
     */
    private String getJson(String... params) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(params[0]);
        if (params.length > 2) {
            List<NameValuePair> postValues = new ArrayList<>(2);
            postValues.add(new BasicNameValuePair("tM", params[1]));
            postValues.add(new BasicNameValuePair("edad", params[2]));
            postValues.add(new BasicNameValuePair("hC", params[3]));
            postValues.add(new BasicNameValuePair("hA", params[4]));
            httppost.setEntity(new UrlEncodedFormEntity(postValues));
        } else if (!params[1].equals("all")) {
            List<NameValuePair> postValues = new ArrayList<>(2);
            postValues.add(new BasicNameValuePair("nombre", params[1]));
            //Encapsulamos
            httppost.setEntity(new UrlEncodedFormEntity(postValues));
        }
        HttpResponse response = httpclient.execute(httppost);
        return inputStreamToString(response.getEntity().getContent()).toString();
    }


    /**
     * Parse el json para coger la informacion de los bares
     *
     * @param json cadena a parsear
     * @return lista de los bares
     */
    private List<Bar> parseJson(String json) throws JSONException {
        List<Bar> bares = new ArrayList<>();
        JSONObject jsonResponse = new JSONObject(json);
        JSONArray jsonMainNode = jsonResponse.optJSONArray("Bar");
        for (int i = 0; i < jsonMainNode.length(); i++) {
            List<String> arrayImagenes = new ArrayList<>();
            List<String> arrayEventos = new ArrayList<>();
            List<String> arrayMusica = new ArrayList<>();
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
                String ruta = imagenesArray.getString(j);
                arrayImagenes.add(ruta);
            }
            JSONArray eventosArray = jsonChildNode.optJSONArray("eventos");
            for (int j = 0; j < eventosArray.length(); j++) {
                String evento = eventosArray.getString(j);
                arrayEventos.add(evento);
            }
            JSONArray musicaArray = jsonChildNode.optJSONArray("musica");
            for (int k = 0; k < musicaArray.length(); k++) {
                String musica = musicaArray.getString(k);
                arrayMusica.add(musica);
            }
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
    private StringBuilder inputStreamToString(InputStream is) throws IOException {
        String rLine;
        StringBuilder answer = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        while ((rLine = rd.readLine()) != null) {
            answer.append(rLine);
        }
        return answer;
    }
}
