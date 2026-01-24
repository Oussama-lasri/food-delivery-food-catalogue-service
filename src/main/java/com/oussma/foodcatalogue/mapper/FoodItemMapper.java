package com.oussma.foodcatalogue.mapper;

import org.mapstruct.Mapper;

import com.oussma.foodcatalogue.dto.FoodItemDTO;
import com.oussma.foodcatalogue.entity.FoodItem;

@Mapper(componentModel = "spring")
public interface FoodItemMapper {
	
	 	FoodItem mapFoodItemDTOToFoodItem(FoodItemDTO foodItemDTO);
	    FoodItemDTO mapFoodItemToFoodItemDto(FoodItem foodItem);
}
