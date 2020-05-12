package com.rew.portal.service.admin.companyProfile;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rew.portal.model.admin.companyProfile.CompanyProfile;
import com.rew.portal.repository.admin.companyProfile.CompanyProfileRepository;

@Service
public class CompanyProfileService {

	@Resource
	private CompanyProfileRepository companyProfileRepository;
	
	public CompanyProfile getProfile() {
		return companyProfileRepository.findById("REWPL").get();
	}
}
