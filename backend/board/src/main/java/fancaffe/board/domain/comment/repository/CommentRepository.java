package fancaffe.board.domain.comment.repository;

import fancaffe.board.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Long countByPostId(Long postId);

    List<Comment> findByPostId(Long postId);
}
