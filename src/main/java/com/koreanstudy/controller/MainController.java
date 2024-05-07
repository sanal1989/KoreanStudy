package com.koreanstudy.controller;

import com.koreanstudy.dto.GrammarDTO;
import com.koreanstudy.dto.NewsDTO;
import com.koreanstudy.dto.TopikOneDTO;
import com.koreanstudy.dto.UserDTO;
import com.koreanstudy.entity.Lesson;
import com.koreanstudy.service.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;

@Controller
public class MainController {

    LessonServiceImp lessonServiceImp;
    GrammarServiceImp grammarServiceImp;
    NewsServiceImp newsServiceImp;
    UserServiceImp userServiceImp;
    FileServiceImp fileServiceImp;
    TopikOneServiceImp topikOneServiceImp;

    public MainController(LessonServiceImp lessonServiceImp,
                          GrammarServiceImp grammarServiceImp,
                          NewsServiceImp newsServiceImp,
                          UserServiceImp userServiceImp,
                          FileServiceImp fileServiceImp,
                          TopikOneServiceImp topikOneServiceImp) {
        this.lessonServiceImp = lessonServiceImp;
        this.grammarServiceImp = grammarServiceImp;
        this.newsServiceImp = newsServiceImp;
        this.userServiceImp = userServiceImp;
        this.fileServiceImp = fileServiceImp;
        this.topikOneServiceImp = topikOneServiceImp;
    }

    @GetMapping(value = "/")
    public String getIndexPage(Model model){
        List<Lesson> lessonList = lessonServiceImp.getAllLessons();
        List<GrammarDTO> grammarList = grammarServiceImp.getAllGrammars();
        List<NewsDTO> newsList = newsServiceImp.getAllNewses();
        List<UserDTO> userList = userServiceImp.getAllUsers();
        List<TopikOneDTO> topikOneList = topikOneServiceImp.getAllTopikOneDTO();
        model.addAttribute("topiks", topikOneList);
        model.addAttribute("lessons", lessonList);
        model.addAttribute("grammars", grammarList);
        model.addAttribute("newses", newsList);
        model.addAttribute("users", userList);
        return "index";
    }

    @GetMapping(value = "/admin")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getAdminPage(Model model){
        List<Lesson> lessonList = lessonServiceImp.getAllLessons();
        List<GrammarDTO> grammarList = grammarServiceImp.getAllGrammars();
        List<NewsDTO> newsList = newsServiceImp.getAllNewses();
        List<UserDTO> userList = userServiceImp.getAllUsers();
        List<TopikOneDTO> topikOneList = topikOneServiceImp.getAllTopikOneDTO();
        model.addAttribute("topiks", topikOneList);
        model.addAttribute("lessons", lessonList);
        model.addAttribute("grammars", grammarList);
        model.addAttribute("newses", newsList);
        model.addAttribute("users", userList);
        return "admin";
    }

    @GetMapping(value = "/lessons/{id}")
    public String getLessonPage(Model model, @PathVariable Long id){
        List<Lesson> lessonList = lessonServiceImp.getAllLessons();
        model.addAttribute("lessons", lessonList);
        Lesson lesson = lessonServiceImp.getLessonById(id);
        model.addAttribute("lesson", lesson);
        return "lesson";
    }

    @GetMapping(value = "/grammars/{id}")
    public String getGrammarPage(Model model, @PathVariable Long id){
        List<GrammarDTO> grammarsList = grammarServiceImp.getAllGrammars();
        model.addAttribute("grammars", grammarsList);
        GrammarDTO grammarDTO = grammarServiceImp.getGrammarById(id);
        model.addAttribute("grammar", grammarDTO);
        return "grammar";
    }

    @GetMapping(value = "/newses/{id}")
    public String getNewsPage(Model model, @PathVariable Long id) throws IOException {
        List<NewsDTO> newsList = newsServiceImp.getAllNewses();
        model.addAttribute("newses", newsList);
        NewsDTO newsDTO = newsServiceImp.getNewsById(id);
        model.addAttribute("news", newsDTO);
        byte[] image = fileServiceImp.getFileById(newsDTO.getPictureId());
        model.addAttribute("image", Base64.encodeBase64String(image));
        return "news";
    }

    @GetMapping(value = "/topiks/{id}")
    public String getTopikPage(Model model, @PathVariable Long id) throws IOException {
        List<TopikOneDTO> topikOneList = topikOneServiceImp.getAllTopikOneDTO();
        model.addAttribute("topiks", topikOneList);
        TopikOneDTO topikOneDTO = topikOneServiceImp.getTopikOneDTOById(id);
        model.addAttribute("topik", topikOneDTO);
//        byte[] image = fileServiceImp.getImageBy(newsDTO.getPictureId());
//        model.addAttribute("image", Base64.encodeBase64String(image));
        return "topik";
    }
    @PostMapping(value = "/news/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RedirectView  addNews(
            @RequestParam(required = false, defaultValue = "0") Long id,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String description,
            @RequestParam(required = false) MultipartFile picture) {
        newsServiceImp.saveNews(id, title, content, description, picture);
        return new RedirectView("/admin");
    }

    @PostMapping(value = "/topik/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public RedirectView  addTopik(
            @RequestParam(required = false, defaultValue = "0") Long id,
            @RequestParam String topikNumber,
            @RequestParam(required = false) MultipartFile testText,
            @RequestParam(required = false) MultipartFile audioText,
            @RequestParam(required = false) MultipartFile audioFile,
            @RequestParam(required = false) MultipartFile answer) {
        topikOneServiceImp.saveTopikOne(id, topikNumber, testText, audioText, audioFile, answer);
        return new RedirectView("/admin");
    }




}
