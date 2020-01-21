package me.ssoon.springboot2webservice.domain.posts;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostsRepositoryTest {

  @Autowired
  PostsRepository postsRepository;

  @AfterEach
  void cleanup() {
    postsRepository.deleteAll();
  }

  @Test
  void 게시글저장_불러오기() throws Exception {
    //given
    String title = "테스트 게시글";
    String content = "테스트 본문";

    postsRepository.save(Posts.builder()
        .title(title)
        .content(content)
        .author("jjinimania@gmail.com")
        .build());

    //when
    List<Posts> postsList = postsRepository.findAll();

    //then
    Posts posts = postsList.get(0);
    assertThat(posts.getTitle()).isEqualTo(title);
    assertThat(posts.getContent()).isEqualTo(content);
  }

  @Test
  void BaseTimeEntity_등록() throws Exception {
    //given
    LocalDateTime now = LocalDateTime.of(2020, 1, 21, 0, 0, 0);
    postsRepository.save(Posts.builder()
        .title("title")
        .content("content")
        .author("author")
        .build());

    //when
    List<Posts> postsList = postsRepository.findAll();

    //then
    Posts posts = postsList.get(0);
    System.out.println(">>>>>>>> createdDate=" + posts.getCreatedDate() + ", modifiedDate=" + posts
        .getModifiedDate());

    assertThat(posts.getCreatedDate()).isAfter(now);
    assertThat(posts.getModifiedDate()).isAfter(now);
  }
}