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
        String message= "";
        try{
            MemberDTO mDTO = memberService.getByCredentials(memberDTO);

            if(mDTO != null){
                final TokenDTO tokenDTO = memberService.login(mDTO);
                return ResponseEntity.ok().body(tokenDTO);
            }else{
                throw new IllegalArgumentException("id password wrong");
            }
        }catch(Exception e){
            log.error("login failed for user: {}, error: {}", memberDTO.getUsername(), e.getMessage());

            responseDTO = ResponseDTO.builder()
                    .error(e.getMessage())
                    .build();

            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);        }
    }

    @GetMapping("/mypage")
    public ResponseEntity<?> memberInfo(@RequestHeader("Authorization") String token){
         try{
            MemberDTO memberDto = memberService.getByToken(token);
            return ResponseEntity.ok().body(memberDto);
        }catch (Exception e){
             log.error("mypage failed  error: {}", e.getMessage());

             ResponseDTO responseDTO = ResponseDTO.builder()
                     .error("mypage failed.")
                     .build();

             return ResponseEntity
                     .badRequest()
                     .body(responseDTO);
        }
    }

    @PostMapping("/mypage")
     public ResponseEntity<?> memberModify(@RequestHeader("Authorization") String token, @RequestBody MemberDTO memberDTO){
        ResponseDTO responseDTO;
        try{
           memberService.modify(memberDTO, token);
           responseDTO = ResponseDTO.builder().message("modify success").build();
           return ResponseEntity.ok().body(responseDTO);

        }catch(Exception e){
            responseDTO = ResponseDTO.builder()
                    .error(e.getMessage()).build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    @PostMapping("/token")
    public ResponseEntity<?> createNewAccessToken( @RequestBody TokenDTO tokenDTO){
        try{
            TokenDTO token = memberService.getNewAccessToken(tokenDTO);
            return ResponseEntity.ok().body(token);

        }catch (Exception e)
        {
            System.out.println("error processing");
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("AccessToken renew failed.")
                    .build();

            return ResponseEntity.badRequest().body(responseDTO);
        }

    }
}
