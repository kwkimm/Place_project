package com.example.sb.board;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardForm {
   @NotEmpty(message = "제목을 입력하세요.")
   private String title;
   @NotEmpty(message = "내용을 입력하세요.")
   private String content;
   private String pName1;
   private String pName2;
   private String pName3;
   
   // 코스장소 pCode
   private Integer pcode1;
   private Integer pcode2;
   private Integer pcode3;
   
}