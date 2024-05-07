package com.koreanstudy.service.interfaces;

import com.koreanstudy.dto.TopikOneDTO;
import com.koreanstudy.entity.TopikOne;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TopikOneService {

    void saveTopikOne(Long id,
                      String topikNumber,
                      MultipartFile testText,
                      MultipartFile audioText,
                      MultipartFile audioFile,
                      MultipartFile answer);

    List<TopikOneDTO> getAllTopikOneDTO();

    List<TopikOneDTO> deleteTopikOneById(Long id);

    TopikOneDTO getTopikOneDTOById(Long id);

    TopikOne getTopikOneById(Long id);

    void saveTopik(TopikOne topikOne);
}
