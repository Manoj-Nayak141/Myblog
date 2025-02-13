package com.myblog.service;

import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    void deletePostById(long id);

    PostDto getPostById(long id);

    PostDto updatePost(long id, PostDto postDto);
     PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

}
