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
        //member 찾기
        String userId = tokenProvider.extractIdByAccessToken(token);
        Member member = memberService.getByUserId(Long.valueOf(userId));

        //post 찾기
        Post post = postService.getByPostId(postId);

        postService.increaseHeart(post);

        Heart heart = Heart.builder()
                .member(member)
                .post(post)
                .build();

        heartRepository.save(heart);
    }

    public void deleteHeart(String token, Long postId) {
        System.out.println("[HeartService] deleteHeart");
        //member 찾기
        String userId = tokenProvider.extractIdByAccessToken(token);

        //post 찾기
        Post post = postService.getByPostId(postId);

        postService.decreaseHeart(post);


        Heart heart = heartRepository.findByMemberIdAndPostId(postId, Long.valueOf(userId));

        heartRepository.delete(heart);
    }

    public boolean findByPostAndMember(Long postId, String userId) {
        Heart heart = heartRepository.findByMemberIdAndPostId(postId, Long.valueOf(userId));
        return heart != null;
    }
}
