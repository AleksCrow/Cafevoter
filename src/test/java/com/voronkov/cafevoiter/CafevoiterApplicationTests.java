package com.voronkov.cafevoiter;

import com.voronkov.cafevoiter.controller.CafeRestController;
import com.voronkov.cafevoiter.controller.UserRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CafevoiterApplicationTests {

	@Autowired
	private CafeRestController cafeController;

	@Autowired
	private UserRestController userController;

	@Test
	void contextLoads() throws Exception {
		assertThat(cafeController).isNotNull();
		assertThat(userController).isNotNull();
	}

}
