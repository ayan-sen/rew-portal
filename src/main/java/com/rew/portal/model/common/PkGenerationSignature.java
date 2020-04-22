package com.rew.portal.model.common;

import javax.persistence.Transient;


public interface PkGenerationSignature {

	@Transient
	String getPrefix();
	
	@Transient
	String getTableName();
	
	@Transient
	String getidColName();
}
