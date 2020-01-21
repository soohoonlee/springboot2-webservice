package me.ssoon.springboot2webservice.web.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import me.ssoon.springboot2webservice.domain.posts.Posts;

@Getter
public class PostListResponseDto {

  private Long id;
  private String title;
  private String author;
  private LocalDateTime modifiedDate;

  public PostListResponseDto(Posts entity) {
    this.id = entity.getId();
    this.title = entity.getTitle();
    this.author = entity.getAuthor();
    this.modifiedDate = entity.getModifiedDate();
  }
}
