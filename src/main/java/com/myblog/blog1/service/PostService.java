package com.myblog.blog1.service;

import com.myblog.blog1.payload.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostDto getPostById(long id);


    List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto updatePost(PostDto postDto, long id);

    List<PostDto> getOnlyPosts();

    void deletePostById(long id);
}
