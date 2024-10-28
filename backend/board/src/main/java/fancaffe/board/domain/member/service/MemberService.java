package fancaffe.board.domain.member.service;

import fancaffe.board.domain.member.dto.MemberDTO;
import fancaffe.board.domain.member.entity.Member;
import fancaffe.board.domain.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member create(final Member member){
        if(member == null | member.getUsername() == null){
            throw new RuntimeException("Invalid arguments");
        }
        final String username = member.getUsername();
        if(memberRepository.existsByUsername(username)){
            log.warn("Username already exists {}",username);
            throw new RuntimeException("Username already exists");
        }

        return memberRepository.save(member);
    }

    public MemberDTO getByCredentials(final String username, final String password, final PasswordEncoder encoder){
        final Optional<Member> originalMember = memberRepository.findByUsername(username);

        if(originalMember.isPresent() && encoder.matches(password,originalMember.get().getPassword())){
            return MemberDTO.builder()
                    .username(username)
                    .id(originalMember.get().getId())
                    .build();
        }
        return null;
    }
}
