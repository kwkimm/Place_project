package com.example.sb.searchPlace;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SearchPlaceService {
	
	private final SearchPlaceRepository searchPlaceRepository;
	
	public Integer savePlace(SearchPlace placeInfo) {
		
		// DB에 있으면 있는 값 변수 o 에 저장( Optional에 있는 메서드 .orElseGet() 이용 ), 없으면 빈 객체 리턴
		SearchPlace o = searchPlaceRepository
				.findByPlaceNameAndLatitudeAndLongitude(placeInfo.getPlaceName()
						, placeInfo.getLatitude(), placeInfo.getLongitude()).orElseGet(() -> {
							return new SearchPlace();
		});
		
		
		if(o.getPlaceName() == null) {
			System.out.println("없음");
			searchPlaceRepository.save(placeInfo);
		} else {
			System.out.println(o.getPlaceName());
		}
		
		// JPA 이용, DB에 저장하든 말든 해당 pcode추출해서 리턴
		SearchPlace temp = searchPlaceRepository
				.findByPlaceNameAndLatitudeAndLongitude(placeInfo.getPlaceName()
						, placeInfo.getLatitude(), placeInfo.getLongitude()).get();
		
		System.out.println("추출한 코드 : " + temp.getPCode());
		
		return temp.getPCode();
		
	}
	

}
