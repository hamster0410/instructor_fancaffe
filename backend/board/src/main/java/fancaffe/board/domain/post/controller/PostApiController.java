package fancaffe.board.domain.post.controller;


import fancaffe.board.domain.heart.service.HeartService;
import fancaffe.board.domain.post.dto.PostDetailDTO;
import fancaffe.board.domain.post.dto.PostListDTO;
import fancaffe.board.domain.post.dto.PostRequest;
import fancaffe.board.domain.post.dto.PostResponse;
import fancaffe.board.domain.post.service.PostService;
import fancaffe.board.global.dto.ResponseDTO;
import fancaffe.board.global.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class PostApiController {

    @Autowired
    private  PostService postService;

    @Autowired
    private HeartService heartService;

    @Autowired
    private TokenProvider tokenProvider;

    @GetMapping("/")
    public ResponseEntity<?> best_list(){
        try{
            System.out.println("postapicontroller start");
            List<PostListDTO> paging = postService.getBestPost(0);
            return ResponseEntity.ok().body(paging);
        }catch(Exception e){
            System.out.println("here " + e.getMessage());
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
            List<PostListDTO> paging = postService.getBestPost(pageid-1);
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
            List<PostListDTO> paging = postService.getNewPosts(pageid-1);
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
    public ResponseEntity<?> main_list(@PathVariable("category") String category,@RequestParam(value = "page", defaultValue = "1") int pageid){
        try{
            List<PostListDTO> paging = postService.getCategoryPosts(pageid-1, category);
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
    public ResponseEntity<?> getPostById(@RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable("category") String category,
            @PathVariable("post_id") Long postId,
            @RequestParam(value = "page", defaultValue = "1") int page) {

        try{
            // 포스트 조회 서비스 호출
            PostDetailDTO postDetailDTO = postService.
                    checkByPostId(postId);

            if(token!=null){
                boolean heart_state = heartService.findByPostAndMember(postId, tokenProvider.extractIdByAccessToken(token));
                postDetailDTO.setHeart(heart_state);
            }

            // PostResponse 반환
            return ResponseEntity.ok(postDetailDTO);
        }catch (Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error(e.getMessage()).build();
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
            boolean writer_check = postService.checkPostWriter(token ,postId);

            return ResponseEntity.ok(writer_check);
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
        ResponseDTO responseDTO;
        try{
            postService.deletePost(token,postId);
            responseDTO = ResponseDTO.builder().
                    message("delete success")
                    .build();
            return ResponseEntity.ok().body(responseDTO);
        }catch(Exception e){
            responseDTO = ResponseDTO.builder()
                    .error("editing fail").build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }
}

