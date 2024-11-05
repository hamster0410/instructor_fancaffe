package fancaffe.board.domain.comment.controller;

import fancaffe.board.domain.comment.dto.CommentDTO;
import fancaffe.board.domain.comment.dto.CommentResponseDTO;
import fancaffe.board.domain.comment.dto.RequestCommentDTO;
import fancaffe.board.domain.comment.service.CommentService;
import fancaffe.board.global.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;


    @GetMapping("/{postId}")
    public ResponseEntity<?> getCommentsByPostId(@PathVariable("postId") Long postId,@RequestParam(value = "page", defaultValue = "1") int pageid) {
        // 댓글 조회 로직
        try{
            List<CommentDTO> comments = commentService.getCommentsByPostId(postId, pageid - 1) ;
            Long commentCount = commentService.getCommentCount(postId);
            CommentResponseDTO commentResponseDTO = CommentResponseDTO
                    .builder()
                    .comments(comments)
                    .totalCommentCount(commentCount)
                    .build();

            return ResponseEntity.ok().body(commentResponseDTO);
        }catch(Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error(e.getMessage()).build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    @PostMapping("/create/{postId}")
    public ResponseEntity<?> createComment(@RequestHeader("Authorization") String token ,
                                           @PathVariable("postId") Long postId,
                                           @RequestBody RequestCommentDTO requestCommentDTO,
                                           @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        ResponseDTO responseDTO;
        // 댓글 생성 로직
        try{
             commentService.saveCommentWithImage(token,postId,requestCommentDTO,imageFile);
             responseDTO =ResponseDTO.builder()
                     .message("comment success")
                     .build();
             return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){
            responseDTO = ResponseDTO
                    .builder()
                    .error(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }

    }

    @PutMapping("/update/{commentId}")
    public ResponseEntity<?> updateComment(@RequestHeader("Authorization") String token ,
                                           @PathVariable("commentId") Long commentId, @RequestBody CommentDTO commentDTO) {
        try{
            CommentDTO commentReturnDTO = commentService.updateComment(token,commentId,commentDTO);

            return ResponseEntity.ok(commentReturnDTO);
        }catch(Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error(e.getMessage()).build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }



    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@RequestHeader("Authorization") String token, @PathVariable("commentId") Long commentId) {
        ResponseDTO responseDTO;
        try {
            commentService.deleteComment(token, commentId);
            responseDTO = ResponseDTO.builder().
                    message("delete success")
                    .build();
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO = ResponseDTO.builder()
                    .error(e.getMessage()).build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }
}
