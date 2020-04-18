package com.rew.portal.repository.admin.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rew.portal.model.admin.menu.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer>{

}