package fancaffe.board.domain.member.service;

import fancaffe.board.domain.member.Role;
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
        System.out.println("Member Service create");
        if(member == null | member.getUsername() == null){
            throw new RuntimeException("Invalid arguments");
        }

        //동일 id 검사
        if(memberRepository.existsByUsername(member.getUsername())){
            log.warn("Username already exists {}",member.getUsername());
            throw new RuntimeException("Username already exists");
        }
        if(memberRepository.existsByMail(member.getMail())){
            log.warn("Username already exists {}",member.getMail());
            throw new RuntimeException("mail already exists");
        }

        member.updateRole(Role.USER);

        return memberRepository.save(member);
    }

    public MemberDTO getByCredentials(final String username, final String password, final PasswordEncoder encoder){
        System.out.println("Member Service getByCredentials");
        final Optional<Member> originalMember = memberRepository.findByUsername(username);
        if(originalMember.isPresent() && encoder.matches(password,originalMember.get().getPassword())){
            return MemberDTO.builder()
                    .id(originalMember.get().getId())
                    .username(username)
                    .build();
        }
        return null;
    }

    public void saveRefreshToken(MemberDTO mDTO, String refreshToken) {
        System.out.println("Member Service saveRefreshToken");
        final Optional<Member> originalMember = memberRepository.findByUsername(mDTO.getUsername());

        if(originalMember.isPresent() ){
            originalMember.get().updateRefreshToken(refreshToken);
            memberRepository.save(originalMember.get());
        }
    }

    public Member getByUserId(Long userId) {
        System.out.println("Member Service getByUserId");
        Optional<Member> member = memberRepository.findById(userId);
        return member.orElse(null);
    }
}
