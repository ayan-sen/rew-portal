package com.rew.portal.repository.admin.rawMaterial;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rew.portal.model.admin.rawMaterial.RawMaterial;

@Repository
public interface RawMaterialRepository extends JpaRepository<RawMaterial, String> {

	public List<RawMaterial> findByIsActive(boolean isActive);
}
