package br.com.senaijandira.quiz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import br.com.senaijandira.quiz.R;

public class FinalActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
    }

    public void finalizarJogo(View view) {
        finish();
    }

    public void iniciarJogo(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
