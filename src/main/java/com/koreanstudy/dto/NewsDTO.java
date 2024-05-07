package com.koreanstudy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {

    private Long id;

    private String title;

    private String content;

    private String description;

    private Long pictureId;

    @Override
    public String toString() {
        return "NewsDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
