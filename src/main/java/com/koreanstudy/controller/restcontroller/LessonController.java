package com.koreanstudy.controller.restcontroller;

import com.koreanstudy.entity.Lesson;
import com.koreanstudy.service.LessonServiceImp;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/lesson")
public class LessonController {

    LessonServiceImp lessonServiceImp;

    public LessonController(LessonServiceImp lessonServiceImp) {
        this.lessonServiceImp = lessonServiceImp;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<Lesson> addLesson(@RequestBody Lesson lesson){
        return lessonServiceImp.saveLesson(lesson);
    }

    @GetMapping(value = "/{id}")
    public Lesson getLesson(@PathVariable Long id){
        return lessonServiceImp.getLessonById(id);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<Lesson> deleteLesson(@PathVariable Long id){
        return lessonServiceImp.deleteLessonById(id);
    }
}
