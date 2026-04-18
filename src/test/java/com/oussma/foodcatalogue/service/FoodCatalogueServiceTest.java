package com.oussma.foodcatalogue.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.oussma.foodcatalogue.dto.FoodCataloguePage;
import com.oussma.foodcatalogue.dto.FoodItemDTO;
import com.oussma.foodcatalogue.dto.Restaurant;
import com.oussma.foodcatalogue.entity.FoodItem;
import com.oussma.foodcatalogue.mapper.FoodItemMapper;
import com.oussma.foodcatalogue.repository.FoodItemRepository;

class FoodCatalogueServiceTest {
	   @Mock
	    private FoodItemRepository foodItemRepo;

	    @Mock
	    private RestTemplate restTemplate;

	    @Mock
	    private FoodItemMapper foodItemMapper;

	    @InjectMocks
	    private FoodCatalogueService foodCatalogueService;
	    
	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this); 
	    }
	    
	    @Test
	    void testAddFoodItem() {
	    	
	    	FoodItemDTO foodDto = new FoodItemDTO(1,"Pizza","Cheese pizza",true,100L,1,5);

	    	FoodItem foodEntity = new FoodItem(1,"Pizza","Cheese pizza",true,100L,1,5);
	    	
	    	when(foodItemMapper.mapFoodItemDTOToFoodItem(foodDto)).thenReturn(foodEntity);
	        when(foodItemRepo.save(foodEntity)).thenReturn(foodEntity);
	        when(foodItemMapper.mapFoodItemToFoodItemDto(foodEntity)).thenReturn(foodDto);

	        FoodItemDTO result = foodCatalogueService.addFoodItem(foodDto);

	        assertEquals(foodDto, result);
	    	
	    }
	    @Test
	    void testFetchFoodCataloguePageDetails() {
	    	Integer restaurantId = 1;
	    	FoodItem foodEntity = new FoodItem(1,"Pizza","Cheese pizza",true,100L,1,5);
	    	FoodItem foodEntity2 = new FoodItem(2,"Tacos","Tacos Pouley",false,100L,1,5);
	    	List<FoodItem> foodList = List.of(foodEntity,foodEntity2);
	    	Restaurant restaurant = new Restaurant(1, "Pizza Palace", "123 Main St", "New York", "Best pizza in town");

	    	 when(foodItemRepo.findByRestaurantId(restaurantId)).thenReturn(foodList);
	    	 when(restTemplate.getForObject(
	                 "http://RESTAURANT-SERVICE/restaurant/fetchById/" + restaurantId,
	                 Restaurant.class))
	             .thenReturn(restaurant);
	    	 FoodCataloguePage result = foodCatalogueService.fetchFoodCataloguePageDetails(restaurantId);

	         assertEquals(foodList, result.getFoodItemsList());
	         assertEquals(restaurant, result.getRestaurant());
	    }
}
