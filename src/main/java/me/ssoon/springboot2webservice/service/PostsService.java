package me.ssoon.springboot2webservice.service;

import lombok.RequiredArgsConstructor;
import me.ssoon.springboot2webservice.domain.posts.Posts;
import me.ssoon.springboot2webservice.domain.posts.PostsRepository;
import me.ssoon.springboot2webservice.web.dto.PostsResponseDto;
import me.ssoon.springboot2webservice.web.dto.PostsSaveRequestDto;
import me.ssoon.springboot2webservice.web.dto.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

  private final PostsRepository postsRepository;

  @Transactional
  public Long save(PostsSaveRequestDto requestDto) {
    return postsRepository.save(requestDto.toEntity()).getId();
  }

  @Transactional
  public Long update(Long id, PostsUpdateRequestDto requestDto) {
    Posts posts = postsRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
    posts.update(requestDto.getTitle(), requestDto.getContent());
    return id;
  }

  public PostsResponseDto findById(Long id) {
    Posts entity = postsRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
    return new PostsResponseDto(entity);
  }
}
