package br.com.thideoli.vendedormovel.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.thideoli.vendedormovel.R;
import br.com.thideoli.vendedormovel.model.ProdutoPedido;

public class ProdutoPedidoAdapter extends BaseAdapter{

    private final List<ProdutoPedido> produtosPedidos;
    private Context context;

    public ProdutoPedidoAdapter(Context context, List<ProdutoPedido> produtosPedidos) {
        this.produtosPedidos = produtosPedidos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.produtosPedidos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.produtosPedidos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ProdutoPedido produtoPedido = produtosPedidos.get(position);

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View view = layoutInflater.inflate(R.layout.list_products_order, null);

        TextView descricao = view.findViewById(R.id.product_name);
        descricao.setText(produtoPedido.getProduto());

        TextView preco = view.findViewById(R.id.product_price);
        preco.setText(String.valueOf(produtoPedido.getPreco()));

        TextView qtde = view.findViewById(R.id.product_qtde);
        qtde.setText(String.valueOf(produtoPedido.getQuantidade()));

        TextView subtotal = view.findViewById(R.id.product_subtotal);
        subtotal.setText(String.valueOf(produtoPedido.getSubtotal()));

        return view;
    }
}
