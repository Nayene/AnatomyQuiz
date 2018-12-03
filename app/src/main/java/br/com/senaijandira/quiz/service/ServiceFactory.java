package br.com.senaijandira.quiz.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceFactory {

    public static QuizService create(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(QuizService.URL_BASE).
                addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit.create(QuizService.class);
    }

}
