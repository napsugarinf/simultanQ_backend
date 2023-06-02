package com.simultanq.base.entity.dto;

import lombok.*;

import java.util.List;
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private Long id;
    private String text;
    private List<AnswerDTO> answers;
}
