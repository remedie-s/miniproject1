
package com.example.mini.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewForm {
    private Long id;
    private Long productid;
    private String contents;
    private Long userid;
}
