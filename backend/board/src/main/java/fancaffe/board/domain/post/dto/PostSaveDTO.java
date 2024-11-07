package fancaffe.board.domain.post.dto;

import fancaffe.board.domain.post.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostSaveDTO {

    private Long id;
    private String title;
    private String contents;
    private Long hits;
    private String member;
    private String category;



    public PostSaveDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.hits = post.getHits();
        this.member = post.getMember().getUsername();
        this.category = post.getCategory();
    }


}
