package fancaffe.board.domain.post.entity;

import fancaffe.board.domain.member.entity.Member;
import fancaffe.board.domain.post.dto.PostResponse;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="POST")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(columnDefinition = "TEXT")
    private String contents;

    @Setter
    @Column Long hits;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Setter
    @Column
    private String category;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date modifiedAt;

    @Builder
    public Post(String title, String contents, Member member,String category,Long hits,Date createdAt, Date modifiedAt){
        this.title = title;
        this.contents = contents;
        this.member = member;
        this.category = category;
        this.hits = hits;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public PostResponse toDto() {
        return new PostResponse(this);
    }

}
