package fancaffe.board.domain.member.service;

import fancaffe.board.domain.member.Role;
import fancaffe.board.domain.member.dto.MemberDTO;
import fancaffe.board.domain.member.dto.TokenDTO;
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

    private TokenProvider tokenProvider;

    @Autowired
    public void setTokenProvider(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

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

    public TokenDTO login(MemberDTO mDTO) {

        final String AccessToken = tokenProvider.AccessTokenCreate(mDTO);
        final String RefreshToken = tokenProvider.RefreshTokenCreate(mDTO);
        saveRefreshToken(mDTO,RefreshToken);
        return TokenDTO.builder().AccessToken(AccessToken).RefreshToken(RefreshToken).build();
    }

    public void modify(MemberDTO memberDTO, String token) {
        System.out.println("Member Service modify");

        Long userId = Long.valueOf(tokenProvider.extractIdByAccessToken(token));
        Optional<Member> member = memberRepository.findById(userId);

        if(memberRepository.existsByMail(memberDTO.getMail())){
            log.warn("Mail already exists {}",memberDTO.getMail());
            throw new RuntimeException("mail already exists");
        }
        if(memberRepository.existsByNickname(memberDTO.getNickname())){
            log.warn("Nickname already exists {}",memberDTO.getNickname());
            throw new RuntimeException("Nickname already exists");
        }
        if(member.isPresent()){
            member.get().setMail(memberDTO.getMail());
            member.get().setNickname(memberDTO.getNickname());
            memberRepository.save(member.get());
        }
    }

    public MemberDTO getByCredentials(final MemberDTO memberDTO){
        System.out.println("Member Service getByCredentials");
        final Optional<Member> originalMember = memberRepository.findByUsername(memberDTO.getUsername());
        if(originalMember.isPresent() && passwordEncoder.matches(memberDTO.getPassword(),originalMember.get().getPassword())){
            System.out.println("member is present and pw is matching");

            return MemberDTO.builder()
                    .id(originalMember.get().getId())
                    .username(memberDTO.getUsername())
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

    public MemberDTO getByToken(String token) {
        System.out.println("Member Service getByToken");
        Long userId = Long.valueOf(tokenProvider.extractIdByAccessToken(token));
        System.out.println(userId);
        Optional<Member> member = memberRepository.findById(userId);
        if(member.isPresent()){
            return MemberDTO.builder()
                    .id(member.get().getId())
                    .username(member.get().getUsername())
                    .mail(member.get().getMail())
                    .nickname(member.get().getNickname())
                    .build();
        }
        return null;
    }


    public TokenDTO getNewAccessToken(TokenDTO tokenDTO) {
        System.out.println("Member Service getNewAccessToken");

        String refreshToken = tokenDTO.getRefreshToken();
        String memberId = tokenProvider.extractIdByRefreshToken(refreshToken);
        Member member = getByUserId(Long.valueOf(memberId));

        if(member.getRefreshtoken().equals(refreshToken)){

            final TokenDTO token = TokenDTO.builder()
                    .AccessToken(tokenProvider.AccessTokenCreate(tokenProvider.extractIdByRefreshToken(refreshToken)))
                    .build();

            return token;
        }

        throw new IllegalArgumentException("Invalid Refresh Token");
    }
}
