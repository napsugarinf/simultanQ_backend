package com.simultanq.base.entity.dto;


import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {
    private Long id;
    private String description;
}
