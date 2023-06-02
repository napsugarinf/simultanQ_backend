package com.simultanq.base.helper;

import com.simultanq.base.entity.Answer;
import com.simultanq.base.entity.Question;
import com.simultanq.base.entity.Quiz;
import com.simultanq.base.entity.dto.AnswerDTO;
import com.simultanq.base.entity.dto.QuestionDTO;
import com.simultanq.base.entity.dto.QuizDTO;

import java.util.List;
import java.util.stream.Collectors;

public class DTOConverters {

    public static QuizDTO convertQuizToDTO(Quiz quiz) {
        List<QuestionDTO> questionDTOs = convertQuestionsToDTO(quiz.getQuestions());
        return QuizDTO.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .PIN(quiz.getPIN())
                .userId(quiz.getUserId())
                .questions(questionDTOs)
                .build();
    }

    public static QuestionDTO convertQuestionToDTO(Question question) {
        List<AnswerDTO> answerDTOs = convertAnswersToDTO(question.getAnswers());
        return QuestionDTO.builder()
                .id(question.getId())
                .text(question.getText())
                .answers(answerDTOs)
                .build();
    }

    public static AnswerDTO convertAnswerToDTO(Answer answer) {
        return AnswerDTO.builder()
                .id(answer.getId())
                .description(answer.getDescription())
                .build();
    }

    public static List<QuestionDTO> convertQuestionsToDTO(List<Question> questions) {
        return questions.stream()
                .map(DTOConverters::convertQuestionToDTO)
                .collect(Collectors.toList());
    }

    public static List<AnswerDTO> convertAnswersToDTO(List<Answer> answers) {
        return answers.stream()
                .map(DTOConverters::convertAnswerToDTO)
                .collect(Collectors.toList());
    }
}
