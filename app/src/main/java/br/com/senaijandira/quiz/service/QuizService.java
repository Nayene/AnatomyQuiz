package br.com.senaijandira.quiz.service;

import java.util.List;

import br.com.senaijandira.quiz.model.Pergunta;
import retrofit2.Call;
import retrofit2.http.GET;

public interface QuizService {

    String URL_BASE = "http://10.0.2.2:5001/";


    @GET("perguntas")
    Call<List<Pergunta>> obterPergunta();


}
