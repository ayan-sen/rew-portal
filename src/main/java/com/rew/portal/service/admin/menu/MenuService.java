package com.rew.portal.service.admin.menu;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rew.portal.model.admin.menu.Menu;
import com.rew.portal.repository.admin.menu.MenuRepository;

@Service
public class MenuService {

	@Resource
	private MenuRepository menuRepository;
	
	public List<Menu> getMenu() {
		Sort sort = Sort.by(Order.by("menuOrder"));
		List<Menu> menu = menuRepository.findAll(sort);
		
		Map<Integer, List<Menu>> menuMap = menu.stream()
				.filter(m -> m.getParent() != null)
				.collect(Collectors.groupingBy(Menu::getParent));
		
		menuMap.entrySet().forEach(e -> {
			Menu parent = menu.stream().filter(m -> m.getId() == e.getKey()).findFirst().get();
			parent.setChildren(e.getValue());
		});
		
		return menu.stream()
				.filter(m -> (m.getParent() == null && !CollectionUtils.isEmpty(m.getChildren())) ||
						(m.getParent() == null && CollectionUtils.isEmpty(m.getChildren())) )
				.collect(Collectors.toList());
	}
}
