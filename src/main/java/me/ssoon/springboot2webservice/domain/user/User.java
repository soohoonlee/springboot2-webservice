package me.ssoon.springboot2webservice.domain.user;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ssoon.springboot2webservice.domain.BaseTimeEntity;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column
  private String picture;

  @Enumerated(STRING)
  @Column(nullable = false)
  private Role role;

  @Builder
  public User(String name, String email, String picture, Role role) {
    this.name = name;
    this.email = email;
    this.picture = picture;
    this.role = role;
  }

  public User update(String name, String picture) {
    this.name = name;
    this.picture = picture;

    return this;
  }

  public String getRoleKey() {
    return this.role.getKey();
  }
}
