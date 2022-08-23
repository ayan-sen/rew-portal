package com.rew.portal.repository.transaction.record;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rew.portal.model.transaction.record.TransactionRecord;

@Repository
public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, String> {
	
	public TransactionRecord findByReferenceId(String referenceId);
	
	public void deleteByReferenceId(String referenceId);

	public List<TransactionRecord> findByClientIdAndBuySellFlagAndIsPaymentDoneOrderByReferenceDateAsc(String clientId, String buySellFlag, boolean isPaymentDone);
}
