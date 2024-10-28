package fancaffe.board.domain.post.dto;

import fancaffe.board.domain.post.entity.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest {

    private String title;
    private String contents;

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .contents(contents)
                .build();
    }

}

