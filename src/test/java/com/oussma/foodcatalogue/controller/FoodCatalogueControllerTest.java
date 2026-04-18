package com.oussma.foodcatalogue.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.oussma.foodcatalogue.dto.FoodCataloguePage;
import com.oussma.foodcatalogue.dto.FoodItemDTO;
import com.oussma.foodcatalogue.dto.Restaurant;
import com.oussma.foodcatalogue.entity.FoodItem;
import com.oussma.foodcatalogue.service.FoodCatalogueService;

public class FoodCatalogueControllerTest {
	
	
		@Mock
		FoodCatalogueService foodCatalogueService;
	
	
		@InjectMocks
		FoodCatalogueController catalogueController;
		

		 @BeforeEach
		    void setUp() {
		        MockitoAnnotations.openMocks(this); 
		    }
	
	 	@Test
	    void testAddFoodItem_success() {

	 		// Arrange
	        FoodItemDTO input = new FoodItemDTO();
	        input.setItemName("Pizza");

	        FoodItemDTO saved = new FoodItemDTO();
	        saved.setItemName("Pizza");

	        when(foodCatalogueService.addFoodItem(any(FoodItemDTO.class)))
	                .thenReturn(saved);
	        
			ResponseEntity<FoodItemDTO> response = catalogueController.addFoodItem(input);

	        assertEquals(HttpStatus.CREATED, response.getStatusCode());
		    assertEquals(saved, response.getBody());
	 }
	 	
	 	@Test
	 	void testFetchRestauDetailsWithFoodMenu_success() {
	 		Integer restaurantId = 1;
	    	FoodItem foodEntity = new FoodItem(1,"Pizza","Cheese pizza",true,100L,1,5);
	    	FoodItem foodEntity2 = new FoodItem(2,"Tacos","Tacos Pouley",false,100L,1,5);
	    	Restaurant restaurant = new Restaurant(1, "Pizza Palace", "123 Main St", "New York", "Best pizza in town");
	    	List<FoodItem> foodList = List.of(foodEntity,foodEntity2);
	    	
	    	FoodCataloguePage foodCataloguePage = new FoodCataloguePage(foodList ,restaurant );
	    	
	    	when(foodCatalogueService.fetchFoodCataloguePageDetails(restaurantId)).thenReturn(foodCataloguePage);
	    	
	    	ResponseEntity<FoodCataloguePage> result = catalogueController.fetchRestauDetailsWithFoodMenu(restaurantId);
	    	
	    	assertEquals(HttpStatus.OK, result.getStatusCode());
	    	assertEquals(foodCataloguePage, result.getBody());
	 	}
	 	
	 	
}
