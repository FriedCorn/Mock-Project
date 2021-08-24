package com.mockproject.quizweb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "content")
    private String content;

    @Basic
    @Column(name = "is_multiple_choice")
    private boolean correctedAnswer;
    @Basic
    @Column(name = "img_src")
    private String imgSrc;

    @OneToMany(mappedBy = "quizByQuizId", orphanRemoval = true)
    @ToString.Exclude
    private List<Answer> answers;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "list_quiz_id")
    private ListQuiz listQuiz;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Quiz quiz = (Quiz) o;

        return Objects.equals(id, quiz.id);
    }

    @Override
    public int hashCode() {
        return 1760944903;
    }
}
