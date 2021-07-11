package ru.agaf.QuizBuilder.jpa.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "quizs")

public class Quiz implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String title;

    @NotNull
    @Size(max = 250)
    private String description;

    @NotNull
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull
    @Column(name = "stop_date")
    private LocalDate stopDate;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JsonIgnore
    @JoinTable(name = "quiz_question",
            joinColumns = { @JoinColumn(name = "quiz_id") },
            inverseJoinColumns = { @JoinColumn(name = "question_id") })
    private Set<Question> questions = new HashSet<>();



    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JsonIgnore
    @JoinTable(name = "quiz_user",
            joinColumns = { @JoinColumn(name = "quiz_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<User> users = new HashSet<>();


    public Quiz() {

    }
    public Quiz(String title, String description, LocalDate startDate, LocalDate stopDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.stopDate = stopDate;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getStopDate() {
        return stopDate;
    }

    public void setStopDate(LocalDate stopDate) {
        this.stopDate = stopDate;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void copyFrom(Quiz quizInDB,boolean isWithId) {
        if(isWithId){
            if(null != quizInDB.getId()) {
                this.setId(quizInDB.getId());
            }
        }
        if(null != quizInDB.getTitle()) {
            this.setTitle(quizInDB.getTitle());
        }
        if(null != quizInDB.getDescription()){
            this.setDescription(quizInDB.getDescription());
        }
        if(null != quizInDB.getQuestions()){
            this.setQuestions(quizInDB.getQuestions());
        }
        if(null != quizInDB.getStartDate()) {
            this.setStartDate(quizInDB.getStartDate());
        }
        if(null != quizInDB.getStopDate()){
            this.setStopDate(quizInDB.getStopDate());
        }
        if(null != quizInDB.getUsers()){
            this.setUsers(quizInDB.getUsers());
        }
    }
}
