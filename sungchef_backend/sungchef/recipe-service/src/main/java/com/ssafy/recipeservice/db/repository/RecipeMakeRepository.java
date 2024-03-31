package com.ssafy.recipeservice.db.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ssafy.recipeservice.db.entity.RecipeMake;

@Repository
public interface RecipeMakeRepository extends JpaRepository<RecipeMake, Integer> {
    int countAllByUserSnsId(String userSnsId);
    Page<RecipeMake> findAllByUserSnsId(String userSnsId, Pageable pageable);
}
