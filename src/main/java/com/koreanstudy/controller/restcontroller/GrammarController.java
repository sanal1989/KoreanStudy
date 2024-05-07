package com.koreanstudy.controller.restcontroller;

import com.koreanstudy.dto.GrammarDTO;
import com.koreanstudy.entity.Grammar;
import com.koreanstudy.service.GrammarServiceImp;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/grammar")
public class GrammarController {

    GrammarServiceImp grammarServiceImp;

    public GrammarController(GrammarServiceImp grammarServiceImp) {
        this.grammarServiceImp = grammarServiceImp;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<GrammarDTO> addLesson(@RequestBody Grammar grammar){
        return grammarServiceImp.saveGrammar(grammar);
    }

    @GetMapping(value = "/{id}")
    public GrammarDTO getLesson(@PathVariable Long id){
        return grammarServiceImp.getGrammarById(id);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<GrammarDTO> deleteLesson(@PathVariable Long id){
        return grammarServiceImp.deleteGrammarById(id);
    }
}
