package br.com.senaijandira.quiz.view;

import java.util.List;

import br.com.senaijandira.quiz.model.Pergunta;

public interface MainView {

    void preencherPerguntas(List<Pergunta> lstPerguntas);
}
