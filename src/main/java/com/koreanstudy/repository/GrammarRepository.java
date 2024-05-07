package com.koreanstudy.repository;

import com.koreanstudy.entity.Grammar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrammarRepository extends JpaRepository<Grammar, Long> {
}
