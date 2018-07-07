package br.com.thideoli.vendedormovel;

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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText emailField;
    private EditText passwordField;

    private String email;
    private String password;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.login_activity_title);
        setContentView(R.layout.activity_login);

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void signIn(View view){
        setAttributes();

        if(validData())
            signIn(email, password);
        else
            Toast.makeText(LoginActivity.this, R.string.message_fill_all_fields, Toast.LENGTH_LONG).show();
    }

    private boolean validData() {
        if(email.trim().equals("") || password.trim().equals(""))
            return false;

        return true;
    }

    private void signIn(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else
                            Toast.makeText(LoginActivity.this, R.string.message_failure_in_login, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void setAttributes(){
        email = emailField.getText().toString();
        password = passwordField.getText().toString();
    }
}
