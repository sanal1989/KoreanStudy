package com.koreanstudy.service;

import com.koreanstudy.dto.NewsDTO;
import com.koreanstudy.entity.File;
import com.koreanstudy.entity.News;
import com.koreanstudy.repository.FileRepository;
import com.koreanstudy.repository.NewsRepository;
import com.koreanstudy.service.interfaces.NewsService;
import com.koreanstudy.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NewsServiceImp implements NewsService {

    NewsRepository newsRepository;
    FileRepository fileRepository;
    FileUtil fileUtil;


    public NewsServiceImp(NewsRepository newsRepository,
                          FileRepository fileRepository,
                          FileUtil fileUtil) {
        this.newsRepository = newsRepository;
        this.fileRepository = fileRepository;
        this.fileUtil = fileUtil;
    }


    public void saveNews(Long id,
                         String title,
                         String content,
                         String description,
                         MultipartFile picture) {
        News news;
        if(newsRepository.existsById(id)){
            news = newsRepository.findById(id).get();
            if( title != null || !title.isEmpty()){
                news.setTitle(title);
            }
            if( content != null || !content.isEmpty()){
                news.setContent(content);
            }
            if( description != null || !description.isEmpty()){
                news.setDescription(description);
            }
            if(!picture.getOriginalFilename().isEmpty()){
                news.setPicture(fileUtil.saveFile(picture));
            }
        }else{
            news = new News();
            news.setId(id);
            news.setTitle(title);
            news.setContent(content);
            news.setDescription(description);
            if(!picture.getOriginalFilename().isEmpty()){
                news.setPicture(fileUtil.saveFile(picture));
            }else {
                news.setPicture(null);
            }
        }
        newsRepository.save(news);
    }

    @Override
    public List<NewsDTO> getAllNewses() {
        return newsRepository.findAll().stream().map((news) -> mapToNewsDTO(news))
                .collect(Collectors.toList());
    }

    @Override
    public List<NewsDTO> deleteNewsById(Long id) {
        News news = newsRepository.findById(id).get();
        File file = news.getPicture();
        if(file != null){
            try {
                Files.delete(Paths.get(news.getPicture().getPath()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileRepository.delete(news.getPicture());
        }
        newsRepository.deleteById(id);
        return newsRepository.findAll().stream().map((news1) -> mapToNewsDTO(news1))
                .collect(Collectors.toList());
    }

    @Override
    public NewsDTO getNewsById(Long id) {
        return mapToNewsDTO(newsRepository.findById(id)
                .orElseThrow(()->new RuntimeException("News not found for id :" + id)));
    }

    private NewsDTO mapToNewsDTO(News news) {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setId(news.getId());
        newsDTO.setTitle(news.getTitle());
        newsDTO.setContent(news.getContent());
        newsDTO.setDescription(news.getDescription());
        if(news.getPicture() !=null){
            newsDTO.setPictureId(news.getPicture().getId());
        }
        return newsDTO;
    }

}
