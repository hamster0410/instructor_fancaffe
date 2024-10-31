package fancaffe.board.domain.member.controller;

import fancaffe.board.domain.member.dto.MemberDTO;
import fancaffe.board.domain.member.dto.TokenDTO;
import fancaffe.board.domain.member.service.MemberService;
import fancaffe.board.global.security.TokenProvider;
import fancaffe.board.global.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/auth")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO> registerMember(@RequestBody MemberDTO memberDTO) {
        ResponseDTO responseDTO;
        String message;
        try {
            int result = memberService.create(memberDTO);
            // result 값을 기준으로 응답 메시지 결정
            switch (result) {
                case 0:
                    message = "SignUp success";
                    break;
                case 1:
                    message = "Username already exists";
                    break;
                case 2:
                    message = "Email already exists";
                    break;
                case 3:
                    message = "Nickname already exists";
                    break;
                default:
                    message = "Sign Up Failed";
                    break;
            }

            // 응답 DTO 빌드 및 반환
            responseDTO = ResponseDTO.builder()
                    .message(message)
                    .build();

            if(result == 0){
                return ResponseEntity.ok(responseDTO);
            }else{
                return ResponseEntity.badRequest().body(responseDTO);
            }

        } catch (Exception e) {
            // 예외 상황 처리 및 로그 기록
            log.error("Signup failed for user: {}, error: {}", memberDTO.getUsername(), e.getMessage());

            responseDTO = ResponseDTO.builder()
                    .error("Signup failed.")
                    .build();

            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody MemberDTO memberDTO){
        ResponseDTO responseDTO;
        try{
            MemberDTO mDTO = memberService.getByCredentials(memberDTO.getUsername(), memberDTO.getPassword());
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
                responseDTO = ResponseDTO.builder()
                        .error("User not found")
                        .build();
                return ResponseEntity.badRequest().body(responseDTO);
            }
        }catch(Exception e){
            log.error("login failed for user: {}, error: {}", memberDTO.getUsername(), e.getMessage());

            responseDTO = ResponseDTO.builder()
                    .error("login failed.")
                    .build();

            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);        }
    }

    @PostMapping("/mypage")
     public ResponseEntity<?> memberModify(@RequestHeader("Authorization") String token, @RequestBody MemberDTO memberDTO){
        ResponseDTO responseDTO;
        try{
           memberService.modify(memberDTO);

           return ResponseEntity.ok().build();
        }catch(Exception e){
            responseDTO = ResponseDTO.builder()
                    .error("MemberModify failed.").build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
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
