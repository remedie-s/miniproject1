package com.example.mini.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QnAReveiwForm {
    @Transient
    private Long id;
    @NotEmpty(message = "답변은 비어선 안됩니다.")
    @Column(length = 500)
    private String content;

}
