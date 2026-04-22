package com.oussma.foodcatalogue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.oussma.foodcatalogue.mapper.FoodItemMapper;

@SpringBootTest
@ActiveProfiles("test")
class FoodcatalogueApplicationTests {
	
	  @MockitoBean
	    private FoodItemMapper foodItemMapper;

	@Test
	void contextLoads() {
	}

}
