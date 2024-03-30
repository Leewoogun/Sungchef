package com.ssafy.userservice.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ssafy.userservice.db.entity.Survey;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Integer> {
	List<Survey> findAllByUserSnsId(String userSnsId);
}
