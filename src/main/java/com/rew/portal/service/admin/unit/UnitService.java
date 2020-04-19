package com.rew.portal.service.admin.unit;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rew.portal.model.admin.unit.Unit;
import com.rew.portal.repository.admin.unit.UnitRepository;

@Service
public class UnitService {

	@Resource
	private UnitRepository unitRepository;
	
	public void addUnit(Unit unit) {
		unitRepository.saveAndFlush(unit);
	}
	
	public void update(Unit unit) {
		unitRepository.saveAndFlush(unit);
	}
	
	public Unit findById(String unitId) {
		Optional<Unit> opt = unitRepository.findById(unitId);
		return opt.isPresent() ? opt.get() : null;
	}
	
	public List<Unit> findAll() {
		return unitRepository.findAll();
	}
}
