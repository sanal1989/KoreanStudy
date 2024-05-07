package com.koreanstudy.controller.restcontroller;

import com.koreanstudy.dto.NewsDTO;
import com.koreanstudy.service.NewsServiceImp;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/news")
public class NewsController {

    NewsServiceImp newsServiceImp;

    public NewsController(NewsServiceImp newsServiceImp) {
        this.newsServiceImp = newsServiceImp;
    }

    @GetMapping(value = "/{id}")
    public NewsDTO getNews(@PathVariable Long id){
        return newsServiceImp.getNewsById(id);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<NewsDTO> deleteNews(@PathVariable Long id){
        return newsServiceImp.deleteNewsById(id);
    }
}
