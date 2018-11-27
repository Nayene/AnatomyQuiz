package br.com.senaijandira.quiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    LinearLayout layout;
    TextView txtPergunta, txtRelogio;
    Button btnResposta1, btnResposta2;

    Integer qtdAcertos=0, qtdErros=0;

    // media player para tocar musica do tempo
    MediaPlayer mediaPlayer;

    int indexPergunta;



    String[] perguntas = {
            "Onde se passa a série Breaking Bad?",
            "Qual e o personagem principal da série?",
            "Quantas temporadas tem a série?",
            "Qual era o nome da lanchonete de Gustavo Fring?"
    };

    String[][] respostas = {
            { "Albuquerque", "Los Angeles" },
            { "Saul Goodman", "Walter White" },
            { "5", "3" },
            { "Los Pollos hermanos", "Mc Donalds" },
    };

    int[]  gabarito = { 0, 1, 0, 0 };

    private void iniciarJogo(){
        qtdErros=0;
        qtdAcertos=0;
        indexPergunta = 0;

        txtPergunta.setText(perguntas[indexPergunta]);

        btnResposta1.setText(respostas[indexPergunta][0]);
        btnResposta1.setTag(0);

        btnResposta2.setText(respostas[indexPergunta][1]);
        btnResposta2.setTag(1);

        btnResposta1.setOnClickListener(clickBtnResposta);
        btnResposta2.setOnClickListener(clickBtnResposta);

        //inicar a contagem
        timer.start();

        // iniciar a musica de tempo
        mediaPlayer = MediaPlayer.create(this, R.raw.countdown);
        mediaPlayer.start();
    }

    // codigo do relogio , contar 30seg.
    CountDownTimer timer = new CountDownTimer(30000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtRelogio.setText(""+ millisUntilFinished / 1000);
        }

        @Override
        public void onFinish() {
            alert("Tempo esgotado", "Você demorou demais!");
        }
    };


    void mudarCorBotao( View botao, int cor ){
        botao.setBackgroundColor(cor);
    }

    void reiniciarCoresBotoes(){

        btnResposta1.setBackgroundColor( ContextCompat.getColor(this, R.color.amarelo));
        btnResposta2.setBackgroundColor( ContextCompat.getColor(this, R.color.roxo));

    }


    View.OnClickListener clickBtnResposta = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int respostaUsuario = (int)v.getTag();

            if(respostaUsuario == gabarito[indexPergunta]){
               qtdAcertos++;
               // alert("Parabéns", "Resposta correta!");
                mudarCorBotao(v, ContextCompat.getColor(v.getContext(), R.color.verde));

                new ProximaPerguntaDelay(1000).execute();

            }else{
                qtdErros++;
                //alert("Sorry", "Resposta errada!");
                mudarCorBotao(v, ContextCompat.getColor(v.getContext(), R.color.vermelho));

                new ProximaPerguntaDelay(1000).execute();

            }
            // parar o relogio quando for respondido
            timer.cancel();

            //parar a musica do relogio
            mediaPlayer.stop();
        }
    };


    public void alert(String titulo, String msg){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(titulo);
        alert.setMessage(msg);

        //não pode deixar o alert clicando fora da caixa quando for proximo
        alert.setCancelable(false);
        alert.setPositiveButton("Proximo", new Dialog.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                proximaPergunta();
            }

        });

        alert.create().show();
    }

    public void gameOver(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Game Over");
        alert.setMessage("QtdAcertos: " + qtdAcertos.toString()+" \nQtdErros: " + qtdErros.toString());

        alert.setNegativeButton("sair", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        alert.setPositiveButton("reiniciar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                iniciarJogo();
            }
        });

        alert.create().show();
    }

    public void proximaPergunta(){

        if(indexPergunta == perguntas.length-1) {
            //jogo acabou
            gameOver();
            return;
        }

        indexPergunta++;
        txtPergunta.setText(perguntas[indexPergunta]);

        btnResposta1.setText(respostas[indexPergunta][0]);
        btnResposta2.setText(respostas[indexPergunta][1]);

        //reiniciando o timer
        timer.start();

        // inicar a musica novamente
        mediaPlayer = MediaPlayer.create(this, R.raw.countdown); // recaregar a musica quando for proximo
        mediaPlayer.start();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtPergunta = findViewById(R.id.txtPergunta);
        txtRelogio = findViewById(R.id.txtRelogio);

        btnResposta1 =  findViewById(R.id.btn1);
        btnResposta2 =  findViewById(R.id.btn2);

        iniciarJogo();

    }


     class ProximaPerguntaDelay extends  AsyncTask<Void, Void, Void>{

        private int milisegundos;

        public ProximaPerguntaDelay(int milisegundos) {
            this.milisegundos = milisegundos;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            SystemClock.sleep(milisegundos);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            proximaPergunta();
            reiniciarCoresBotoes();
        }

    }

}
