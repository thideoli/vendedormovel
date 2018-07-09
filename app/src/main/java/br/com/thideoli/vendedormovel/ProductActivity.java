package br.com.thideoli.vendedormovel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import br.com.thideoli.vendedormovel.model.Produto;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Produto");
        setContentView(R.layout.activity_product);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Produto produto = (Produto) intent.getSerializableExtra("produto");

        TextView codigo = findViewById(R.id.order_detail_code);
        TextView descricao = findViewById(R.id.order_detail_name);
        TextView preco = findViewById(R.id.order_detail_address);
        TextView estoque = findViewById(R.id.product_detail_stock);

        codigo.setText(produto.getCodigo());
        descricao.setText(produto.getDescricao());
        preco.setText(String.valueOf(produto.getPreco()));

        estoque.setTextColor(Color.BLUE);
        estoque.setText("Disponível");
        if(produto.getEstoque() == 0) {
            estoque.setTextColor(Color.RED);
            estoque.setText("Indisponível");
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
