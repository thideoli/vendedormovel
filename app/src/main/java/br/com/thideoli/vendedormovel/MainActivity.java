package br.com.thideoli.vendedormovel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import br.com.thideoli.vendedormovel.helper.ClienteHelper;
import br.com.thideoli.vendedormovel.helper.ProdutoHelper;
import br.com.thideoli.vendedormovel.utils.AsyncResponse;
import br.com.thideoli.vendedormovel.utils.Network;
import br.com.thideoli.vendedormovel.utils.Receiver;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    private FirebaseAuth firebaseAuth;
    private Receiver receiver;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null) {
            TextView emailLabel = findViewById(R.id.emailLabel);
            emailLabel.setText(user.getEmail());
        }

        SharedPreferences loginActivityPreferences = getSharedPreferences("VendedorMovel", MODE_PRIVATE);
        ((TextView) findViewById(R.id.dateTimeTextView)).setText(loginActivityPreferences.getString("last_update", "00/00/0000 00:00"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_logout:
                logout();
                return true;
                case R.id.main_synchronize:
                synchronize();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void synchronize() {
        if(!Network.isConnected(MainActivity.this)) {
            Toast.makeText(MainActivity.this, R.string.message_failure_network, Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog = ProgressDialog.show(MainActivity.this, null, "Recebendo...", true);
        receiver = new Receiver();
        receiver.delegate = MainActivity.this;
        receiver.execute();
    }

    private void logout() {
        firebaseAuth.signOut();
        chamaTelaLogin();
    }

    private void chamaTelaLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void listOrders(View view) {
    }

    public void listCustomers(View view) {
        Intent intent = new Intent(MainActivity.this, CustomersActivity.class);
        startActivity(intent);
    }

    public void listProducts(View view) {
        Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
        startActivity(intent);
    }


    @Override
    public void processFinish(Object output) {

        if(output == null) {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, "Erro ao sincronizar, tente novamente.", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Atualizando...");

        try {

            String produtos = ((Map<String, String>) output).get("produtos");
            new ProdutoHelper(this).sendJsonToDB(produtos);

            String clientes = ((Map<String, String>) output).get("clientes");
            new ClienteHelper(this).sendJsonToDB(clientes);

            atualizaDataHoraUltimaSincronizacao();

            progressDialog.dismiss();

        } catch (Exception e) {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, "Erro ao sincronizar, tente novamente.", Toast.LENGTH_LONG).show();
        }

    }

    private void atualizaDataHoraUltimaSincronizacao() {
        Calendar cal = new GregorianCalendar(Calendar.getInstance().getTimeZone());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date = df.format(cal.getTime());
        ((TextView) findViewById(R.id.dateTimeTextView)).setText(date);

        SharedPreferences loginActivityPreferences = getSharedPreferences("VendedorMovel", MODE_PRIVATE);
        SharedPreferences.Editor editor = loginActivityPreferences.edit();
        editor.putString("last_update", date);
        editor.commit();
    }
}
