package fancaffe.board.domain.post.controller;


import fancaffe.board.domain.post.dto.PostRequest;
import fancaffe.board.domain.post.dto.PostResponse;
import fancaffe.board.domain.post.service.PostService;
import fancaffe.board.global.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @GetMapping("/")
    public ResponseEntity<?> best_list(){
        try{
            List<PostResponse> paging = postService.getBestPost(0);
            return ResponseEntity.ok().body(paging);
        }catch(Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("best_list fail").build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    @GetMapping("/best")
    public ResponseEntity<?> best_list(@RequestParam(value = "page", defaultValue = "1") int pageid){
        try{
            List<PostResponse> paging = postService.getBestPost(pageid-1);
            return ResponseEntity.ok().body(paging);
        }catch(Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("best_list fail").build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    @GetMapping("/new")
    public ResponseEntity<?> main_list(@RequestParam(value = "page", defaultValue = "1") int pageid){
        try{
            List<PostResponse> paging = postService.getNewPosts(pageid-1);
            return ResponseEntity.ok().body(paging);
        }catch(Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("main_list fail").build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    @GetMapping("/{category}")
    public ResponseEntity<?> main_list(@PathVariable("category") String category,@RequestParam(value = "page1", defaultValue = "0") int pageid){
        try{
            List<PostResponse> paging = postService.getCategoryPosts(pageid-1, category);
            return ResponseEntity.ok().body(paging);
        }catch(Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("main_list fail").build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    //게시글 상세정보 조회
    @GetMapping("/{category}/{post_id}")
    public ResponseEntity<?> getPostById(
            @PathVariable("category") String category,
            @PathVariable("post_id") Long postId,
            @RequestParam(value = "page", defaultValue = "1") int page) {

        try{
            // 포스트 조회 서비스 호출
            PostResponse postResponse = postService.findPostById(postId);
            // PostResponse 반환
            return ResponseEntity.ok(postResponse);
        }catch (Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("main_list fail").build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    // 게시글 저장
    @PostMapping("/post/write")
    public ResponseEntity<?> savePost( @RequestHeader("Authorization") String token, @RequestBody final PostRequest params) {

        try{
            PostResponse postResponse = postService.savePost(params,token);

            return ResponseEntity.ok(postResponse);
        }catch (Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("save fail").build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }


    // 게시글 작성자 확인
    @GetMapping("/{category}/{post_id}/edit")
    public ResponseEntity<?> postEdit( @RequestHeader("Authorization") String token,
                                    @PathVariable("category") String category,
                                    @PathVariable("post_id") Long postId
                                    ) {
        try{
            PostResponse postResponse = postService.checkPostWriter(token ,postId);

            return ResponseEntity.ok(postResponse);
        }catch (Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("checking fail").build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    //게시글 수정
    @PutMapping("/{category}/{post_id}/edit")
    public ResponseEntity<?> postEditSave( @RequestHeader("Authorization") String token,
                                           @PathVariable("category") String category,
                                           @PathVariable("post_id") Long postId,
                                           @RequestBody final PostRequest params
    ) {
        try{
            PostResponse postResponse = postService.updatePost(token ,postId, params);

            return ResponseEntity.ok(postResponse);
        }catch(Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("editing fail").build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    //게시글 삭제
    @DeleteMapping("/{category}/{post_id}/delete")
    public ResponseEntity<?> postDelete(
                                        @RequestHeader("Authorization") String token,
                                        @PathVariable("category") String category,
                                        @PathVariable("post_id") Long postId){
        try{
            postService.deletePost(token,postId);

            return ResponseEntity.ok().build();
        }catch(Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("editing fail").build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }
}

