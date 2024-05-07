package com.koreanstudy.repository;

import com.koreanstudy.entity.TopikOne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopikOneRepository extends JpaRepository<TopikOne, Long> {
}
