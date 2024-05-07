package com.koreanstudy.entity;

import com.koreanstudy.entity.enums.ETypeQuestion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long number;

    @Enumerated(EnumType.STRING)
    private ETypeQuestion type;

    private String question;

    private String answer;

    private String words;

    private String grammars;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private File audioFile;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private TopikOne topikOne;


}
