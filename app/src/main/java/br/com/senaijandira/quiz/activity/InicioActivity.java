package br.com.senaijandira.quiz.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import br.com.senaijandira.quiz.R;

public class InicioActivity extends Activity {

        MediaPlayer mediaPlayer;
        ImageView imgQuiz;
        Animation shakeAnim; //animação de shake

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        mediaPlayer = MediaPlayer.create(this, R.raw.grey_anatomy);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();


        imgQuiz = findViewById(R.id.img_quiz);
        //Animação fadein
        Animation fadein = AnimationUtils.loadAnimation(this, R.anim.fadein);
        imgQuiz.startAnimation(fadein);


        shakeAnim =AnimationUtils.loadAnimation(this, R.anim.shake);
        imgQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //iniciar a animação ao clicar na imagem
                imgQuiz.startAnimation(shakeAnim);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!mediaPlayer.isPlaying()){
            //executar a musica
            mediaPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //pausar a musica
        mediaPlayer.pause();
    }



    public void iniciarJogo(View v){
        mediaPlayer.stop();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        finish();
    }


}
