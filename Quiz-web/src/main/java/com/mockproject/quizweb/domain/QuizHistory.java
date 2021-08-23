package com.mockproject.quizweb.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "quiz_history")
public class QuizHistory {

    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "answer")
    private Integer answer;

    @Basic
    @Column(name = "time_answered")
    private String timeAnswered;

    @OneToMany(mappedBy = "quizHistoryByQuizHistoryId", orphanRemoval = true)
    @ToString.Exclude
    private List<AnswerHistory> answerHistories;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        QuizHistory that = (QuizHistory) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 1659141468;
    }
}
