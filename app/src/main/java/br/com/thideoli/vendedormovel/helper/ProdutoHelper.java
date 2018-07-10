package br.com.thideoli.vendedormovel.helper;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.thideoli.vendedormovel.dao.ProdutoDAO;
import br.com.thideoli.vendedormovel.model.Produto;

public class ProdutoHelper {

    private static Context context;

    public ProdutoHelper(Context context) {
        this.context = context;
    }

    public void sendJsonToDB(String produtos) throws Exception {

        if(produtos.equals("null"))
            return;

        JsonParser jp = new JsonParser();
        JsonObject jo = jp.parse(produtos).getAsJsonObject();

        Set<Map.Entry<String, JsonElement>> entries = jo.entrySet();

        for (Map.Entry<String, JsonElement> entry: entries) {

            JsonObject joProduto = jo.getAsJsonObject(entry.getKey());

            Produto produto = new Produto();
            produto.setCodigo(entry.getKey());
            produto.setDescricao(joProduto.get("descricao").getAsString());
            produto.setEstoque(joProduto.get("estoque").getAsInt());
            produto.setPreco(joProduto.get("preco").getAsDouble());

            ProdutoDAO produtoDAO = new ProdutoDAO(context);

            if(produtoDAO.findByCode(produto.getCodigo()) == null)
                produtoDAO.insert(produto);
            else
                produtoDAO.update(produto);

        }
    }

}
