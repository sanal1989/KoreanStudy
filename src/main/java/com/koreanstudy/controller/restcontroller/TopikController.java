package com.koreanstudy.controller.restcontroller;

import com.koreanstudy.dto.TopikOneDTO;
import com.koreanstudy.service.TopikOneServiceImp;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/topik")
public class TopikController {

    TopikOneServiceImp topikOneServiceImp;

    public TopikController(TopikOneServiceImp topikOneServiceImp) {
        this.topikOneServiceImp = topikOneServiceImp;
    }

    @GetMapping(value = "/{id}")
    public TopikOneDTO getTopik(@PathVariable Long id){
        return topikOneServiceImp.getTopikOneDTOById(id);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<TopikOneDTO> deleteTopik(@PathVariable Long id){
        return topikOneServiceImp.deleteTopikOneById(id);
    }
}
