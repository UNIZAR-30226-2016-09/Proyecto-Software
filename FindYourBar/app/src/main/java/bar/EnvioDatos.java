package bar;

import android.os.AsyncTask;
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
import java.util.ArrayList;
import java.util.List;

public class EnvioDatos {

    //private static String url = "http://ip:5107/getNames.php";
    //private static String url = "http://ps1516.ddns.net:80/getNames.php";
    private static String jsonResult;

    public static void accessWebService() {
        String url = "http://ps1516.ddns.net:80/getBares.php";
        //JsonReadTask task = new JsonReadTask();
        // passes values for the urls string arrayg
        getJsonResult(new String[]{url, "all"});

    }

    /**
     * Recibe el bar a buscar en la base de datos
     */
    public static void enviarBares(String dato) {
        String url = "http://ps1516.ddns.net:80/getNames.php";
        //JsonReadTask task = new JsonReadTask();
        // passes values for the urls string array
        //task.execute(new String[]{url, dato});
    }

    /**
     * Recibe la edad
     */
    public static void enviarEdad(String dato) {
        String url = "http://ps1516.ddns.net:80/getEdad.php";
        //JsonReadTask task = new JsonReadTask();
        // passes values for the urls string array
        //task.execute(new String[]{url, dato});
    }

    /**
     * Recibe el tipo de musica
     */
    public static void enviarMusica(String dato){
        String url = "http://ps1516.ddns.net:80/getMusica.php";
        //JsonReadTask task = new JsonReadTask();
        // passes values for the urls string array
        //task.execute(new String[]{url, dato});
    }

    /**
     * Recibe la hora de apertura
     */
    public static void enviarHoraApertura(String dato1){
        String url = "http://ps1516.ddns.net:80/getHA.php";
        //JsonReadTask task = new JsonReadTask();
        // passes values for the urls string array
       //task.execute(new String[]{url, dato1});
    }

    /**
     * Recibe la hora de cierre
     */
    public static void enviarHoraCierre(String dato1, String dato2){
        String url = "http://ps1516.ddns.net:80/getHC.php";
        //JsonReadTask task = new JsonReadTask();
        // passes values for the urls string array
        //task.execute(new String[]{url, "horas", dato1, dato2});
    }


    private static String getJsonResult(String... params) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(params[0]);
        try {
            if (!params[1].equals("all")) {
                List<NameValuePair> postValues = new ArrayList<NameValuePair>(2);
                postValues.add(new BasicNameValuePair("nombre", params[1]));
                //Encapsulamos
                httppost.setEntity(new UrlEncodedFormEntity(postValues));
            }
            HttpResponse response = httpclient.execute(httppost);
            jsonResult = inputStreamToString(
                    response.getEntity().getContent()).toString();
            Log.e("jsonResult", jsonResult);

            // Parsing del json con la informacion de los bares
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

                    Log.e("nombre", name);

                    ConjuntoBares.getInstance().addBar(new Bar(name, des, dir, tl, e, fb));
                }

            } catch (JSONException e) {
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static StringBuilder inputStreamToString(InputStream is) {
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

