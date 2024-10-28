package fancaffe.board.domain.post.dto;

import fancaffe.board.domain.post.entity.Post;
import lombok.Getter;

@Getter
public class PostResponse {

    private Long id;
    private String title;
    private String contents;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
    }

}
