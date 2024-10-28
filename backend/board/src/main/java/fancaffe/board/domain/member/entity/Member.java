package fancaffe.board.domain.member.entity;

import fancaffe.board.domain.BaseTimeEntity;
import fancaffe.board.domain.member.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

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


    private String password;

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    private String token;

    @Enumerated(EnumType.STRING)
    private Role role;

    //refreshtoken
    //created_at

    public void updatePassword(PasswordEncoder passwordEncoder, String password){
        this.password = passwordEncoder.encode(password);
    }

    public void updateUserName(String username){
        this.username = username;
    }

    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }


}
