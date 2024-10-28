package fancaffe.board.domain.member.dto;

import fancaffe.board.domain.member.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private Long id;

    private String password;

    private String username;

    private String token;

    private Role role;
}
