package ru.agaf.QuizBuilder.rest.controller.admin;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.agaf.QuizBuilder.jpa.model.Question;
import ru.agaf.QuizBuilder.jpa.model.Quiz;
import ru.agaf.QuizBuilder.jpa.repo.AnswerRepository;
import ru.agaf.QuizBuilder.jpa.repo.QuestionRepository;
import ru.agaf.QuizBuilder.jpa.repo.QuizRepository;
import ru.agaf.QuizBuilder.jpa.repo.UserRepository;
import ru.agaf.QuizBuilder.rest.controller.UtilController;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/adminui")
public class AdminAPIController {
    final static Logger logger = Logger.getLogger(AdminAPIController.class);

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public AdminAPIController(QuizRepository quizRepository, QuestionRepository questionRepository,
                              UserRepository userRepository, AnswerRepository answerRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
    }

    @PostMapping("/quiz/create")
    public ResponseEntity<Void> create(@RequestBody Quiz quiz) {
        try {
            quizRepository.save(new Quiz(quiz.getTitle(), quiz.getDescription(), quiz.getStartDate(), quiz.getStopDate()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/question/create")
    public ResponseEntity<Void> create(@RequestBody Question question) {
        try {
            questionRepository.save(new Question(question.getQtext(), question.getType()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/quiz/{id}")
    public ResponseEntity<Quiz> updateById(@PathVariable("id") long id, @RequestBody Quiz updatedQuiz) {
        Optional<Quiz> quizData = quizRepository.findById(id);

        if (quizData.isPresent()) {
            Quiz _quiz = quizData.get();
            _quiz.copyFrom(updatedQuiz,false);
            return new ResponseEntity<>(quizRepository.save(_quiz), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/quiz/{id}")
    public ResponseEntity<HttpStatus> deleteQuizById(@PathVariable("id") long id) {
        try {
            //TODO добавить удаление также его вопросов, а также ответов пользавателей
            quizRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/quiz/addquestion/{id}")
    public ResponseEntity<Void> addQuestionToQuiz(@PathVariable("id") long id, @RequestBody long idq) {
        try {
            Quiz quizToUpdate = quizRepository.getById(id);
            Set<Question> questionList = quizToUpdate.getQuestions();
            Question q = questionRepository.getById(idq);
            questionList.add(q);
            quizToUpdate.setQuestions(questionList);
            quizRepository.save(quizToUpdate);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/question/{id}")
    public ResponseEntity<Question> updateById(@PathVariable("id") long id, @RequestBody Question updatedQestion) {
        Optional<Question> questionData = questionRepository.findById(id);

        if (questionData.isPresent()) {
            Question _question = questionData.get();
            _question.copyFrom(updatedQestion,false);
            return new ResponseEntity<>(questionRepository.save(_question), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/question/{id}")
    public ResponseEntity<HttpStatus> deleteQuestionById(@PathVariable("id") long id) {
        try {
            questionRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
