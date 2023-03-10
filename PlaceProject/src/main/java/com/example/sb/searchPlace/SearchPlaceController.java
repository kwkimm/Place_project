package com.example.sb.searchPlace;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.RequiredArgsConstructor;

@RequestMapping("/searchPlace")
@RequiredArgsConstructor
@Controller
public class SearchPlaceController {
	
	private final SearchPlaceService searchPlaceService;
	
	@GetMapping("/searchMap")
	public String searchMap() {
		return "searchMap";
	}
	
	@ResponseBody
	@PostMapping("/addPlace")
	public List<Integer> addPlace(@RequestParam String list) {
		
		System.out.println(list);
		List<Integer> pCodeList = new ArrayList<>();
		
		Gson gson = new Gson();
		ArrayList<Map<String, String>> data = gson.fromJson(list, new TypeToken<ArrayList<Map<String, String>>>() {}.getType());
		
		for(Map<String, String> d : data) {
//			System.out.println(d.get("placeName"));
//			System.out.println(d.get("latitude"));
//			System.out.println(d.get("longitude"));
			
			SearchPlace placeInfo = new SearchPlace();
			
			placeInfo.setPlaceName(d.get("placeName"));
			placeInfo.setLatitude(d.get("latitude"));
			placeInfo.setLongitude(d.get("longitude"));
			
			Integer pCode = searchPlaceService.savePlace(placeInfo);
			
			pCodeList.add(pCode);

		}

		return pCodeList;
	}
	
}

