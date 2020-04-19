package com.rew.portal.controller.admin.menu;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rew.portal.model.admin.menu.Menu;
import com.rew.portal.service.admin.menu.MenuService;

@RestController
public class MenuController {

	@Resource
	private MenuService menuService;
	
	@GetMapping("/admin/menu")
	public List<Menu> getMenu() {
		return menuService.getMenu();
	}
}
