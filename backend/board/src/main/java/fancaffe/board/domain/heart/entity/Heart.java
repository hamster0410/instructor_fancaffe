package fancaffe.board.domain.heart.entity;

import fancaffe.board.domain.member.entity.Member;
import fancaffe.board.domain.post.entity.Post;
import jakarta.persistence.*;

@Table(name="HEART")
@Entity
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post; // 좋아요가 눌린 게시글

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 좋아요를 누른 사용자
}