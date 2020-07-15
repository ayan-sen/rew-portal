package com.rew.portal.repository.common;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rew.portal.model.common.UserInfo;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, String> {

}
