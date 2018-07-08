package br.com.thideoli.vendedormovel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.thideoli.vendedormovel.utils.Network;

public class LoginActivity extends AppCompatActivity {

    private EditText emailField;
    private EditText passwordField;

    private String email;
    private String password;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null)
            chamaTelaPrincipal();

        setTitle(R.string.login_activity_title);
        setContentView(R.layout.activity_login);

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);

    }

    public void signIn(View view){
        setAttributes();

        if(!Network.isConnected(LoginActivity.this)){
            Toast.makeText(LoginActivity.this, R.string.message_failure_network, Toast.LENGTH_LONG).show();
            return;
        }

        if(validData())
            signIn(email, password);
        else
            Toast.makeText(LoginActivity.this, R.string.message_fill_all_fields, Toast.LENGTH_LONG).show();
    }

    private boolean validData() {
        return !email.trim().equals("") && !password.trim().equals("");
    }

    private void signIn(String email, String password) {
        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, null, "Entrando...", true);
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                            chamaTelaPrincipal();
                        else {
                            Toast.makeText(LoginActivity.this, R.string.message_failure_in_login, Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    private void chamaTelaPrincipal() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setAttributes(){
        email = emailField.getText().toString();
        password = passwordField.getText().toString();
    }
}
