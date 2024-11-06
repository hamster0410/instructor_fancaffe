package fancaffe.board.domain.member.entity;

import fancaffe.board.global.dto.BaseTimeEntity;

import fancaffe.board.domain.comment.entity.Comment;
import fancaffe.board.domain.heart.entity.Heart;
import fancaffe.board.domain.member.Role;
import fancaffe.board.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Table(name = "MEMBER")
@Getter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    @Setter
    private String password;

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Setter
    @Column(nullable = false, length = 30, unique = true)
    private String mail;

    @Setter
    @Column(nullable = false, length = 30, unique = true)
    private String nickname;

    private String refreshtoken;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>(); // 컬렉션 초기화

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Heart> hearts = new ArrayList<>(); // 사용자가 누른 좋아요 리스트

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>(); // 사용자가 누른 좋아요 리스트

    public void updateUserName(String username){
        this.username = username;
    }

    public void updatePassword(PasswordEncoder passwordEncoder, String password){
        this.password = passwordEncoder.encode(password);
    }


    public void updateRefreshToken(String refreshtoken){
        this.refreshtoken = refreshtoken;
    }

    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

    public void updateRole(Role role) {
        this.role = role;
    }
}