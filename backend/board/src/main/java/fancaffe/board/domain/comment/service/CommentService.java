package fancaffe.board.domain.comment.service;

import fancaffe.board.domain.comment.dto.CommentDTO;
import fancaffe.board.domain.comment.dto.RequestCommentDTO;
import fancaffe.board.domain.comment.entity.Comment;
import fancaffe.board.domain.comment.repository.CommentRepository;
import fancaffe.board.domain.member.entity.Member;
import fancaffe.board.domain.member.service.MemberService;
import fancaffe.board.domain.post.entity.Post;
import fancaffe.board.domain.post.service.PostService;
import fancaffe.board.global.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Value("${file.upload-dir-comment}")  // 파일 저장 경로를 application.properties에 설정
    private String uploadDir;


    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private PostService postService;


    public List<CommentDTO> getCommentsByPostId(Long postId, int page) {
        // 페이지 크기를 10으로 지정
        Pageable pageable = PageRequest.of(page, 10);
        Page<Comment> comments = commentRepository.findByPostId(postId, pageable);

        List<CommentDTO> commentDTOList = new ArrayList<>();
        return comments.getContent().stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());

    }

    public Long getCommentCount(Long postId) {
        return commentRepository.countByPostId(postId);
    }

    public void createComment(String token, Long postId, RequestCommentDTO requestCommentDTO, List<MultipartFile> imageFiles) throws IOException {

        String userId = tokenProvider.extractIdByAccessToken(token);
        Member member = memberService.getByUserId(Long.valueOf(userId));
        Post post = postService.getByPostId(postId);

        //image 저장
        List<String> imageUrl = saveImage(imageFiles,userId);

        Comment parent_comment;
        if(requestCommentDTO.getParentId() != null){
            System.out.println("here " + requestCommentDTO.getParentId());
            parent_comment = commentRepository.findById(requestCommentDTO.getParentId()).get();
        }else{
            parent_comment = null;
        }

        Comment comment = Comment.builder()
                .imageUrl(imageUrl)
                .post(post)
                .content(requestCommentDTO.getContent())
                .parent(parent_comment)
                .member(member)
                .build();

        postService.increaseComment(post);
        commentRepository.save(comment);
    }

    public CommentDTO updateComment(String token, Long commentId, RequestCommentDTO commentDTO, List<MultipartFile> imageFiles) throws IOException {
        String userId = tokenProvider.extractIdByAccessToken(token);
        Long UID = Long.valueOf(userId);
        Optional<Comment> comment = commentRepository.findById(commentId);
        List<String> imageUrl = saveImage(imageFiles,userId);

        //코멘트가 존재하고 사용자가 쓴 글이 맞으면
        if(comment.isPresent() && UID.equals(comment.get().getMember().getId())){

            comment.get().setContent(commentDTO.getContent());
            comment.get().setImageUrl(imageUrl);
            commentRepository.save(comment.get());

            return new CommentDTO(comment.get());
        }else{
            throw new IllegalArgumentException("comment is not present");
        }
    }

    public void deleteComment(String token, Long commentId) {
        Long userId = Long.valueOf(tokenProvider.extractIdByAccessToken(token));
        Optional<Comment> comment = commentRepository.findById(commentId);

        Post post = comment.get().getPost();
        postService.decreaseComment(post);

        if(comment.isPresent() && userId.equals(comment.get().getMember().getId())){
            commentRepository.delete(comment.get());
        }else{
            throw new IllegalArgumentException("comment is not present");
        }

    }

    public List<String> saveImage(List<MultipartFile> imageFiles,String userId) throws IOException {
        List<String> returnFiles = new ArrayList<>();
        Date date = new Date();
        for(MultipartFile imageFile : imageFiles){
            if (imageFile != null && !imageFile.isEmpty()) {
                // 고유한 파일 이름 생성
                String uniqueFileName =  userId + "_" + date.getDate() + "_" + imageFile.getOriginalFilename();
                File destinationFile = new File(uploadDir + File.separator + uniqueFileName);

                // 파일 시스템에 이미지 파일 저장
                imageFile.transferTo(destinationFile);

                // 이미지 URL 설정 (예: http://localhost:8080/images/파일명)
                System.out.println("unique filename" + uniqueFileName + imageFile.getContentType());
                returnFiles.add(uniqueFileName);
            }
        }
        return  returnFiles;
    }
}
