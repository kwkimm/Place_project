package com.example.sb.searchPlace;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchPlaceRepository extends JpaRepository<SearchPlace, Integer>{

	Optional<SearchPlace> findByPlaceNameAndLatitudeAndLongitude(String placeName, String latitude, String longitude);
	SearchPlace findBypCode(Integer pCode);
	
}
