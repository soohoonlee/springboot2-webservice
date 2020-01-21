package me.ssoon.springboot2webservice.domain.posts;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostsRepository extends JpaRepository<Posts, Long> {

  @Query("select p from Posts p ORDER BY p.id desc")
  List<Posts> findAllDesc();
}
