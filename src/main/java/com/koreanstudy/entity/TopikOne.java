package com.koreanstudy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDateTime;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class TopikOne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private String TopikNumber;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private File testText;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private File audioText;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private File audioFile;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private File answer;

    @OneToMany
    private Set<Question> questions;
}
