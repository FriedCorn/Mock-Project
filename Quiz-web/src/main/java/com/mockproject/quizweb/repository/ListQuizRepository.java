package com.mockproject.quizweb.repository;

import com.mockproject.quizweb.domain.ListQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListQuizRepository extends JpaRepository<ListQuiz,Long> {

}
