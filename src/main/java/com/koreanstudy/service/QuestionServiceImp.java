package com.koreanstudy.service;

import com.koreanstudy.dto.QuestionDTO;
import com.koreanstudy.dto.UserDTO;
import com.koreanstudy.entity.Question;
import com.koreanstudy.entity.TopikOne;
import com.koreanstudy.entity.enums.ETypeQuestion;
import com.koreanstudy.repository.QuestionRepository;
import com.koreanstudy.service.interfaces.QuestionService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImp implements QuestionService {

    QuestionRepository questionRepository;
    TopikOneServiceImp topikOneServiceImp;

    public QuestionServiceImp(QuestionRepository questionRepository, @Lazy TopikOneServiceImp topikOneServiceImp) {
        this.questionRepository = questionRepository;
        this.topikOneServiceImp = topikOneServiceImp;
    }

    @Override
    public List<QuestionDTO> saveQuestion(Long idTopik, QuestionDTO questionDTO) {
        TopikOne topikOne = topikOneServiceImp.getTopikOneById(idTopik);
        Question question;
        if(questionRepository.existsById(questionDTO.getId())){
            question = questionRepository.findById(questionDTO.getId()).get();
        }else{
            question = new Question();
        }
        question.setId(questionDTO.getId());
        question.setNumber(questionDTO.getNumber());
        if(questionDTO.getType().equals("READING")){
            question.setType(ETypeQuestion.READING);
        }else {
            question.setType(ETypeQuestion.LISTENING);
        }
        question.setQuestion(questionDTO.getQuestion());
        question.setAnswer(questionDTO.getAnswer());
        question.setWords(questionDTO.getWords());
        question.setGrammars(questionDTO.getGrammars());
        question.setTopikOne(topikOne);
        questionRepository.save(question);
        Set<Question> questionSet = topikOne.getQuestions();
        questionSet.add(question);
        topikOneServiceImp.saveTopik(topikOne);
        return questionRepository.findAll().stream()
                .map(question1 -> mapToQestionDTO(question1))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionDTO> getAllQuestions() {
        return null;
    }

    @Override
    public List<QuestionDTO> deleteQuestionById(Long id) {
        Question question = questionRepository.findById(id).get();
        TopikOne topikOne = question.getTopikOne();
        Set<Question> questions = topikOne.getQuestions();
        questions.remove(question);
        topikOneServiceImp.saveTopik(topikOne);
        questionRepository.deleteById(id);
        return questionRepository.findAll().stream()
                .map(question1 -> mapToQestionDTO(question1))
                .collect(Collectors.toList());
    }

    @Override
    public QuestionDTO getQuestionById(Long id) {
        return mapToQestionDTO(questionRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Question not found for id :" + id)));
    }

    public QuestionDTO mapToQestionDTO(Question question){
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setNumber(question.getNumber());
        questionDTO.setType(question.getType().toString());
        questionDTO.setQuestion(question.getQuestion());
        questionDTO.setAnswer(question.getAnswer());
        questionDTO.setWords(question.getWords());
        questionDTO.setGrammars(question.getGrammars());
        return questionDTO;
    }
}
