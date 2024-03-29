package com.simultanq.base.websocket;
import com.simultanq.base.entity.Question;
import com.simultanq.base.entity.Quiz;
import com.simultanq.base.entity.dto.QuestionDTO;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


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

    private Map<String, Long> quizTimers = new HashMap<>();

//    //private Map<String, Integer> participants = new HashMap<>();


//    @MessageMapping("/joinQuiz/{quizPin}/{playerId}")
//    public void joinQuiz(@DestinationVariable String quizPin,
//                          @Header("playerId") String playerId) {
//        participants.clear();
//        int score = 0;
//
//        if (!participants.containsKey(quizPin)) {
//            participants.put(quizPin, new HashMap<>());
//        }
//        sendparticipants(quizPin);
//        participants.get(quizPin).put(playerId, score);
//
//
//    }
    @MessageMapping("/startQuiz/{quizPin}/{playerId}")
    public void startQuiz(@DestinationVariable String quizPin,
                          @Header("playerId") String playerId) {
        Quiz quiz1 = quizService.getQuizByPIN(quizPin);
        int score = 0;

        if (!participants.containsKey(quizPin)) {
            participants.put(quizPin, new HashMap<>());
        }

        participants.get(quizPin).put(playerId, score);

        sendparticipants(quizPin);
        startQuizTimer(quizPin);

        List<Question> questions = quiz1.getQuestions();
        if (!questions.isEmpty()) {
            QuestionDTO questionToBeSent = DTOConverters.convertQuestionToDTO(questions.get(0));
            messagingTemplate.convertAndSend("/quiz/" + quizPin + "/" + playerId, questionToBeSent);
        }
    }

    @MessageMapping("/submitResponse/{quizPin}/{questionId}/{playerId}")
    public void submitResponse(@DestinationVariable String quizPin,
                               @DestinationVariable Long questionId,
                               String response,
                               @Header("playerId") String playerId) {
        Quiz quiz1 = quizService.getQuizByPIN(quizPin);
        Question question = questionService.getQuestionById(questionId);

        boolean isCorrect = checkResponse(question, response);
        int points = isCorrect ? 1 : 0;

        updateScore(quizPin, playerId, points);

        sendparticipants(quizPin);

        Question nextQuestion = getNextQuestion(quiz1, questionId);
        if (nextQuestion != null) {
            QuestionDTO nextQuestionToBeSent = DTOConverters.convertQuestionToDTO(nextQuestion);
            messagingTemplate.convertAndSend("/quiz/" + quizPin + "/" + playerId, nextQuestionToBeSent);
        } else {
            messagingTemplate.convertAndSend("/quiz/" + quizPin + "/" + playerId, "Quiz completed");


        }
    }

    @MessageMapping("/sendparticipants/{quizPin}")
    public void sendparticipants(@DestinationVariable String quizPin) {
//        Map<String, Integer> quizParticipants = participants.getOrDefault(quizPin, new HashMap<>());
//
//        // Sort participants by score in descending order
//        List<Map.Entry<String, Integer>> sortedParticipants = new ArrayList<>(quizParticipants.entrySet());
//        sortedParticipants.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
//
//        // Create a LinkedHashMap to maintain the sorted order
//        Map<String, Integer> sortedParticipantsMap = new LinkedHashMap<>();
//        for (Map.Entry<String, Integer> entry : sortedParticipants) {
//            sortedParticipantsMap.put(entry.getKey(), entry.getValue());
//        }
//
//
//        messagingTemplate.convertAndSend("/quiz/" + quizPin, sortedParticipantsMap);
        if (participants.isEmpty()) {
            messagingTemplate.convertAndSend("/quiz/" + quizPin, "No participants");
        }
        else{
            messagingTemplate.convertAndSend("/quiz/" + quizPin, participants.get(quizPin));
        }

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


    @MessageMapping("/endQuiz/{quizPin}")
    private void endQuiz(String quizPin) {
        participants.remove(quizPin);
        messagingTemplate.convertAndSend("/quiz/" + quizPin, participants);

    }



//    @Scheduled(fixedDelay = 1000) // Runs every second
//    public void checkQuizTimeLimits() {
//        for (String quizPin : quizTimers.keySet()) {
//            long quizStartTime = quizTimers.get(quizPin);
//            long currentTime = System.currentTimeMillis();
//            long elapsedTimeInSeconds = (currentTime - quizStartTime) / 1000;
//
//            if (elapsedTimeInSeconds >= 30) { // Time limit is 2 minutes (120 seconds)
//                endQuiz(quizPin);
//            }
//        }
//    }
private void startQuizTimer(String quizPin) {
    if (!quizTimers.containsKey(quizPin)) {
        long currentTime = System.currentTimeMillis();
        quizTimers.put(quizPin, currentTime);
    }
}
    @Bean // Add this bean to the configuration class
    public ScheduledExecutorService quizTimerExecutor() {
        return Executors.newScheduledThreadPool(1);
    }
}