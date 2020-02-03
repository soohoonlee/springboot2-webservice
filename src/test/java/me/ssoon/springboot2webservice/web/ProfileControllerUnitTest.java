package me.ssoon.springboot2webservice.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

class ProfileControllerUnitTest {

  @Test
  @DisplayName("real profile이 조회된다.")
  void real_profile() {
    // given
    String expectedProfile = "real";
    MockEnvironment mockEnvironment = new MockEnvironment();
    mockEnvironment.addActiveProfile(expectedProfile);
    mockEnvironment.addActiveProfile("oauth");
    mockEnvironment.addActiveProfile("real-db");

    ProfileController profileController = new ProfileController(mockEnvironment);

    // when
    String profile = profileController.profile();

    // then
    assertThat(profile).isEqualTo(expectedProfile);
  }

  @Test
  @DisplayName("real_profile이 없으면 첫 번째가 조회된다.")
  void not_exist_real_profile_show_first() throws Exception {
    //given
    String expectedProfile = "oauth";
    MockEnvironment mockEnvironment = new MockEnvironment();
    mockEnvironment.addActiveProfile(expectedProfile);
    mockEnvironment.addActiveProfile("real-db");

    ProfileController profileController = new ProfileController(mockEnvironment);

    // when
    String profile = profileController.profile();

    // then
    assertThat(profile).isEqualTo(expectedProfile);
  }

  @Test
  @DisplayName("active_profile이 없으면 default가 조회된다.")
  void none_active_profile_show_default() throws Exception {
    //given
    String expectedProfile = "default";
    MockEnvironment mockEnvironment = new MockEnvironment();
    ProfileController profileController = new ProfileController(mockEnvironment);

    // when
    String profile = profileController.profile();

    // then
    assertThat(profile).isEqualTo(expectedProfile);
  }
}