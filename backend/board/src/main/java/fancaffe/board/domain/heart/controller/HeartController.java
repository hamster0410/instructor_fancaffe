package fancaffe.board.domain.heart.controller;

import fancaffe.board.domain.heart.service.HeartService;
import fancaffe.board.global.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/heart")
public class HeartController {

    @Autowired
    HeartService heartService;

    @GetMapping("/add")
    public ResponseEntity<?> heartAdd(@RequestHeader("Authorization") String token,@RequestParam(value = "postId")Long postId){
        ResponseDTO responseDTO;
        String message;
        try {
            heartService.addHeart(token, postId);
            message = "heart success";
            responseDTO = ResponseDTO.builder()
                            .message(message)
                            .build();
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO = ResponseDTO.builder()
                    .error(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/delete")
    public ResponseEntity<?> heartDelete(@RequestHeader("Authorization") String token,@RequestParam(value = "postId")Long postId){
        ResponseDTO responseDTO;
        String message;
        try {
            heartService.deleteHeart(token, postId);
            message = "delete success";
            responseDTO = ResponseDTO.builder()
                    .message(message)
                    .build();
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO = ResponseDTO.builder()
                    .error(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
