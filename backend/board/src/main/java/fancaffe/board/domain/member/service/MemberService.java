package fancaffe.board.domain.member.service;

import fancaffe.board.domain.member.Role;
import fancaffe.board.domain.member.dto.MemberDTO;
import fancaffe.board.domain.member.entity.Member;
import fancaffe.board.domain.member.repository.MemberRepository;
import fancaffe.board.global.security.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public int create(MemberDTO memberDTO){
        System.out.println("Member Service create");
        //동일 id 검사
        if(memberRepository.existsByUsername(memberDTO.getUsername())){
            log.warn("Username already exists {}",memberDTO.getUsername());
            return 1;
        }

        //동일 메일 검사
        if(memberRepository.existsByMail(memberDTO.getMail())){
            log.warn("Mail already exists {}",memberDTO.getMail());
            return 2;
        }

        //동일 닉네임 검사
        if(memberRepository.existsByNickname(memberDTO.getNickname())){
            log.warn("Nickname already exists {}",memberDTO.getNickname());
            return 3;
        }

        Member member = Member.builder()
                .username(memberDTO.getUsername())
                .password(passwordEncoder.encode((memberDTO.getPassword())))
                .mail(memberDTO.getMail())
                .nickname(memberDTO.getNickname())
                .role(Role.USER)
                .build();

        memberRepository.save(member);

        return 0;
    }

    public void modify(MemberDTO memberDTO) {
        System.out.println("Member Service modify");


        Optional<Member> member = memberRepository.findByUsername(memberDTO.getUsername());

        if(memberRepository.existsByMail(memberDTO.getMail())){
            log.warn("Mail already exists {}",memberDTO.getMail());
            throw new RuntimeException("mail already exists");
        }
        if(memberRepository.existsByNickname(memberDTO.getNickname())){
            log.warn("Nickname already exists {}",memberDTO.getNickname());
            throw new RuntimeException("mail already exists");
        }
        if(member.isPresent()){
            member.get().setMail(memberDTO.getMail());
            member.get().setNickname(memberDTO.getNickname());
            memberRepository.save(member.get());
        }
    }

    public MemberDTO getByCredentials(final String username, final String password){
        System.out.println("Member Service getByCredentials");
        final Optional<Member> originalMember = memberRepository.findByUsername(username);
        if(originalMember.isPresent() && passwordEncoder.matches(password,originalMember.get().getPassword())){
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
