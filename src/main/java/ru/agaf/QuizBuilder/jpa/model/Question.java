package ru.agaf.QuizBuilder.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "questions")

public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    private String qtext;

    @NotNull
    @Size(max = 100)
    private String type;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE,
                CascadeType.REMOVE
            },
            mappedBy = "questions")
    private Set<Quiz> quizs = new HashSet<>();

    @Transient
    private List<Answer> answers  = new ArrayList<>();

    public Question() {

    }

    public Question(String qtext, String type) {
        this.qtext = qtext;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQtext() {
        return qtext;
    }

    public void setQtext(String name) {
        this.qtext = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Quiz> getQuizs() {
        return quizs;
    }

    public void setQuizs(Set<Quiz> quizs) {
        this.quizs = quizs;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void copyFrom(Question updatedQestion, boolean isWithId) {
        if(isWithId){
            if(null != updatedQestion.getId()) {
                this.setId(updatedQestion.getId());
            }
        }
        if(null != updatedQestion.getQtext()) {
            this.setQtext(updatedQestion.getQtext());
        }
        if(null != updatedQestion.getType()){
            this.setType(updatedQestion.getType());
        }
        if(!updatedQestion.getQuizs().isEmpty()){
            this.setQuizs(updatedQestion.getQuizs());
        }
        if(null != updatedQestion.getAnswers()) {
            this.setAnswers(updatedQestion.getAnswers());
        }
    }
}
