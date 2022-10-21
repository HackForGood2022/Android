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

public class GetPuebloPorNombreAsyncTask extends AsyncTask<String, Void, GetPuebloPorNombreAsyncTask.Result> {
    private MapaActivity mActivity = null;

    public static class Result {
        public String result;
        public Integer trabajos;
        public Integer picture;
        public String description;
    }

    public GetPuebloPorNombreAsyncTask(MapaActivity activity)
    {
        mActivity = activity;
    }

    private String nombreDelPueblo;

    protected GetPuebloPorNombreAsyncTask.Result doInBackground(String... params) {
        HttpURLConnection con;
        try {
            nombreDelPueblo = params[0];
            con = (HttpURLConnection) new URL("https://ruralapp-backend.herokuapp.com/api/general/get_pueblo").openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            String jsonInputString = "{\"pueblo\":\""+params[0]+"\"}";
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes();
                os.write(input, 0, input.length);
            }

            InputStreamReader reader = new InputStreamReader(con.getInputStream());
            Gson gson = new Gson();
            return gson.fromJson(reader, GetPuebloPorNombreAsyncTask.Result.class);

        } catch (IOException e) {
            Log.e("ERROR_GET_PUEBLOS",e.getMessage());
        } catch (Exception e) {
            Log.e("ERROR_GET_PUEBLOS",e.getMessage());
        }
        return new Result();
    }

    protected void onPostExecute(GetPuebloPorNombreAsyncTask.Result resultado) {
        mActivity.setUpInfoPueblo(resultado, nombreDelPueblo);
    }
}