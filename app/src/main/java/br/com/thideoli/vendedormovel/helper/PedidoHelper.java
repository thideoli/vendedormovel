package br.com.thideoli.vendedormovel.helper;

import android.content.Context;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;
import java.util.Set;

import br.com.thideoli.vendedormovel.dao.PedidoDAO;
import br.com.thideoli.vendedormovel.model.Pedido;

public class PedidoHelper {

    private static Context context;

    public PedidoHelper(Context context) {
        this.context = context;
    }

    public void sendJsonToDB(String pedidos) throws Exception {

        if(pedidos.equals("null"))
            return;

        JsonParser jp = new JsonParser();
        JsonObject jo = jp.parse(pedidos).getAsJsonObject();

        Set<Map.Entry<String, JsonElement>> entries = jo.entrySet();

        for (Map.Entry<String, JsonElement> entry: entries) {

            JsonObject joPedido = jo.getAsJsonObject(entry.getKey());

            Pedido pedido = new Pedido();
            pedido.setCodigo(entry.getKey());

            PedidoDAO pedidoDAO = new PedidoDAO(context);

            if(pedidoDAO.findByCode(pedido.getCodigo()) == null)
                pedidoDAO.insert(pedido);
            else
                pedidoDAO.update(pedido);

        }
    }

}
