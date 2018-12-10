package br.com.senaijandira.quiz.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import java.util.List;

import br.com.senaijandira.quiz.R;
import br.com.senaijandira.quiz.model.Pergunta;
import br.com.senaijandira.quiz.service.QuizService;
import br.com.senaijandira.quiz.service.ServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    LinearLayout layout;
    TextView txtPergunta, txtRelogio, erros, acertos;
    Button btnResposta1, btnResposta2, btnResposta3, btnResposta4;
    Button btnjogarNovamente, btnSair;

    Integer qtdAcertos=0, qtdErros=0;



    // media player para tocar musica do tempo
    MediaPlayer mediaPlayer;

    int indexPergunta;

    List<Pergunta> resultadoApi;

    public void iniciarJogo(){
        qtdErros=0;
        qtdAcertos=0;
        indexPergunta = 0;

//        txtPergunta.setText(perguntas[indexPergunta]);
        txtPergunta.setText(resultadoApi.get(indexPergunta).getPergunta());

//        btnResposta1.setText(respostas[indexPergunta][0]);
        btnResposta1.setText(resultadoApi.get(indexPergunta).getRespostas().get(0));
        btnResposta1.setTag(0);

        btnResposta2.setText(resultadoApi.get(indexPergunta).getRespostas().get(1));
        btnResposta2.setTag(1);

        btnResposta3.setText(resultadoApi.get(indexPergunta).getRespostas().get(2));
        btnResposta3.setTag(2);

        btnResposta4.setText(resultadoApi.get(indexPergunta).getRespostas().get(3));
        btnResposta4.setTag(3);

        btnResposta1.setOnClickListener(clickBtnResposta);
        btnResposta2.setOnClickListener(clickBtnResposta);
        btnResposta3.setOnClickListener(clickBtnResposta);
        btnResposta4.setOnClickListener(clickBtnResposta);

        //inicar a contagem
        timer.start();

        // iniciar a musica de tempo
        mediaPlayer = MediaPlayer.create(this, R.raw.grey_anatomy);
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
        btnResposta3.setBackgroundColor( ContextCompat.getColor(this, R.color.azul));
        btnResposta4.setBackgroundColor( ContextCompat.getColor(this, R.color.rosa));

    }


    View.OnClickListener clickBtnResposta = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int respostaUsuario = (int)v.getTag();

            if(respostaUsuario == resultadoApi.get(indexPergunta).getGabarito()){
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

    //Finalizando o aplicativo
    public void gameOver(){

        Intent intent = new Intent(this, FinalActivity.class);

        intent.putExtra("Erro", qtdErros);
        intent.putExtra("Acerto", qtdAcertos);

        startActivity(intent);
        finish();
  }

    //indo para a próxima pergunta
    public void proximaPergunta(){

        if(indexPergunta == resultadoApi.size()-1) {
            //jogo acabou
            //erros.setText(qtdErros.toString());
            gameOver();
            return;
        }

        indexPergunta++;
        txtPergunta.setText(resultadoApi.get(indexPergunta).getPergunta());

//        btnResposta1.setText(respostas[indexPergunta][0]);
        btnResposta1.setText(resultadoApi.get(indexPergunta).getRespostas().get(0));
        btnResposta1.setTag(0);

        btnResposta2.setText(resultadoApi.get(indexPergunta).getRespostas().get(1));
        btnResposta2.setTag(1);

        btnResposta3.setText(resultadoApi.get(indexPergunta).getRespostas().get(2));
        btnResposta3.setTag(2);

        btnResposta4.setText(resultadoApi.get(indexPergunta).getRespostas().get(3));
        btnResposta4.setTag(3);

        //reiniciando o timer
        timer.start();

        // inicar a musica novamente
        mediaPlayer = MediaPlayer.create(this, R.raw.grey_anatomy); // recaregar a musica quando for proximo
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
        btnResposta3 = findViewById(R.id.btn3);
        btnResposta4 = findViewById(R.id.btn4);
        btnjogarNovamente = findViewById(R.id.btnJogarNovamente);
        btnSair = findViewById(R.id.btnSair);



        //qtdErros = findViewById(R.id.qtdAcertos);


        // carregar as informações da API
        QuizService api = ServiceFactory.create();

        api.obterPergunta().enqueue(new Callback<List<Pergunta>>() {
            @Override
            public void onResponse(Call<List<Pergunta>> call, Response<List<Pergunta>> response) {

                resultadoApi = response.body();
                iniciarJogo();
            }

            @Override
            public void onFailure(Call<List<Pergunta>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Erro ao carregar o quiz!", Toast.LENGTH_SHORT).show();
            }
        });




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

    public void passarInformacao(){
        Intent intent = new Intent(this, FinalActivity.class);
        intent.putExtra("Erro", qtdErros);
        intent.putExtra("Acerto", qtdAcertos);


        startActivity(intent);

    }

}
