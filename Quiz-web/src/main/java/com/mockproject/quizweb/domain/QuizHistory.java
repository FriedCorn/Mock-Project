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
    @Column(name = "time_answered")
    private String timeAnswered;

    @Basic
    @Column(name = "time_started")
    private String timeStarted;

    @OneToMany(mappedBy = "quizHistoryByQuizHistoryId", orphanRemoval = true)
    @ToString.Exclude
    private List<AnswerHistory> answerHistories;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "list_quiz_id")
    private ListQuiz listQuiz;

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
