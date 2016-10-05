package com.example.controllers.commentsControllers;

import com.example.entity.comments.TroublesComments;
import com.example.repository.commentsRepository.TroublesCommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/troublescomments")
public class TroublesCommentsController {
    TroublesCommentsRepository troublesCommentsRepository;

    @Autowired
    public TroublesCommentsController(TroublesCommentsRepository troublesCommentsRepository) {
        this.troublesCommentsRepository = troublesCommentsRepository;
    }

    @RequestMapping(path = "/getall", method = RequestMethod.GET)
    @ResponseBody
    List<TroublesComments> getAll(){
        return troublesCommentsRepository.findAll();
    }
}
