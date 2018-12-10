package br.com.senaijandira.quiz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import br.com.senaijandira.quiz.R;

public class FinalActivity extends Activity {

    TextView erros, acertos;
    int erro, acerto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        erros = findViewById(R.id.erros);
        acertos = findViewById(R.id.acertos);

        erro = getIntent().getIntExtra("Erro", 0);
        acerto = getIntent().getIntExtra("Acerto",0);

        erros.setText(String.valueOf(erro));
        acertos.setText(String.valueOf(acerto));

    }

    public void finalizarJogo(View view) {
        finish();
    }

    public void iniciarJogo(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
