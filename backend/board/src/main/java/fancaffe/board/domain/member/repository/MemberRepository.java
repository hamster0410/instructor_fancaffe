package fancaffe.board.domain.member.repository;

import fancaffe.board.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    boolean existsByUsername(String username);
    Member findByUsernameAndPassword(String username, String password);
}
