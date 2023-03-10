package com.example.sb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.sb.board.BoardEntity;
import com.example.sb.board.BoardService;

@Controller
public class MainController {
	@Autowired
	private BoardService boardService;

	@GetMapping("/")
	public String index(Model model) {

		List<BoardEntity> list = boardService.getHiVcnList();
		System.out.println(list);
		model.addAttribute("list", list);

		return "index";
	}

}
