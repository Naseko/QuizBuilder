package ru.agaf.QuizBuilder.rest.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.agaf.QuizBuilder.jpa.model.Answer;
import ru.agaf.QuizBuilder.jpa.model.Question;
import ru.agaf.QuizBuilder.jpa.model.Quiz;
import ru.agaf.QuizBuilder.jpa.model.User;
import ru.agaf.QuizBuilder.jpa.repo.AnswerRepository;
import ru.agaf.QuizBuilder.jpa.repo.QuestionRepository;
import ru.agaf.QuizBuilder.jpa.repo.QuizRepository;
import ru.agaf.QuizBuilder.jpa.repo.UserRepository;

import java.time.LocalDate;
import java.util.Set;

@RestController
@RequestMapping("/adminui")
public class UtilController {
    final static Logger logger = Logger.getLogger(UtilController.class);
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;


    @Autowired
    public UtilController(QuizRepository quizRepository, QuestionRepository questionRepository, UserRepository userRepository, AnswerRepository answerRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
    }

    @PostMapping("/loadtestdata")
    public ResponseEntity<Void> loadTestData() {
        try {

            User user1 = new User("User1", "swerwer");

            Quiz javaQuiz = new Quiz("Java quiz",
                    "Some simple quiz on java knowledge", LocalDate.now(), LocalDate.of(2021, 12, 21));
            Question javaSpring = new Question("Do you use Spring boot?", "YN");
            Question javaJPA = new Question("Do you use JPA?", "YN");

            javaQuiz.getQuestions().add(javaSpring);
            javaQuiz.getQuestions().add(javaJPA);

            javaSpring.getQuizs().add(javaQuiz);
            javaJPA.getQuizs().add(javaQuiz);

            quizRepository.save(javaQuiz);

            user1.getQuizs().add(javaQuiz);
            javaQuiz.getUsers().add(user1);
            quizRepository.save(javaQuiz);

            Quiz tickQuiz = new Quiz("TICK quiz",
                    "A simple quiz on TICK", LocalDate.of(2019, 12, 21), LocalDate.of(2020, 12, 21));
            Question grafana = new Question("Do you like grafana", "YN");
            Question influxDB = new Question("How do you like InfluxDB?", "text");

            tickQuiz.getQuestions().add(grafana);
            tickQuiz.getQuestions().add(influxDB);

            grafana.getQuizs().add(tickQuiz);
            influxDB.getQuizs().add(tickQuiz);

            quizRepository.save(tickQuiz);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/take")
    public ResponseEntity<Void> mockTestPassage() {
        try {
            User user = userRepository.getById(1L);
            Quiz quizToPass = quizRepository.getById(user.getId());
            Set<Question> questions = quizToPass.getQuestions();
            questions.forEach((question) -> {
                Answer answer = new Answer();
                answer.setUser(user);
                answer.setQuestion(question);
                answer.setText("Bla-bla-bla");
                answerRepository.save(answer);
            });
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}