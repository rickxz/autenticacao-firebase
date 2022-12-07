package com.example.autenticacaofirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListaActivity extends AppCompatActivity {
    private TextView lblUsuario;
    private EditText txtNome;
    private EditText txtNota1;
    private EditText txtNota2;
    private Button btnInserir;
    private Button btnSair;
    private ListView lista;

    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth users = FirebaseAuth.getInstance();
    private String currentUser = users.getCurrentUser().getUid();
    private DatabaseReference alunos = db.child("alunos").child(currentUser);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        lblUsuario = findViewById(R.id.lblUsuario);
        txtNome = findViewById(R.id.txtNome);
        txtNota1 = findViewById(R.id.txtNota1);
        txtNota2 = findViewById(R.id.txtNota2);
        btnInserir = findViewById(R.id.btnInserir);
        btnSair = findViewById(R.id.btnSair);
        lista = findViewById(R.id.lista);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        lblUsuario.setText(email);

        btnInserir.setOnClickListener(new btnInserirListener());
        btnSair.setOnClickListener(new btnSairListener());
        FirebaseListOptions<Aluno> options = new FirebaseListOptions.Builder<Aluno>().setLayout(R.layout.item_lista).setQuery(alunos, Aluno.class).setLifecycleOwner(this).build();
        AlunoAdapter adapter = new AlunoAdapter(options);
        lista.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {

    }
    private class btnInserirListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String nome = txtNome.getText().toString();
            Double nota1 = Double.parseDouble(txtNota1.getText().toString());
            Double nota2 = Double.parseDouble(txtNota2.getText().toString());
            Aluno aluno = new Aluno(nome, nota1, nota2);
            String key = alunos.push().getKey();
            alunos.child(key).setValue(aluno);
            txtNome.setText("");
            txtNota1.setText("");
            txtNota2.setText("");
        }
    }

    private class btnSairListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            users.signOut();
            finish();
            Toast.makeText(ListaActivity.this, "LOGOFF", Toast.LENGTH_SHORT).show();
        }
    }

    private class AlunoAdapter extends FirebaseListAdapter<Aluno> {
        public AlunoAdapter(FirebaseListOptions options) {
            super(options);
        }
        @Override
        protected void populateView(@NonNull View v, @NonNull Aluno model, int position) {
            TextView lblNome = v.findViewById(R.id.lblNome);
            TextView lblNota1 = v.findViewById(R.id.lblNota1);
            TextView lblNota2 = v.findViewById(R.id.lblNota2);
            TextView lblMedia = v.findViewById(R.id.lblMedia);
            double nota1 = model.getNota1();
            double nota2 = model.getNota2();
            lblNome.setText(model.getNome());
            lblNota1.setText(Double.toString(nota1));
            lblNota2.setText(Double.toString(nota2));
            lblMedia.setText(Double.toString((nota1+nota2)/2));
        }
    }
}