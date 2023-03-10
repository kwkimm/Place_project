package com.example.sb.comm;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sb.board.BoardEntity;
import com.example.sb.board.BoardService;
import com.example.sb.user.UserEntity;
import com.example.sb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/comm")
@RequiredArgsConstructor
@Controller
public class CommController {
	
	private final CommService commService;
	private final BoardService boardService;
	private final UserService userService;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{bCode}")
	public String createComm(Model model, @PathVariable("bCode") Long bCode, 
			@Valid CommForm commForm, BindingResult bindingResult, Principal principal) {
		
		/*
		 * bCode(해당 게시물 기본값)를 통해서 해당 게시물 정보 빼오기
		 */
		BoardEntity boardEntity = boardService.getBoard(bCode);
		
		/*
		 * 로그인한 유저 정보 빼오기
		 */
		UserEntity userEntity = userService.getUser(principal.getName());
		

		if(bindingResult.hasErrors()) {
			model.addAttribute("board", boardEntity);
			return "board_detail";
		}
		
		commService.create(boardEntity, commForm.getContent(), userEntity);
		return "redirect:/board/detail/" + bCode; 
	}
	
	
	/*
	 * 댓글 수정 버튼 클릭식 댓글 수정하는 form으로 넘어가기
	 */
	@GetMapping("/modify/{commCode}")
	public String commModify(Model model,@PathVariable("commCode") Long commCode, CommForm commForm) {
		
		// 해당 댓글 정보 불러오기
		CommEntity commEntity = commService.getComm(commCode);
		
		commForm.setContent(commEntity.getContent());
		model.addAttribute("comm", commEntity);
		return "comm_form";
	}
	
	@PostMapping("/modify/{commCode}")
	public String commModify(@PathVariable("commCode") Long commCode, @Valid CommForm commForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "comm_form"; // 댓글 수정 폼
		}
		
		// 댓글에 해당 게시물 code가져오기
		Long bCode = commService.modify(commCode, commForm.getContent());
		
		return "redirect:/board/detail/" + bCode;
	}
	
	@GetMapping("/delete/{commCode}")
	public String commDelete(@PathVariable("commCode") Long commCode) {
		
		Long bCode = commService.delete(commCode);
		
		return "redirect:/board/detail/" + bCode;
	}
}
