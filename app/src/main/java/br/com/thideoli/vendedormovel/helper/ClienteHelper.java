package br.com.thideoli.vendedormovel.helper;

import android.content.Context;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;
import java.util.Set;

import br.com.thideoli.vendedormovel.dao.ClienteDAO;
import br.com.thideoli.vendedormovel.model.Cliente;

public class ClienteHelper {

    private static Context context;

    public ClienteHelper(Context context) {
        this.context = context;
    }

    public void sendJsonToDB(String produtos) throws Exception {
        JsonParser jp = new JsonParser();
        JsonObject jo = jp.parse(produtos).getAsJsonObject();

        Set<Map.Entry<String, JsonElement>> entries = jo.entrySet();

        for (Map.Entry<String, JsonElement> entry: entries) {

            JsonObject joProduto = jo.getAsJsonObject(entry.getKey());

            Cliente cliente = new Cliente();
            cliente.setCodigo(entry.getKey());
            cliente.setRazaoSocial(joProduto.get("razao_social").getAsString());
            cliente.setCnpj(joProduto.get("cnpj").getAsString());
            cliente.setEndereco(joProduto.get("endereco").getAsString());
            cliente.setTelefone(joProduto.get("telefone").getAsString());
            cliente.setEmail(joProduto.get("email").getAsString());

            ClienteDAO clienteDAO = new ClienteDAO(context);

            if(clienteDAO.findByCode(cliente.getCodigo()) == null)
                clienteDAO.insert(cliente);
            else
                clienteDAO.update(cliente);

        }
    }

}
