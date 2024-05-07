package com.koreanstudy.service.interfaces;

import com.koreanstudy.dto.NewsDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NewsService {

    void saveNews(Long id,
             String title,
             String content,
             String description,
             MultipartFile picture);

    List<NewsDTO> getAllNewses();

    List<NewsDTO> deleteNewsById(Long id);

    NewsDTO getNewsById(Long id);

}
