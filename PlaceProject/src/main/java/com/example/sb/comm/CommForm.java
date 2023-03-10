package com.example.sb.comm;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommForm {
	@NotEmpty(message = "댓글을 입력하세요.")
	private String content;
}
