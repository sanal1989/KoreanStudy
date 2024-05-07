package com.koreanstudy.service;

import com.koreanstudy.dto.QuestionDTO;
import com.koreanstudy.dto.TopikOneDTO;
import com.koreanstudy.entity.File;
import com.koreanstudy.entity.TopikOne;
import com.koreanstudy.repository.FileRepository;
import com.koreanstudy.repository.TopikOneRepository;
import com.koreanstudy.service.interfaces.TopikOneService;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TopikOneServiceImp implements TopikOneService {

    TopikOneRepository topikOneRepository;
    FileRepository fileRepository;
    QuestionServiceImp questionServiceImp;
    FileUtil fileUtil;

    public TopikOneServiceImp(TopikOneRepository topikOneRepository,
                              FileRepository fileRepository,
                              QuestionServiceImp questionServiceImp,
                              FileUtil fileUtil) {
        this.topikOneRepository = topikOneRepository;
        this.fileRepository = fileRepository;
        this.questionServiceImp = questionServiceImp;
        this.fileUtil = fileUtil;
    }
        

    @Override
    public List<TopikOneDTO> getAllTopikOneDTO() {
        return topikOneRepository.findAll().stream()
                .map(topik->mapToTopikDTO(topik))
                .collect(Collectors.toList());
    }

    @Override
    public List<TopikOneDTO> deleteTopikOneById(Long id) {
        TopikOne topikOne = topikOneRepository.findById(id).get();

        File testText = topikOne.getTestText();
        if(testText != null){
            try {
                Files.delete(Paths.get(topikOne.getTestText().getPath()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileRepository.delete(testText);
        }

        File audioText = topikOne.getAudioText();
        if(testText != null){
            try {
                Files.delete(Paths.get(topikOne.getAudioText().getPath()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileRepository.delete(audioText);
        }

        File audioFile = topikOne.getAudioFile();
        if(testText != null){
            try {
                    Files.delete(Paths.get(topikOne.getAudioFile().getPath()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileRepository.delete(audioFile);
        }

        File answer = topikOne.getAnswer();
        if(testText != null){
            try {
                Files.delete(Paths.get(topikOne.getAnswer().getPath()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileRepository.delete(answer);
        }

        topikOneRepository.deleteById(id);
        return getAllTopikOneDTO();
    }

    @Override
    public TopikOneDTO getTopikOneDTOById(Long id) {
        return mapToTopikDTO(topikOneRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Topik not found for id :" + id)));
    }

    public void saveTopik(TopikOne topikOne){
        topikOneRepository.save(topikOne);
    }
    public void saveTopikOne(Long id,
                             String topikNumber,
                             MultipartFile testText,
                             MultipartFile audioText,
                             MultipartFile audioFile,
                             MultipartFile answer) {
        TopikOne topikOne;
        if(topikOneRepository.existsById(id)){
            topikOne = topikOneRepository.findById(id).get();
            if(topikNumber != null || !topikNumber.isEmpty()){
                topikOne.setTopikNumber(topikNumber);
            }
            if(!testText.getOriginalFilename().isEmpty()){
                topikOne.setTestText(fileUtil.saveFile(testText));
            }
            if(!audioText.getOriginalFilename().isEmpty()){
                topikOne.setAudioText(fileUtil.saveFile(audioText));
            }
            if(!audioFile.getOriginalFilename().isEmpty()){
                topikOne.setAudioFile(fileUtil.saveFile(audioFile));
            }
            if(!answer.getOriginalFilename().isEmpty()){
                topikOne.setAnswer(fileUtil.saveFile(answer));
            }
        }else{
            topikOne = new TopikOne();
            topikOne.setTopikNumber(topikNumber);
            if(!testText.getOriginalFilename().isEmpty()){
                topikOne.setTestText(fileUtil.saveFile(testText));
            }else {
                topikOne.setTestText(null);
            }
            if(!audioText.getOriginalFilename().isEmpty()){
                topikOne.setAudioText(fileUtil.saveFile(audioText));
            }else {
                topikOne.setTestText(null);
            }
            if(!audioFile.getOriginalFilename().isEmpty()){
                topikOne.setAudioFile(fileUtil.saveFile(audioFile));
            }else {
                topikOne.setAudioFile(null);
            }
            if(!answer.getOriginalFilename().isEmpty()){
                topikOne.setAnswer(fileUtil.saveFile(answer));
            }else {
                topikOne.setAnswer(null);
            }
        }
        topikOneRepository.save(topikOne);
    }

    public TopikOne getTopikOneById(Long id){
        return topikOneRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Topik not found for id :" + id));
    }

    private TopikOneDTO mapToTopikDTO(TopikOne topikOne) {
        TopikOneDTO topikOneDTO = new TopikOneDTO();
        topikOneDTO.setId(topikOne.getId());
        topikOneDTO.setTopikNumber(topikOne.getTopikNumber());
        List<QuestionDTO> questionDTOList = topikOne.getQuestions().stream()
                .map(question -> questionServiceImp.mapToQestionDTO(question))
                .collect(Collectors.toList());
        topikOneDTO.setQuestions(questionDTOList);
        if(topikOne.getTestText() !=null){
            topikOneDTO.setTestTextId(topikOne.getTestText().getId());
        }

        if(topikOne.getAudioText() !=null){
            topikOneDTO.setAudioTextId(topikOne.getAudioText().getId());
        }

        if(topikOne.getAudioFile() !=null){
            topikOneDTO.setAudioFileId(topikOne.getAudioFile().getId());
        }

        if(topikOne.getAnswer() !=null){
            topikOneDTO.setAnswerId(topikOne.getAnswer().getId());
        }
        return topikOneDTO;
    }
}
