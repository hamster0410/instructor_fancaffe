package fancaffe.board.domain.comment.controller;

import fancaffe.board.domain.comment.dto.CommentDTO;
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


//    @GetMapping("/{postId}")
//    public ResponseEntity<?> getCommentsByPostId(@PathVariable Long postId) {
//        // 댓글 조회 로직
//    }
//
//    @PostMapping("/comments/{postId}")
//    public ResponseEntity<?> createComment(@PathVariable Long postId, @RequestBody CommentDTO commentDTO) {
//        // 댓글 생성 로직
//    }
//
//    @PutMapping("/comments/{postId}/{commentId}")
//    public ResponseEntity<?> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentDTO commentDTO) {
//        // 댓글 수정 로직
//    }
//
//    @DeleteMapping("/comments/{postId}/{commentId}")
//    public ResponseEntity<?> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
//        // 댓글 삭제 로직
//    }
//
//    @PostMapping("/comments/{postId}/{commentId}/replies")
//    public ResponseEntity<?> createReply(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody ReplyDTO replyDTO) {
//        // 대댓글 생성 로직
//    }

}
