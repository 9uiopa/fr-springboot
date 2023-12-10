package com.uiopa.frspringboot.service.posts;

import com.uiopa.frspringboot.domain.posts.Posts;
import com.uiopa.frspringboot.domain.posts.PostsRepository;
import com.uiopa.frspringboot.web.dto.PostsListResponseDto;
import com.uiopa.frspringboot.web.dto.PostsResponseDto;
import com.uiopa.frspringboot.web.dto.PostsSaveRequestDto;
import com.uiopa.frspringboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();

    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        //dto의 List 반환
        return postsRepository.findAllDesc().stream()

                .map(PostsListResponseDto::new)// '메서드 참조 - 생성자 참조' 라는 표현 방식이다.
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id){
        Posts posts =  postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        postsRepository.delete(posts);
    }
}
