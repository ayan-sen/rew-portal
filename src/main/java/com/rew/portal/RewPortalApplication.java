package com.rew.portal;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.rew.portal.model.admin.menu.Menu;
import com.rew.portal.service.admin.menu.MenuService;

@SpringBootApplication
@EnableJpaRepositories
public class RewPortalApplication implements CommandLineRunner {

	@Resource
	private MenuService menuService;
	
	public static void main(String[] args) {
		SpringApplication.run(RewPortalApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		List<Menu> menuList = menuService.getMenu();
		System.out.println(menuList);
	}

}


