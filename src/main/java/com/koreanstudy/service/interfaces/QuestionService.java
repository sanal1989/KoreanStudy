package com.koreanstudy.service.interfaces;

import com.koreanstudy.dto.QuestionDTO;

import java.util.List;

public interface QuestionService {

    List<QuestionDTO> saveQuestion(Long idTopik,QuestionDTO questionDTO);

    List<QuestionDTO> getAllQuestions();

    List<QuestionDTO> deleteQuestionById(Long id);

    QuestionDTO getQuestionById(Long id);
}
