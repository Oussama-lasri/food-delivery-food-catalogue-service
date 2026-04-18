package com.oussma.foodcatalogue.service;

import com.oussma.foodcatalogue.dto.FoodCataloguePage;
import com.oussma.foodcatalogue.dto.FoodItemDTO;
import com.oussma.foodcatalogue.dto.Restaurant;
import com.oussma.foodcatalogue.entity.FoodItem;
import com.oussma.foodcatalogue.mapper.FoodItemMapper;
import com.oussma.foodcatalogue.repository.FoodItemRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodCatalogueService {

  
    private final FoodItemRepository foodItemRepo;

    private final RestTemplate restTemplate;
    
    private final FoodItemMapper foodItemMapper;


    public FoodItemDTO addFoodItem(FoodItemDTO foodItemDTO) {
        FoodItem foodItemSavedInDB = foodItemRepo.save(foodItemMapper.mapFoodItemDTOToFoodItem(foodItemDTO));
        return foodItemMapper.mapFoodItemToFoodItemDto(foodItemSavedInDB);
    }

    public FoodCataloguePage fetchFoodCataloguePageDetails(Integer restaurantId) {
        List<FoodItem> foodItemList =  fetchFoodItemList(restaurantId);
        Restaurant restaurant = fetchRestaurantDetailsFromRestaurantMS(restaurantId);
        return createFoodCataloguePage(foodItemList, restaurant);
    }

    private FoodCataloguePage createFoodCataloguePage(List<FoodItem> foodItemList, Restaurant restaurant) {
        FoodCataloguePage foodCataloguePage = new FoodCataloguePage();
        foodCataloguePage.setFoodItemsList(foodItemList);
        foodCataloguePage.setRestaurant(restaurant);
        return foodCataloguePage;
    }

    private Restaurant fetchRestaurantDetailsFromRestaurantMS(Integer restaurantId) {
       return restTemplate.getForObject("http://RESTAURANT-SERVICE/restaurant/fetchById/"+restaurantId, Restaurant.class);
    }

    private List<FoodItem> fetchFoodItemList(Integer restaurantId) {
        return foodItemRepo.findByRestaurantId(restaurantId);
    }
}
