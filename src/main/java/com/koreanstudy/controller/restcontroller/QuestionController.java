package com.koreanstudy.controller.restcontroller;

import com.koreanstudy.dto.GrammarDTO;
import com.koreanstudy.dto.QuestionDTO;
import com.koreanstudy.entity.Grammar;
import com.koreanstudy.service.QuestionServiceImp;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/question")
public class QuestionController {

    QuestionServiceImp questionServiceImp;

    public QuestionController(QuestionServiceImp questionServiceImp) {
        this.questionServiceImp = questionServiceImp;
    }

    @PostMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<QuestionDTO> addQuestion(@PathVariable Long id,
                                         @RequestBody QuestionDTO questionDTO){
        return questionServiceImp.saveQuestion(id, questionDTO);
    }

    @GetMapping(value = "/{id}")
    public QuestionDTO getQuestion(@PathVariable Long id){
        return questionServiceImp.getQuestionById(id);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<QuestionDTO> deleteQuestion(@PathVariable Long id){
        return questionServiceImp.deleteQuestionById(id);
    }
}
