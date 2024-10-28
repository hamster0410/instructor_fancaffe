package fancaffe.board.domain.member.controller;

import fancaffe.board.domain.member.dto.MemberDTO;
import fancaffe.board.domain.member.entity.Member;
import fancaffe.board.domain.member.service.MemberService;
import fancaffe.board.global.security.TokenProvider;
import fancaffe.board.global.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            final String token = tokenProvider.create(mDTO);
            System.out.println("controller here " + token);
            final MemberDTO responseMemberDTO = MemberDTO.builder()
                    .username(mDTO.getUsername())
                    .id(mDTO.getId())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseMemberDTO);
        }else{
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login failed.")
                    .build();

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
