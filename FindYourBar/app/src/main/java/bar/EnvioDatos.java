package bar;

import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
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
import java.util.logging.Handler;

/**
 * Created by anicacortes on 04/04/2016.
 */
public class EnvioDatos {

    private static String url = "http://192.168.0.19:5107/getNames.php";
    private static String jsonResult;



    // Async Task to access the web
    private static class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
            try {
                List<NameValuePair> postValues = new ArrayList<NameValuePair>(2);
                postValues.add(new BasicNameValuePair("nombre", params[1]));
                //Encapsulamos
                httppost.setEntity(new UrlEncodedFormEntity(postValues));
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
            }
            return answer;
        }

        @Override
        protected void onPostExecute(String result) {
            ListDrwaer();
            //SearchBar.fillData();
            Log.e("Post","execute");
        }
    }// end async task

    public static void ListDrwaer() {
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

                ConjuntoBares.addBar(new Bar(name, des));
            }
            ConjuntoBares.verBares();

        } catch (JSONException e) {
        }

    }

    public static void enviarDatos(String dato){

        JsonReadTask task = new JsonReadTask();
        // passes values for the urls string array
        task.execute(new String[]{url, dato});

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
            Log.e("ERROR","ERROR");
        }
        return answer;
    }
}

