package com.example.autenticacaofirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class MainActivity extends AppCompatActivity {
    // Feito por Herick Victor Rodrigues | SC301018X
    private EditText txtEmail;
    private EditText txtSenha;
    private Button btnLogin;
    private Button btnCriar;

    private FirebaseAuth users = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        btnLogin = findViewById(R.id.btnLogin);
        btnCriar = findViewById(R.id.btnCriar);
        btnCriar.setOnClickListener(new btnCriarListener());
        btnLogin.setOnClickListener(new btnLoginListener());
    }


    private class btnCriarListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String email = txtEmail.getText().toString();
            if (users.getCurrentUser() == null) {
                String password = txtSenha.getText().toString();
                users.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Usuário criado com sucesso!", Toast.LENGTH_SHORT).show();
                        } else {
                            FirebaseAuthException e = (FirebaseAuthException)task.getException();
                            Toast.makeText(MainActivity.this, "Não foi possível realizar seu cadastro. Tente novamente.", Toast.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity.this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                txtEmail.setText("");
                txtSenha.setText("");
            }
        }
    }

    private class btnLoginListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String email = txtEmail.getText().toString();
            String password = txtSenha.getText().toString();
            users.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        txtEmail.setText("");
                        txtSenha.setText("");
                        Intent i = new Intent(getApplicationContext(), ListaActivity.class);
                        i.putExtra("email", email);
                        startActivity(i);
                    } else {
                        FirebaseAuthException e = (FirebaseAuthException)task.getException();
                        Toast.makeText(MainActivity.this, "Não foi possível realizar seu login. Tente novamente.", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}