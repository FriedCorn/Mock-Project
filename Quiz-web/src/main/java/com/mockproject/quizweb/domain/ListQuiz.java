package com.mockproject.quizweb.domain;

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
@Table(name = "list_quiz")
public class ListQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "numb_quiz")
    private int numberOfQuiz;

    @Basic
    @Column(name = "time_limit")
    private String timeLimit;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "listQuiz", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Quiz> quizzes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ListQuiz listQuiz = (ListQuiz) o;

        return Objects.equals(id, listQuiz.id);
    }

    @Override
    public int hashCode() {
        return 1517684984;
    }
}
