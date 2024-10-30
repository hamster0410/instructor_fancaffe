package fancaffe.board.domain.member.controller;

import fancaffe.board.domain.member.dto.MemberDTO;
import fancaffe.board.domain.member.dto.TokenDTO;
import fancaffe.board.domain.member.entity.Member;
import fancaffe.board.domain.member.service.MemberService;
import fancaffe.board.global.security.TokenProvider;
import fancaffe.board.global.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> registerMember(@RequestBody MemberDTO memberDTO){
        try{
            if(memberDTO == null || memberDTO.getPassword() == null) {
                throw new RuntimeException("Invalid Password Value.");
            }
            Member member = Member.builder()
                    .username(memberDTO.getUsername())
                    .password(passwordEncoder.encode((memberDTO.getPassword())))
                    .role(memberDTO.getRole())
                    .build();
            Member registeredMember = memberService.create(member);
            MemberDTO responseMemberDTO = MemberDTO.builder()
                    .id(registeredMember.getId())
                    .username(registeredMember.getUsername())
                    .build();

            return ResponseEntity.ok().body(responseMemberDTO);
        }catch(Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder()
            .error("Login failed.").build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody MemberDTO memberDTO){
        MemberDTO mDTO = memberService.getByCredentials(memberDTO.getUsername(), memberDTO.getPassword(), passwordEncoder);

        if(mDTO != null){
            final String AccessToken = tokenProvider.AccessTokenCreate(mDTO);
            final String RefreshToken = tokenProvider.RefreshTokenCreate(mDTO);
            memberService.saveRefreshToken(mDTO,RefreshToken);
            final MemberDTO responseMemberDTO = MemberDTO.builder()
                    .username(mDTO.getUsername())
                    .id(mDTO.getId())
                    .AccessToken(AccessToken)
                    .RefreshToken(RefreshToken)
                    .build();
            return ResponseEntity.ok().body(responseMemberDTO);
        }else{
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login failed.")
                    .build();

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/token")
    public ResponseEntity<?> createNewAccessToken( @RequestBody TokenDTO tokenDTO){

        String refreshToken = tokenDTO.getRefreshToken();

        if(tokenProvider.validateRefreshToken(refreshToken)){

            final TokenDTO token = TokenDTO.builder()
                    .AccessToken(tokenProvider.AccessTokenCreate(tokenProvider.extractRefreshToken(refreshToken)))
                    .build();

            return ResponseEntity.ok().body(token);

        }else{
            System.out.println("error processing");
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("AccessToken renew failed.")
                    .build();

            return ResponseEntity.badRequest().body(responseDTO);
        }


    }
}
