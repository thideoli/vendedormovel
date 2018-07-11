package br.com.thideoli.vendedormovel.helper;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.thideoli.vendedormovel.dao.PedidoDAO;
import br.com.thideoli.vendedormovel.dao.ProdutoPedidoDAO;
import br.com.thideoli.vendedormovel.model.Pedido;
import br.com.thideoli.vendedormovel.model.ProdutoPedido;

public class PedidoHelper {

    private static Context context;

    public PedidoHelper(Context context) {
        this.context = context;
    }

    public static String toJsonString(Pedido pedido) {
        JSONStringer json = new JSONStringer();

        try {
            json.object()
                    .key("cliente").value(pedido.getCliente())
                    .key("data").value(pedido.getData())
                    .key("enviado").value(1)
                    .key("total").value(pedido.getTotal())
                    .key("vendedor").value(pedido.getVendedor())
                    .key("itens").array();

            for (ProdutoPedido produtoPedido : pedido.getProdutosPedido()) {
                json.object()
                        .key("descricao").value(produtoPedido.getProduto())
                        .key("preco").value(produtoPedido.getPreco())
                        .key("quantidade").value(produtoPedido.getQuantidade())
                        .key("subtotal").value(produtoPedido.getSubtotal())
                        .endObject();
            }

            json.endArray()
                    .endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString();
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
            pedido.setCliente(joPedido.get("cliente").getAsString());
            pedido.setVendedor(joPedido.get("vendedor").getAsString());
            pedido.setData(joPedido.get("data").getAsString());
            pedido.setTotal(joPedido.get("total").getAsDouble());
            pedido.setEnviado(joPedido.get("enviado").getAsInt());


            List<ProdutoPedido> produtosPedido = new ArrayList<ProdutoPedido>();

            if(joPedido.has("itens")) {

                JsonArray jaProdutos = joPedido.getAsJsonArray("itens");

                ProdutoPedidoDAO produtoPedidoDAO = new ProdutoPedidoDAO(context);
                produtoPedidoDAO.deleteByPedido(entry.getKey());


                for (Object o : jaProdutos) {
                    JsonObject joProduto = (JsonObject) o;

                    ProdutoPedido produtoPedido = new ProdutoPedido();
                    produtoPedido.setPedido(entry.getKey());
                    produtoPedido.setProduto(joProduto.get("descricao").getAsString());
                    produtoPedido.setPreco(joProduto.get("preco").getAsDouble());
                    produtoPedido.setQuantidade(joProduto.get("quantidade").getAsInt());
                    produtoPedido.setSubtotal(joProduto.get("subtotal").getAsDouble());

                    produtosPedido.add(produtoPedido);

                    produtoPedidoDAO.insert(produtoPedido);

                }

            }
            pedido.setProdutosPedido(produtosPedido);



            PedidoDAO pedidoDAO = new PedidoDAO(context);

            if(pedidoDAO.findByCode(pedido.getCodigo()) == null)
                pedidoDAO.insert(pedido);
            else
                pedidoDAO.update(pedido);

        }
    }

}
