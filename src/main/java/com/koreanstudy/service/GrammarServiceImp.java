package com.koreanstudy.service;

import com.koreanstudy.dto.GrammarDTO;
import com.koreanstudy.entity.Grammar;
import com.koreanstudy.repository.GrammarRepository;
import com.koreanstudy.service.interfaces.GrammarService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GrammarServiceImp implements GrammarService {

    GrammarRepository grammarRepository;

    public GrammarServiceImp(GrammarRepository grammarRepository) {
        this.grammarRepository = grammarRepository;
    }

    @Override
    public List<GrammarDTO> saveGrammar(Grammar grammar) {
        grammarRepository.save(grammar);
        return grammarRepository.findAll().stream()
                .map(grammar1 -> mapToGrammarDTO(grammar1))
                .collect(Collectors.toList());
    }

    @Override
    public List<GrammarDTO> getAllGrammars() {
        return grammarRepository.findAll().stream()
                .map(grammar -> mapToGrammarDTO(grammar))
                .collect(Collectors.toList());
    }

    @Override
    public List<GrammarDTO> deleteGrammarById(Long id) {
        grammarRepository.deleteById(id);
        return grammarRepository.findAll().stream()
                .map(grammar -> mapToGrammarDTO(grammar))
                .collect(Collectors.toList());
    }

    @Override
    public GrammarDTO getGrammarById(Long id) {
        return mapToGrammarDTO(grammarRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Grammar not found for id :" + id)));
    }

    private GrammarDTO mapToGrammarDTO(Grammar grammar){
        GrammarDTO grammarDTO = new GrammarDTO();
        grammarDTO.setId(grammar.getId());
        grammarDTO.setTitle(grammar.getTitle());
        grammarDTO.setContent(grammar.getContent());
        grammarDTO.setExample(grammar.getExample());
        return  grammarDTO;
    }
}
