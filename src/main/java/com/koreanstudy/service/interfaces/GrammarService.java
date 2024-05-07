package com.koreanstudy.service.interfaces;

import com.koreanstudy.dto.GrammarDTO;
import com.koreanstudy.entity.Grammar;

import java.util.List;

public interface GrammarService {

    List<GrammarDTO> saveGrammar(Grammar grammar);

    List<GrammarDTO> getAllGrammars();

    List<GrammarDTO> deleteGrammarById(Long id);

    GrammarDTO getGrammarById(Long id);
}
