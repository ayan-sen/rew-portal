package com.rew.portal.controller.admin.rawMaterial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rew.portal.model.admin.rawMaterial.RawMaterial;
import com.rew.portal.service.admin.rawMaterial.RawMaterialService;
@Slf4j
@RestController
public class RawMaterialController {

	@Resource
	private RawMaterialService rawMaterialService;

	@PostMapping("/admin/raw-material")
	public ResponseEntity<Map<String, String>> create(
			@RequestBody RawMaterial rawMaterial) {
		Map<String, String> response = new HashMap<>();
		try {
			rawMaterialService.save(rawMaterial);
			response.put("status", "success");
			response.put("message", "Raw material data saved Successfully");
			return new ResponseEntity<Map<String, String>>(response,
					HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Exception in raw material creation", e);
			response.put("status", "failure");
			response.put("message",
					"Error occurred during raw material creation");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, String>>(response,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/admin/raw-materials")
	public ResponseEntity<List<RawMaterial>> findAllRawMaterials() {
		return new ResponseEntity<List<RawMaterial>>(
				rawMaterialService.findAllRawMaterials(), HttpStatus.OK);
	}

	@GetMapping("/admin/raw-materials/{code}")
	public ResponseEntity<RawMaterial> findById(@PathVariable String code) {
		RawMaterial rawMaterial = rawMaterialService.findById(code);
		return Objects.isNull(rawMaterial) ? 
				new ResponseEntity<RawMaterial>(HttpStatus.NOT_FOUND) 
				: new ResponseEntity<RawMaterial>(rawMaterial, HttpStatus.OK);
	}
	
	@GetMapping("/admin/products")
	public ResponseEntity<List<RawMaterial>> findAllProducts() {
		return new ResponseEntity<List<RawMaterial>>(
				rawMaterialService.findAllProducts(), HttpStatus.OK);
	}
	
	@GetMapping("/admin/materials")
	public ResponseEntity<List<RawMaterial>> findAll() {
		return new ResponseEntity<List<RawMaterial>>(
				rawMaterialService.findAll(), HttpStatus.OK);
	}
	
}
