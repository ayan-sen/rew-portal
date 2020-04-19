package com.rew.portal.repository.admin.unit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rew.portal.model.admin.unit.Unit;

@Repository
public interface UnitRepository extends JpaRepository<Unit, String>{

}
