package br.com.thideoli.vendedormovel.utils;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Receiver extends AsyncTask {
    public AsyncResponse delegate = null;

    @Override
    protected Object doInBackground(Object[] objects) {

        Map<String, String> retorno = new HashMap<String, String>();
        BufferedReader bufferedReader;
        String line;
        StringBuilder content;

        try {
            bufferedReader = getBufferedReader("https://vendedor-movel.firebaseio.com/produtos.json");
            content = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null)
                content.append(line);
            bufferedReader.close();
            retorno.put("produtos", content.toString());

            bufferedReader = getBufferedReader("https://vendedor-movel.firebaseio.com/clientes.json");
            content = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null)
                content.append(line);
            bufferedReader.close();
            retorno.put("clientes", content.toString());

            return retorno;

        } catch (Exception e) {
            return null;
        }
    }

    @NonNull
    private BufferedReader getBufferedReader(String spec) throws IOException {
        URL url = new URL(spec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-type", "application/json");
        connection.setDoInput(true);
        return new BufferedReader(new InputStreamReader(connection.getInputStream()));
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        delegate.processFinish(o);
    }
}
