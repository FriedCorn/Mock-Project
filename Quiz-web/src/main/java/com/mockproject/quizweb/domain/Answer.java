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
@Table(name = "answer")
public class Answer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "content")
    private String content;

    @Basic
    @Column(name = "img_src")
    private String imgSrc;

    @ManyToOne
    @JoinColumn(name = "quiz_id", referencedColumnName = "id", nullable = false)
    private Quiz quizByQuizId;

    @Basic
    @Column(name = "is_true_answer")
    private boolean isTrueAnswer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Answer answer = (Answer) o;

        return Objects.equals(id, answer.id);
    }

    @Override
    public int hashCode() {
        return 1142810673;
    }
}
