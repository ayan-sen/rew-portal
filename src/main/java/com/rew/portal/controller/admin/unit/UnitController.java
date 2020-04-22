package com.rew.portal.controller.admin.unit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rew.portal.model.admin.unit.Unit;
import com.rew.portal.service.admin.unit.UnitService;

@Slf4j
@RestController
public class UnitController {

	@Resource
	private UnitService unitService;

	@PutMapping("/admin/unit")
	public ResponseEntity<Map<String, String>> create(@RequestBody Unit unit) {
		Map<String, String> response = new HashMap<>();
		try {
			unitService.addUnit(unit);
			response.put("status", "success");
			response.put("message", "Unit Created Successfully");
			return new ResponseEntity<Map<String,String>>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Exception in unit creation", e);
			response.put("status", "failure");
			response.put("message", e.getMessage());
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String,String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PatchMapping("/admin/unit")
	public ResponseEntity<Map<String, String>> update(@RequestBody Unit unit) {
		Map<String, String> response = new HashMap<>();
		try {
			unitService.update(unit);
			response.put("status", "success");
			response.put("message", "Unit Created Successfully");
			return new ResponseEntity<Map<String,String>>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("status", "failure");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String,String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/admin/units")
	public ResponseEntity<List<Unit>> findAll() {
		return  new ResponseEntity<List<Unit>>(unitService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/admin/units/{unitId}")
	public ResponseEntity<Unit> findById(@PathVariable String unitId) {
		Unit unit = unitService.findById(unitId);
		return Objects.isNull(unit) ? new ResponseEntity<Unit>(HttpStatus.NOT_FOUND) : new ResponseEntity<Unit>(unit, HttpStatus.OK);
	}
}
