package fancaffe.board.domain.post.repository;

import fancaffe.board.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);

    Page<Post> findAllByOrderByHitsDesc(Pageable pageable);

    Page<Post> findByCategory(Pageable pageable, String category);

    Page<Post> findAllByOrderByCreatedDateDesc(Pageable pageable);
}
