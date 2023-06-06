package com.simultanq.base.websocket;
import com.simultanq.base.entity.Question;
import com.simultanq.base.entity.Quiz;
import com.simultanq.base.entity.dto.QuizDTO;
import com.simultanq.base.helper.DTOConverters;
import com.simultanq.base.service.QuestionService;
import com.simultanq.base.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class WebSocketQuizController {

    @Autowired
    private
    QuizService quizService;

    @Autowired
    private
    QuestionService questionService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private Map<String, Map<String, Integer>> participants = new HashMap<>();

//    //private Map<String, Integer> participants = new HashMap<>();

    @MessageMapping("/startQuiz/{quizPin}/{playerId}")
    public void startQuiz(@DestinationVariable String quizPin,
                          @Header("playerId") String playerId) {
        
        Quiz quiz = quizService.getQuizByPIN(quizPin);
        int score = 0;

        if (!participants.containsKey(quizPin)) {
            participants.put(quizPin, new HashMap<>());
        }

        participants.get(quizPin).put(playerId, score);

        sendparticipants(quizPin);

        List<Question> questions = quiz.getQuestions();
        if (!questions.isEmpty()) {
            messagingTemplate.convertAndSend("/quiz/" + quizPin + "/" + playerId, questions.get(0));
        }
    }

    @MessageMapping("/submitResponse/{quizPin}/{questionId}/{playerId}")
    public void submitResponse(@DestinationVariable String quizPin,
                               @DestinationVariable Long questionId,
                               String response,
                               @Header("playerId") String playerId) {
        Quiz quiz = quizService.getQuizByPIN(quizPin);
        Question question = questionService.getQuestionById(questionId);
        boolean isCorrect = checkResponse(question, response);
        int points = isCorrect ? 1 : 0;

        updateScore(quizPin, playerId, points);

        sendparticipants(quizPin);

        Question nextQuestion = getNextQuestion(quiz, questionId);
        if (nextQuestion != null) {
            messagingTemplate.convertAndSend("/quiz/" + quizPin + "/" + playerId, nextQuestion);
        } else {
            messagingTemplate.convertAndSend("/quiz/" + quizPin + "/" + playerId, "Quiz completed");
        }
    }

    @MessageMapping("/sendparticipants/{quizPin}")
    public void sendparticipants(@DestinationVariable String quizPin) {
        messagingTemplate.convertAndSend("/quiz/" + quizPin, participants.get(quizPin));
    }

    private void updateScore(String quizPin, String playerId, int points) {
        if (participants.containsKey(quizPin)) {
            Map<String, Integer> quizParticipants = participants.get(quizPin);
            if (quizParticipants.containsKey(playerId)) {
                int score = quizParticipants.get(playerId);
                quizParticipants.put(playerId, score + points);
            }
        }
    }




    private Question getNextQuestion(Quiz quiz, Long currentQuestionId) {
        List<Question> questions = quiz.getQuestions();
        int currentIndex = -1;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getId().equals(currentQuestionId)) {
                currentIndex = i;
                break;
            }
        }
        if (currentIndex != -1 && currentIndex < questions.size() - 1) {
            return questions.get(currentIndex + 1);
        }
        return null;
    }



    private boolean checkResponse(Question question, String response) {
        Long correctAnswerId = question.getCorrectAnswerId();
        // Compare the response with the correct answer's ID
        return response.equals(String.valueOf(correctAnswerId));
    }
}