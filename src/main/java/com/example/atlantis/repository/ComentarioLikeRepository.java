package com.example.atlantis.repository;

import com.example.atlantis.model.ComentarioLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComentarioLikeRepository extends JpaRepository<ComentarioLike, Integer> {
    List<ComentarioLike> findAll();

}
