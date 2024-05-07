package com.koreanstudy.service;

import com.koreanstudy.entity.Lesson;
import com.koreanstudy.repository.LessonRepository;
import com.koreanstudy.service.interfaces.LessonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImp implements LessonService {

    LessonRepository lessonRepository;

    public LessonServiceImp(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public List<Lesson> saveLesson(Lesson lesson){
//        String content =lesson.getContent().replace("&#10;","&lt;br&gt;");
//        String example = String.join("&lt;br&gt;",lesson.getExample().split("\\n") );
//        String dialog = String.join("&lt;br&gt;",lesson.getDialog().split("\\n") );
//        String word = String.join("&lt;br&gt;",lesson.getWords().split("\\n") );
//        lesson.setContent(content);
//        lesson.setDialog(dialog);
//        lesson.setExample(example);
//        lesson.setWords(word);
        lessonRepository.save(lesson);
        return lessonRepository.findAll();
    }

    @Override
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    @Override
    public List<Lesson> deleteLessonById(Long id) {
        lessonRepository.deleteById(id);
        return lessonRepository.findAll();
    }

    @Override
    public Lesson getLessonById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Lesson not found for id :" + id));
    }
}
