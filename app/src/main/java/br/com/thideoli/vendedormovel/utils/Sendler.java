package br.com.thideoli.vendedormovel.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Sendler extends AsyncTask {
    private final String json;
    public AsyncResponse delegate = null;

    public Sendler(String json) {
        this.json = json;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            final URL url = new URL("https://vendedor-movel.firebaseio.com/pedidos.json");
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-type", "application/json");

            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            final OutputStream outputStream = connection.getOutputStream();
            final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            writer.write(json);
            writer.flush();
            writer.close();
            outputStream.close();

            connection.connect();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;

            StringBuilder content = new StringBuilder();


            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line);
            }
            bufferedReader.close();

            Map<String, String> retorno = new HashMap<String, String>();
            retorno.put("enviado", "sim");
            return retorno;

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        delegate.processFinish(o);
    }

}
