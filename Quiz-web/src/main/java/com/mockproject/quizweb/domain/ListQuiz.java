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
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "numb_quiz")
    private String numberOfQuiz;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "listQuiz", cascade = CascadeType.ALL, orphanRemoval = true)
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
