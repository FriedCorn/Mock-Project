package com.mockproject.quizweb.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "answer_history")
public class AnswerHistory {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "quiz_history_id", referencedColumnName = "id", nullable = false)
    private QuizHistory quizHistoryByQuizHistoryId;

    @OneToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AnswerHistory that = (AnswerHistory) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 185458764;
    }
}
