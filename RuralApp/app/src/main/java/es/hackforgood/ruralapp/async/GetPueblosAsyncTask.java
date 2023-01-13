package es.hackforgood.ruralapp.async;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import es.hackforgood.ruralapp.MapaActivity;

public class GetPueblosAsyncTask extends AsyncTask<String, Void, GetPueblosAsyncTask.Result> {
    private MapaActivity mActivity = null;

    private String jsonHard = "{" +
            "result: \"success\"," +
            "pueblos: [" +
            "{ name: \"Villanueva de Sigena\", latitud: \"41.715278\", longitud: \"-0.008889\", cantidad: \"2\" },\n" +
            "{ name: \"Alcolea\", latitud: \"36.966667\", longitud: \"-2.95\", cantidad: \"1\" },\n" +
            "{ name: \"La Puebla de Hijar\", latitud: \"41.213239\", longitud: \"-0.445625\", cantidad: \"2\" },\n" +
            "{ name: \"Fago\", latitud: \"42.735278\", longitud: \"-0.865556\", cantidad: \"1\" },\n" +
            "{ name: \"Canfranc\", latitud: \"42.716656\", longitud: \"-0.525198\", cantidad: \"5\" }\n" +
            "]}";

    public static class Pueblo {
        public String name;
        public String latitud;
        public String longitud;
        public String cantidad;
    }
    public static class Result {
        public String result;
        public List<Pueblo> pueblos;
    }

    public GetPueblosAsyncTask(MapaActivity activity)
    {
        mActivity = activity;
    }

    protected GetPueblosAsyncTask.Result doInBackground(String... params) {
        //HttpURLConnection con;
        try {
            /*
            con = (HttpURLConnection) new URL("https://ruralapp-backend.herokuapp.com/api/general/get_pueblos").openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);*/

            /*
            String jsonInputString = "{}";
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes();
                os.write(input, 0, input.length);
            }*/

            //InputStreamReader reader = new InputStreamReader(con.getInputStream());
            Gson gson = new Gson();
            return gson.fromJson(jsonHard, GetPueblosAsyncTask.Result.class);

        } /*catch (IOException e) {
            Log.e("ERROR_GET_PUEBLOS",e.getMessage());
        }*/ catch (Exception e) {
            Log.e("ERROR_GET_PUEBLOS",e.getMessage());
        }
        return new Result();
    }

    protected void onPostExecute(GetPueblosAsyncTask.Result resultado) {
        mActivity.setupAdapter(resultado);
    }
}