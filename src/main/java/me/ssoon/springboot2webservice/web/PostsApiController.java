package me.ssoon.springboot2webservice.web;

import lombok.RequiredArgsConstructor;
import me.ssoon.springboot2webservice.service.PostsService;
import me.ssoon.springboot2webservice.web.dto.PostsResponseDto;
import me.ssoon.springboot2webservice.web.dto.PostsSaveRequestDto;
import me.ssoon.springboot2webservice.web.dto.PostsUpdateRequestDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

  private final PostsService postsService;

  @PostMapping("/api/v1/posts")
  public Long save(@RequestBody PostsSaveRequestDto requestDto) {
    return postsService.save(requestDto);
  }

  @PutMapping("/api/v1/posts/{id}")
  public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
    return postsService.update(id, requestDto);
  }

  @DeleteMapping("/api/v1/posts/{id}")
  public Long delete(@PathVariable Long id) {
    postsService.delete(id);
    return id;
  }

  @GetMapping("/api/v1/posts/{id}")
  public PostsResponseDto findById(@PathVariable Long id) {
    return postsService.findById(id);
  }
}
