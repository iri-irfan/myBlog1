package com.myblog.blog1.service.impl;

import com.myblog.blog1.entity.Comment;
import com.myblog.blog1.entity.Post;
import com.myblog.blog1.exception.ResourceNotFoundException;
import com.myblog.blog1.payload.CommentDto;
import com.myblog.blog1.repository.PostRepository;
import com.myblog.blog1.repository.CommentRepository;
import com.myblog.blog1.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, long postId) {
   Post post= postRepository.findById(postId).orElseThrow(
            ()-> new ResourceNotFoundException("Post Not Found With id:" + postId)
    );

    Comment comment = new Comment();
    comment.setEmail(commentDto.getEmail());
    comment.setText(commentDto.getText());
    comment.setPost(post); // OneToManyMapping
        Comment savedComment = commentRepository.save(comment);
        CommentDto dto = new CommentDto();
        dto.setId(savedComment.getId());
        dto.setEmail(savedComment.getEmail());
        dto.setText(savedComment.getText());
        return dto;
    }

    @Override
    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public CommentDto updateComment(long id, CommentDto commentDto, long postId) {
       Post post= postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post not found with Id:" +id)
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment Not Found for Id:" + id)
        );
        Comment c = mapToEntity(commentDto);
        c.setId(comment.getId());
        c.setPost(post);
        Comment savedC = commentRepository.save(c);
        CommentDto dtoo = mapToDto(savedC);
        return dtoo;
    }

    CommentDto mapToDto(Comment comment){
        CommentDto dto = modelMapper.map(comment, CommentDto.class);
        return dto;
    }
    Comment mapToEntity(CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }


}
