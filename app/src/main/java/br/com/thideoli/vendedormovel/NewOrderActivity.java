package br.com.thideoli.vendedormovel;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import br.com.thideoli.vendedormovel.adapter.ProdutoPedidoAdapter;
import br.com.thideoli.vendedormovel.dao.ClienteDAO;
import br.com.thideoli.vendedormovel.dao.PedidoDAO;
import br.com.thideoli.vendedormovel.dao.ProdutoDAO;
import br.com.thideoli.vendedormovel.dao.ProdutoPedidoDAO;
import br.com.thideoli.vendedormovel.model.Cliente;
import br.com.thideoli.vendedormovel.model.Pedido;
import br.com.thideoli.vendedormovel.model.Produto;
import br.com.thideoli.vendedormovel.model.ProdutoPedido;
import br.com.thideoli.vendedormovel.utils.Date;

public class NewOrderActivity extends AppCompatActivity {

    private Spinner spClientes;
    private Spinner spProdutos;
    private List<ProdutoPedido> produtosPedido;
    double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Novo Pedido");
        setContentView(R.layout.activity_new_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preencheComboClientes();
        preencheComboProdutos();

        produtosPedido = new ArrayList<ProdutoPedido>();

        total = 0;

    }

    private void preencheComboClientes() {
        ClienteDAO clienteDAO = new ClienteDAO(NewOrderActivity.this);
        List<Cliente> clientes = clienteDAO.listAll();

        ArrayAdapter<Cliente> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, clientes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spClientes = (Spinner) findViewById(R.id.spinner_clientes);
        spClientes.setAdapter(adapter);
    }

    private void preencheComboProdutos() {
        ProdutoDAO produtoDAO = new ProdutoDAO(NewOrderActivity.this);
        List<Produto> produtos = produtoDAO.listAllAtivos();

        ArrayAdapter<Produto> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, produtos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProdutos = (Spinner) findViewById(R.id.spinner_produtos);
        spProdutos.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("O pedido será cancelado. Tem certeza que deseja voltar?")
                .setPositiveButton("Sim", voltar())
                .setNegativeButton("Não", null)
                .setCancelable(false)
                .show();
    }

    private DialogInterface.OnClickListener voltar() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                NewOrderActivity.super.onBackPressed();
            }
        };
    }

    public void adicionar(View view) {
        Produto produto = (Produto) spProdutos.getSelectedItem();
        TextView tvQtde = (TextView) findViewById(R.id.editTextQtde);

        if(tvQtde.getText().toString().trim().equals("")){
            Toast.makeText(this, "Informe a quantidade", Toast.LENGTH_SHORT).show();
            return;
        }

        int qtde = Integer.valueOf(tvQtde.getText().toString());

        if(qtde == 0) {
            Toast.makeText(this, "A quantidade deve ser maior que ZERO", Toast.LENGTH_SHORT).show();
            return;
        }

        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setProduto(produto.getDescricao());
        produtoPedido.setPreco(produto.getPreco());
        produtoPedido.setQuantidade(qtde);
        produtoPedido.setSubtotal(qtde * produto.getPreco());
        produtoPedido.setPedido("");

        produtosPedido.add(produtoPedido);

        tvQtde.setText("");


        ListView lvItens = findViewById(R.id.list_add_products);
        ProdutoPedidoAdapter arrayAdapter = new ProdutoPedidoAdapter(NewOrderActivity.this, produtosPedido);
        lvItens.setAdapter(arrayAdapter);

        total = total + produtoPedido.getSubtotal();

        TextView tvTotal = findViewById(R.id.valor_total);
        tvTotal.setText(String.valueOf(total));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_order_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_order_save:
                salvar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void salvar() {

        try {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser user = firebaseAuth.getCurrentUser();

            TextView tvTotal = (TextView) findViewById(R.id.valor_total);

            Pedido pedido = new Pedido();
            pedido.setCodigo("new: " + Date.pegaDataHoraSegundoAtual());
            pedido.setCliente(((Cliente) spClientes.getSelectedItem()).getRazaoSocial());
            pedido.setVendedor(user.getEmail());
            pedido.setData(Date.pegaDataHoraAtual());
            pedido.setTotal(Double.valueOf(tvTotal.getText().toString()));
            pedido.setEnviado(0);
            pedido.setProdutosPedido(produtosPedido);


            PedidoDAO pedidoDAO = new PedidoDAO(NewOrderActivity.this);
            pedidoDAO.insert(pedido);
            ProdutoPedidoDAO produtoPedidoDAO = new ProdutoPedidoDAO(NewOrderActivity.this);

            for(ProdutoPedido produtoPedido : produtosPedido) {
                produtoPedido.setPedido(pedido.getCodigo());
                produtoPedidoDAO.insert(produtoPedido);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Houve um erro ao salvar o pedido, tente novamente", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Pedido salvo", Toast.LENGTH_SHORT).show();
        finish();
    }
}
