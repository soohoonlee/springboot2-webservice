package me.ssoon.springboot2webservice.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import me.ssoon.springboot2webservice.domain.posts.Posts;
import me.ssoon.springboot2webservice.domain.posts.PostsRepository;
import me.ssoon.springboot2webservice.web.dto.PostsSaveRequestDto;
import me.ssoon.springboot2webservice.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class PostsApiControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private PostsRepository postsRepository;

  @Autowired
  private WebApplicationContext context;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .build();
  }

  @AfterEach
  void tearDown() {
    postsRepository.deleteAll();
  }

  @Test
  @WithMockUser(roles = "USER")
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
    mockMvc.perform(post(url)
          .contentType(MediaType.APPLICATION_JSON)
          .content(new ObjectMapper().writeValueAsString(requestDto)))
        .andExpect(status().isOk());

    //then
    List<Posts> postsList = postsRepository.findAll();
    assertThat(postsList.get(0).getTitle()).isEqualTo(title);
    assertThat(postsList.get(0).getContent()).isEqualTo(content);
  }

  @Test
  @WithMockUser(roles = "USER")
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

    //when
    mockMvc.perform(put(url)
          .contentType(MediaType.APPLICATION_JSON)
          .content(new ObjectMapper().writeValueAsString(requestDto)))
        .andExpect(status().isOk());

    //then
    List<Posts> postsList = postsRepository.findAll();
    assertThat(postsList.get(0).getTitle()).isEqualTo(expectedTitle);
    assertThat(postsList.get(0).getContent()).isEqualTo(expectedContent);
  }

  @Test
  @WithMockUser(roles = "USER")
  void Posts_삭제된다() throws Exception {
    //given
    Posts savedPosts = postsRepository.save(Posts.builder()
        .title("title")
        .content("content")
        .author("author")
        .build());


    String url = "http://localhost:" + port + "/api/v1/posts/" + savedPosts.getId();

    //when
    mockMvc.perform(delete(url)
          .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    //then
    List<Posts> postsList = postsRepository.findAll();
    assertThat(postsList.size()).isEqualTo(0);
  }
}