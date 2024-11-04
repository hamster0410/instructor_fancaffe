package fancaffe.board.domain.comment.controller;

import fancaffe.board.domain.comment.entity.Comment;
import fancaffe.board.domain.comment.service.CommentService;
import fancaffe.board.global.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

//    @GetMapping("/get/{postId}")
//    public ResponseEntity<?> getPostComment(@PathVariable("category") Long postId){
//        ResponseDTO responseDTO;
//
//        try{
//            List<Comment> comments = commentService.getComments(postId);
//        }catch (Exception e){
//
//        }
//    }


}
