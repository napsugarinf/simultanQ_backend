package com.simultanq.base.entity.dto;

import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor

//public class UserDTO extends RepresentationModel<UserDTO>{
    public class UserDTO {

    private String username;

    private String password;

    private String role;

    @ToString.Exclude
    private String token;
}
