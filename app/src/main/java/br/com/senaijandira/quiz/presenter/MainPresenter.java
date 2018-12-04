package br.com.senaijandira.quiz.presenter;

import java.util.List;

import br.com.senaijandira.quiz.activity.MainActivity;
import br.com.senaijandira.quiz.model.Pergunta;
import br.com.senaijandira.quiz.service.QuizService;
import br.com.senaijandira.quiz.service.ServiceFactory;
import br.com.senaijandira.quiz.view.MainView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {

    MainView mainView;
    QuizService service;
    MainActivity mainActivity;

    public MainPresenter(MainView mainView, QuizService service){
        this.mainView = mainView;
        this.service = service;
    }

    public void carregarPerguntas(){

        //carregar as informações da API
        QuizService api = ServiceFactory.create();

        api.obterPergunta().enqueue(new Callback<List<Pergunta>>() {
            @Override
            public void onResponse(Call<List<Pergunta>> call, Response<List<Pergunta>> response) {

                List<Pergunta> perguntas = response.body();

                mainView.preencherPerguntas(perguntas);
                mainActivity.iniciarJogo();
            }

            @Override
            public void onFailure(Call<List<Pergunta>> call, Throwable t) {
                //Toast.makeText("Erro",1,5).show();
            }
        });

        ///
    }

}
