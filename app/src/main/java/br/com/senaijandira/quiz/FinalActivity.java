package br.com.senaijandira.quiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
