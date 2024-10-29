package fancaffe.board.domain.post.dto;

import fancaffe.board.domain.member.entity.Member;
import fancaffe.board.domain.post.entity.Post;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostResponse {

    private Long id;
    private String title;
    private String contents;
    private Long hits;
    private String member;
    private String category;
    private Date createdAt;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.hits = post.getHits();
        this.member = post.getMember().getUsername();
        this.category = post.getCategory();
        this.createdAt = post.getCreatedAt();
    }


}
