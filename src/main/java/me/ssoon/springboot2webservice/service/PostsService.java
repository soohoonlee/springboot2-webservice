package me.ssoon.springboot2webservice.service;

import lombok.RequiredArgsConstructor;
import me.ssoon.springboot2webservice.domain.posts.PostsRepository;
import me.ssoon.springboot2webservice.web.dto.PostsSaveRequestDto;
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
}
