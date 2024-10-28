package fancaffe.board.domain.post.controller;

import fancaffe.board.domain.post.dto.PostRequest;
import fancaffe.board.domain.post.dto.PostResponse;
import fancaffe.board.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    // 게시글 저장
    @PostMapping
    public Long savePost(@RequestHeader("Authorization") String token, @RequestBody final PostRequest params) {

        return postService.savePost(params,token);
    }

    // 게시글 상세정보 조회
    @GetMapping("/{id}")
    public PostResponse findPostById(@RequestHeader("Authorization") String token, @PathVariable("id") final Long id) {
        System.out.println("PostService : findpostbyid");
        return postService.findPostById(id);
    }

    // 게시글 목록 조회
    @GetMapping
    public List<PostResponse> findAllPost() {
        return postService.findAllPost();
    }

}
