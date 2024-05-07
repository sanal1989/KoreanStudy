package com.koreanstudy.dto;

import com.koreanstudy.entity.File;
import com.koreanstudy.entity.Question;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopikOneDTO {

    private Long id;

    private String topikNumber;

    private Long testTextId;

    private Long audioTextId;

    private Long audioFileId;

    private Long answerId;

    private List<QuestionDTO> questions;
}
