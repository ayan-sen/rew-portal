package com.rew.portal.service.admin.rawMaterial;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import com.rew.portal.model.admin.rawMaterial.RawMaterial;
import com.rew.portal.repository.admin.rawMaterial.RawMaterialRepository;

@Service
public class RawMaterialService {

	@Resource
	private RawMaterialRepository rawMaterialRepository;
	
	public void save(RawMaterial rawMaterial) {
		rawMaterialRepository.save(rawMaterial);
	}
	
	public RawMaterial findById(String code) {
		Optional<RawMaterial> opt = rawMaterialRepository.findById(code);
		return opt.isPresent() ? opt.get() : null;
	}
	
	public List<RawMaterial> findAll() {
		return rawMaterialRepository.findAll();
	}
	
	public List<RawMaterial> findAllRawMaterials() {
		return rawMaterialRepository.findByIsActiveAndType(true, "R");
	}
	
	public List<RawMaterial> findAllProducts() {
		return rawMaterialRepository.findByIsActiveAndType(true, "P");
	}
	
	public void delete(String rmId) throws ObjectNotFoundException {
		Optional<RawMaterial> optRm = rawMaterialRepository.findById(rmId);
		if(optRm.isPresent()) {
			RawMaterial rm = optRm.get();
			rm.setIsActive(false);
			rawMaterialRepository.save(rm);
		} else {
			throw new ObjectNotFoundException("Material not found with client id " , rmId);
		}
	}
}
