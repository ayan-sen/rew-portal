package com.rew.portal.repository.transaction.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rew.portal.model.transaction.invoice.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {

}
