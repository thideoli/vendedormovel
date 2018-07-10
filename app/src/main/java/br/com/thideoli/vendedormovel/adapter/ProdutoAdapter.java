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
import br.com.thideoli.vendedormovel.model.Produto;

public class ProdutoAdapter extends BaseAdapter{

    private final List<Produto> produtos;
    private Context context;

    public ProdutoAdapter(Context context, List<Produto> produtos) {
        this.produtos = produtos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.produtos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.produtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Produto produto = produtos.get(position);

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View view = layoutInflater.inflate(R.layout.list_products, null);

        TextView descricao = view.findViewById(R.id.product_name);
        descricao.setText(produto.getDescricao());

        TextView estoque = view.findViewById(R.id.product_stock);
        estoque.setTextColor(Color.BLUE);
        estoque.setText("Disponível");
        if(produto.getEstoque() == 0) {
            estoque.setTextColor(Color.RED);
            estoque.setText("Indisponível");
        }

        return view;
    }
}
