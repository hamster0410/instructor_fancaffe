package fancaffe.board.domain.post.entity;

import fancaffe.board.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="POST")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @Column Long hits;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date modifiedAt;

    @Builder
    public Post(String title, String contents, Member member){
        this.title = title;
        this.contents = contents;
        this.member = member;
    }
}
