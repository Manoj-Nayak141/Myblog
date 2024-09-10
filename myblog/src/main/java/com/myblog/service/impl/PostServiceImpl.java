package com.myblog.service.impl;
import com.myblog.entity.Post;
import com.myblog.exception.ResourceNotFound;
import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import com.myblog.repository.PostRepository;
import com.myblog.service.PostService;
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

    private PostRepository postRepo;
    private ModelMapper modelMapper;
    public PostServiceImpl(PostRepository postRepo , ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savedPost = postRepo.save(post);
        PostDto dto = mapToDto(savedPost);
        return dto;
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(()->new ResourceNotFound("Post Not Found With id:" + id));
    postRepo.deleteById(id);
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Post not found with id:" + id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Post not found with id:" + id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post savedPost = postRepo.save(post);
        PostDto dto = mapToDto(savedPost);

        return dto;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Post> all = postRepo.findAll(pageable);
        List<Post> listOfPosts = all.getContent();

        List<PostDto> allPosts = listOfPosts.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

        PostResponse response = new PostResponse();
        response.setDto(allPosts);
        response.setPageNo(all.getNumber());
        response.setTotalPages(all.getTotalPages());
        response.setLastPage(all.isLast());
        response.setPageSize(all.getSize());


        return response;
    }


    PostDto mapToDto(Post savedPost) {
        //PostDto postDto = new PostDto();
       PostDto postDto = modelMapper.map(savedPost,PostDto.class);
        //postDto.setId(savedPost.getId());
        //postDto.setTitle(savedPost.getTitle());
        //postDto.setDescription(savedPost.getDescription());
        //postDto.setContent(savedPost.getContent());
        return postDto;
    }

    Post mapToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);
        //Post post = new Post();
        //post.setTitle(postDto.getTitle());
        //post.setDescription(postDto.getDescription());
        //post.setContent(postDto.getContent());

        return post;

    }
}
