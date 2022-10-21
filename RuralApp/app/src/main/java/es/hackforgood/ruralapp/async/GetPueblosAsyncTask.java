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
        HttpURLConnection con;
        try {
            con = (HttpURLConnection) new URL("https://ruralapp-backend.herokuapp.com/api/general/get_pueblos").openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            String jsonInputString = "{}";
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes();
                os.write(input, 0, input.length);
            }

            InputStreamReader reader = new InputStreamReader(con.getInputStream());
            Gson gson = new Gson();
            return gson.fromJson(reader, GetPueblosAsyncTask.Result.class);

        } catch (IOException e) {
            Log.e("ERROR_GET_PUEBLOS",e.getMessage());
        } catch (Exception e) {
            Log.e("ERROR_GET_PUEBLOS",e.getMessage());
        }
        return new Result();
    }

    protected void onPostExecute(GetPueblosAsyncTask.Result resultado) {
        mActivity.setupAdapter(resultado);
    }
}