package br.com.thideoli.vendedormovel;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NewOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Novo Pedido");
        setContentView(R.layout.activity_new_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}
