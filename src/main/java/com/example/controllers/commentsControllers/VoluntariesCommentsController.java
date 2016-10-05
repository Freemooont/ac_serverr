package com.example.controllers.commentsControllers;

import com.example.entity.comments.VoluntariesComments;
import com.example.repository.commentsRepository.VoluntariesCommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/voluntariescommetns")
public class VoluntariesCommentsController {
    VoluntariesCommentsRepository voluntariesCommentsRepository;

    @Autowired
    public VoluntariesCommentsController(VoluntariesCommentsRepository voluntariesCommentsRepository) {
        this.voluntariesCommentsRepository = voluntariesCommentsRepository;
    }

    @RequestMapping(path = "/getall", method = RequestMethod.GET)
    @ResponseBody
    List<VoluntariesComments> getAll() {
        return voluntariesCommentsRepository.findAll();
    }
}
