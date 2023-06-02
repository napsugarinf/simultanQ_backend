package com.simultanq.base.entity.dto;

import lombok.*;
import org.hibernate.boot.jaxb.hbm.internal.RepresentationModeConverter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuizDTO extends RepresentationModel<QuizDTO> {
    private Long id;
    private String title;
    private String PIN;
    private String userId;
    private List<QuestionDTO> questions;
}
