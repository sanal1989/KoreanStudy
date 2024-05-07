package com.koreanstudy.service.interfaces;

import com.koreanstudy.entity.Lesson;

import java.util.List;

public interface LessonService {

    List<Lesson> saveLesson(Lesson lesson);

    List<Lesson> getAllLessons();

    List<Lesson> deleteLessonById(Long id);

    Lesson getLessonById(Long id);
}
