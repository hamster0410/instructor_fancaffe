package fancaffe.board.domain.post.service;


import fancaffe.board.domain.member.entity.Member;
import fancaffe.board.domain.member.repository.MemberRepository;
import fancaffe.board.domain.post.dto.PostRequest;
import fancaffe.board.domain.post.dto.PostResponse;
import fancaffe.board.domain.post.entity.Post;
import fancaffe.board.domain.post.repository.PostRepository;
import fancaffe.board.global.security.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    @Autowired
    private TokenProvider tokenProvider;

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // 게시글 저장
    @Transactional
    public Long savePost(final PostRequest params,String token) {
        String userId = tokenProvider.extractAllClaims(token);
        System.out.println("here " + userId);
        Member member = memberRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + userId));
        Post post = Post.builder().title(params.getTitle()).member(member).contents(params.getContents()).build();
        postRepository.save(post);
        return post.getId();
    }

    // 게시글 상세정보 조회
    @Transactional(readOnly = true)
    public PostResponse findPostById(final Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("post not found : " + id));
        return new PostResponse(post);
    }

    // 게시글 목록 조회
    @Transactional(readOnly = true)
    public List<PostResponse> findAllPost() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> new PostResponse(post))
                .collect(Collectors.toList());
    }



}
