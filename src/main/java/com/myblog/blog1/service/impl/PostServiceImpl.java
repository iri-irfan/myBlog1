package com.myblog.blog1.service.impl;

import com.myblog.blog1.entity.Post;
import com.myblog.blog1.exception.ResourceNotFoundException;
import com.myblog.blog1.payload.PostDto;
import com.myblog.blog1.repository.PostRepository;
import com.myblog.blog1.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

   private PostRepository postRepository;

   private ModelMapper modelMapper;


    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {

        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = mapToEntity(postDto);
        Post savedPost = postRepository.save(post);

        PostDto dto = mapToDto(savedPost);
        return dto;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post not found with id:"+id)
        );

        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());
        return dto;
    }

    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> pagePost = postRepository.findAll(pageable);
        List<Post> posts = pagePost.getContent();
        List<PostDto> dtos = posts.stream().map(p->mapToDto(p)).collect(Collectors.toList());
        return dtos; // We need to add extra addons to debug Lamda Expressions, To debug Lamda Expressions requires additional info
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not Found with id:" + id)
        );
        Post p = mapToEntity(postDto);
        p.setId(post.getId());
        Post savedPost = postRepository.save(p);
        PostDto dtooos = mapToDto(savedPost);
        return dtooos;
    }

    @Override
    public List<PostDto> getOnlyPosts() {
        List<Post> allpost = postRepository.findAll();
        List<PostDto> adto = allpost.stream().map(a -> mapToDto(a)).collect(Collectors.toList());
        return adto;
    }

    @Override
    public void deletePostById(long id) {
        postRepository.deleteById(id);
    }

    PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);
//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return dto;
    }
    Post mapToEntity(PostDto postDto){
       Post post = modelMapper.map(postDto, Post.class);
//        Post post = new Post();
//        post.setTitle(postDto.getTitle()); // Here we copy content from dto(postDto) to entity(post)
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
}
