package br.com.thideoli.vendedormovel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.thideoli.vendedormovel.adapter.ProdutoAdapter;
import br.com.thideoli.vendedormovel.dao.ProdutoDAO;
import br.com.thideoli.vendedormovel.model.Produto;

public class ProductsActivity extends AppCompatActivity {

    private ListView lvProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.products);
        setContentView(R.layout.activity_products);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ProdutoDAO produtoDAO = new ProdutoDAO(ProductsActivity.this);

        List<Produto> produtos = produtoDAO.listAll();

        lvProdutos = findViewById(R.id.list_products);
        ProdutoAdapter arrayAdapter = new ProdutoAdapter(ProductsActivity.this, produtos);
        lvProdutos.setAdapter(arrayAdapter);
        lvProdutos.setOnItemClickListener(chamaTelaDetalheProduto());

    }

    private AdapterView.OnItemClickListener chamaTelaDetalheProduto() {
        return new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produto = (Produto) lvProdutos.getItemAtPosition(position);

                Intent intent = new Intent(ProductsActivity.this, ProductActivity.class);
                intent.putExtra("produto", produto);
                startActivity(intent);
            }
        };
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
