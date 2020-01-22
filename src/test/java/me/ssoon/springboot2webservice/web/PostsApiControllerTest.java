package me.ssoon.springboot2webservice.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import me.ssoon.springboot2webservice.domain.posts.Posts;
import me.ssoon.springboot2webservice.domain.posts.PostsRepository;
import me.ssoon.springboot2webservice.web.dto.PostsSaveRequestDto;
import me.ssoon.springboot2webservice.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class PostsApiControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private PostsRepository postsRepository;

  @AfterEach
  void tearDown() {
    postsRepository.deleteAll();
  }

  @Test
  void Posts_등록된다() throws Exception {
    //given
    String title = "title";
    String content = "content";
    PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
        .title(title)
        .content(content)
        .author("author")
        .build();

    String url = "http://localhost:" + port + "/api/v1/posts";

    //when
    ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

    //then
    assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
    assertThat(responseEntity.getBody()).isGreaterThan(0L);

    List<Posts> postsList = postsRepository.findAll();
    assertThat(postsList.get(0).getTitle()).isEqualTo(title);
    assertThat(postsList.get(0).getContent()).isEqualTo(content);
  }

  @Test
  void Posts_수정된다() throws Exception {
    //given
    Posts savedPosts = postsRepository.save(Posts.builder()
        .title("title")
        .content("content")
        .author("author")
        .build());

    Long updateId = savedPosts.getId();
    String expectedTitle = "title2";
    String expectedContent = "content2";

    PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
        .title(expectedTitle)
        .content(expectedContent)
        .build();

    String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;
    HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

    //when
    ResponseEntity<Long> responseEntity = restTemplate
        .exchange(url, PUT, requestEntity, Long.class);

    //then
    assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
    assertThat(responseEntity.getBody()).isGreaterThan(0L);

    List<Posts> postsList = postsRepository.findAll();
    assertThat(postsList.get(0).getTitle()).isEqualTo(expectedTitle);
    assertThat(postsList.get(0).getContent()).isEqualTo(expectedContent);
  }

  @Test
  void Posts_삭제된다() throws Exception {
    //given
    Posts savedPosts = postsRepository.save(Posts.builder()
        .title("title")
        .content("content")
        .author("author")
        .build());


    String url = "http://localhost:" + port + "/api/v1/posts/" + savedPosts.getId();
    HttpEntity<Posts> deletedEntity = new HttpEntity<>(savedPosts);

    //when
    ResponseEntity<Long> responseEntity = restTemplate
        .exchange(url, DELETE, deletedEntity, Long.class);

    //then
    assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
    assertThat(responseEntity.getBody()).isEqualTo(1L);

    List<Posts> postsList = postsRepository.findAll();
    assertThat(postsList.size()).isEqualTo(0);
  }
}