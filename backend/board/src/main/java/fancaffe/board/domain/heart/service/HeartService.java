package fancaffe.board.domain.heart.service;

import fancaffe.board.domain.heart.entity.Heart;
import fancaffe.board.domain.heart.repository.HeartRepository;
import fancaffe.board.domain.member.entity.Member;
import fancaffe.board.domain.member.service.MemberService;
import fancaffe.board.domain.post.entity.Post;
import fancaffe.board.domain.post.service.PostService;
import fancaffe.board.global.security.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HeartService {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PostService postService;

    @Autowired
    private HeartRepository heartRepository;

    public void addHeart(String token, Long postId) {
        String userId = tokenProvider.extractIdByAccessToken(token);
        Member member = memberService.getByUserId(Long.valueOf(userId));

        Post post = postService.getByPostId(postId);

        Heart heart = Heart.builder()
                .member(member)
                .post(post)
                .build();

        heartRepository.save(heart);
    }

    public void deleteHeart(String token, Long postId) {
        String userId = tokenProvider.extractIdByAccessToken(token);
        Member member = memberService.getByUserId(Long.valueOf(userId));

        Post post = postService.getByPostId(postId);

        Heart heart = Heart.builder()
                .member(member)
                .post(post)
                .build();

        heartRepository.delete(heart);
    }
}
