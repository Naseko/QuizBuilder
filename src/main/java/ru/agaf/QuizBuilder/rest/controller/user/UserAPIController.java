package ru.agaf.QuizBuilder.rest.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.agaf.QuizBuilder.jpa.model.Answer;
import ru.agaf.QuizBuilder.jpa.model.Question;
import ru.agaf.QuizBuilder.jpa.model.Quiz;
import ru.agaf.QuizBuilder.jpa.model.User;
import ru.agaf.QuizBuilder.jpa.repo.AnswerRepository;
import ru.agaf.QuizBuilder.jpa.repo.QuestionRepository;
import ru.agaf.QuizBuilder.jpa.repo.QuizRepository;
import ru.agaf.QuizBuilder.jpa.repo.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/userui")
public class UserAPIController {
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public UserAPIController(QuizRepository quizRepository, QuestionRepository questionRepository,
                             UserRepository userRepository, AnswerRepository answerRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
    }

    @GetMapping("/quiz/{id}")
    public ResponseEntity<Quiz> getById(@PathVariable(value = "id") Long id, Principal principal) {
        System.out.println("PRINCIPAL: [" + principal.getName() + "]");
        return new ResponseEntity<>(
                quizRepository.getById(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/quiz/all")
    public ResponseEntity<List<Quiz>> getAllQuizes() {
        return new ResponseEntity<>(
                quizRepository.findAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/question/{id}")
    public ResponseEntity<Question> getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(
                questionRepository.getById(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/question/all")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return new ResponseEntity<>(
                questionRepository.findAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/quiz/allByUserId/{id}")
    public ResponseEntity<List<Quiz>> getAllByUserId(@PathVariable(value = "id") Long id) {

        List<Quiz> quizesInDB = quizRepository.getAllByUserId(id);
        List<Quiz> quizesToReturn = new ArrayList<>();
        quizesInDB.forEach((quizInDB) -> {
            Quiz quizToReturn = new Quiz();
            quizToReturn.copyFrom(quizInDB,true);
            List<Question> questions = questionRepository.findAllByQuizId(quizInDB.getId());
            questions.forEach((question) -> {
                List<Answer> answers = answerRepository.findAllByQuestionIdAndUserId(question.getId(), id);
                question.setAnswers(answers);
                quizToReturn.getQuestions().add(question);
            });
            quizesToReturn.add(quizToReturn);
        });
        return new ResponseEntity<>(quizesToReturn, HttpStatus.OK);
    }

    @GetMapping("/quiz/allActive")
    public ResponseEntity<List<Quiz>> findAllActiveQuizes() {
        List<Quiz> quizes = quizRepository.findAllActiveQuizes();
        return new ResponseEntity<>(quizes, HttpStatus.OK);
    }

    @PostMapping("/answer/create")
    public ResponseEntity<Void> create(@RequestBody Answer answer) {
        try {
            answerRepository.save(new Answer(answer.getText(),answer.getQuestion(),answer.getUser()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}