package com.rew.portal.service.transaction.orderPlacement;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javassist.NotFoundException;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rew.portal.model.admin.companyProfile.CompanyProfile;
import com.rew.portal.model.transaction.orderPlacement.OrderPlacement;
import com.rew.portal.model.transaction.orderPlacement.OrderPlacementDetails;
import com.rew.portal.model.transaction.project.Project;
import com.rew.portal.repository.transaction.orderPlacement.OrderPlacementRepository;
import com.rew.portal.repository.transaction.project.ProjectRepository;
import com.rew.portal.service.admin.companyProfile.CompanyProfileService;

@Service
public class OrderPlacementService {

	@Resource
	private OrderPlacementRepository orderPlacementRepository;
	
	@Resource
	private ProjectRepository projectRepository;
	
	@Resource
	private CompanyProfileService companyProfileService;
	
	@Autowired
    ResourceLoader resourceLoader;
	
	public OrderPlacement save(OrderPlacement orderPlacement) {
		List<OrderPlacementDetails> details = orderPlacement.getDetails();
		details.forEach(d -> d.setOrderPlacement(orderPlacement));
		return orderPlacementRepository.save(orderPlacement);
	}
	
	public OrderPlacement findById(String orderId) {
		Optional<OrderPlacement> opt = orderPlacementRepository.findById(orderId);
		OrderPlacement op = opt.isPresent() ? opt.get() : null;
		if(Objects.nonNull(op)) {
			Project project = projectRepository.findLatest(op.getProjectId());
			op.setProject(project);
		}
		return op;
	}
	
	public List<OrderPlacement> findAll() {
		return orderPlacementRepository.findByIsActive(true);
	}
	
	public void delete(String orderPlacementId) throws NotFoundException {
		OrderPlacement orderPlacement = this.findById(orderPlacementId);
		if(Objects.nonNull(orderPlacement)) {
			orderPlacement.setIsActive(false);
			orderPlacementRepository.save(orderPlacement);
		} else {
			throw new NotFoundException("Order not found with order id" + orderPlacementId);
		}
	}
	
	public void deleteDetail(String orderPlacementId, int detailId) throws NotFoundException {
		OrderPlacement orderPlacement = this.findById(orderPlacementId);
		if(Objects.nonNull(orderPlacement)) {
			orderPlacement.removeDetail(detailId);
			orderPlacementRepository.save(orderPlacement);
		} else {
			throw new NotFoundException("Order detail not found with id " + orderPlacementId);
		}
	}
	
	public ByteArrayInputStream generateInvoice(String orderPlacementId) throws DocumentException, MalformedURLException, URISyntaxException, IOException {
		OrderPlacement orderPlacement = this.findById(orderPlacementId);
		CompanyProfile companyProfile = companyProfileService.getProfile();
		
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		//PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("f:\\gen\\"+System.currentTimeMillis()+".pdf"));
		PdfWriter writer = PdfWriter.getInstance(document, out);
		document.open();
		
		enrichHeader(document, companyProfile);
		enrichBody(document, orderPlacement, companyProfile);
		footer(companyProfile, document, writer);
		
		document.add(new Chunk(""));
		document.close();
		return new ByteArrayInputStream(out.toByteArray());
	}

	private void enrichHeader(Document document, CompanyProfile companyProfile) throws URISyntaxException, MalformedURLException, IOException, DocumentException {
		
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100);
		
		org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:/static/image/logo.png");
		Path path = Paths.get(resource.getURI());
		Image img = Image.getInstance(path.toAbsolutePath().toString());
		img.scalePercent(80);

		PdfPCell imageCell = new PdfPCell(img);
		imageCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		imageCell.setBorder(0);
		table.addCell(imageCell);

		Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
		PdfPCell cell = new PdfPCell(new Phrase(companyProfile.getCompanyName(), headFont));
		cell.setBorder(0);
		cell.setColspan(3);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(cell);
		
		org.springframework.core.io.Resource resource2 = resourceLoader.getResource("classpath:/static/image/urs.png");
		Path path2 = Paths.get(resource2.getURI());
		Image img2 = Image.getInstance(path2.toAbsolutePath().toString());
		img2.scalePercent(80);
		PdfPCell imageCell2 = new PdfPCell(img2);
		imageCell2.setBorder(0);
		imageCell2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		table.addCell(imageCell2);
		
		document.add(table);
		
		PdfPTable cinTable = new PdfPTable(5);
		Font cinFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);
		PdfPCell cinCell = new PdfPCell(new Phrase("Corporate Identity Number (CIN) :" + companyProfile.getCin(), cinFont));
		cinCell.setBorder(0);
		cinCell.setColspan(5);
		cinCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cinTable.addCell(cinCell);
		
		document.add(cinTable);
	}
	
	private void enrichBody(Document document, OrderPlacement orderPlacement, CompanyProfile companyProfile) throws DocumentException {
		Font pFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
		
		Font uFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 11);
		uFont.setStyle(Font.UNDERLINE);
		
		Font bFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10);
		
		Paragraph para = new Paragraph();
		para.add(new Phrase(orderPlacement.getOrderId(), pFont));
		para.add(Chunk.NEWLINE);
		para.add(new Phrase("Dated : " + orderPlacement.getOrderDateString(), uFont));
		para.add(Chunk.NEWLINE);
		para.add(Chunk.NEWLINE);
		para.add(Chunk.NEWLINE);
		para.add(new Phrase(orderPlacement.getSupplierName(), pFont));
		para.add(Chunk.NEWLINE);
		pFont.setSize(10);
		para.add(new Phrase(orderPlacement.getSupplierDetails().getAddress(), pFont));
		para.add(Chunk.NEWLINE);
		para.add(Chunk.NEWLINE);
		para.add(Chunk.NEWLINE);
		para.add(new Phrase("Re : Purchase Order", pFont));
		para.add(Chunk.NEWLINE);
		para.add(Chunk.NEWLINE);
		para.add(new Phrase("Dear Sir,", bFont));
		para.add(Chunk.NEWLINE);
		para.add(Chunk.NEWLINE);
		para.add(new Phrase("We are pleased to place the following order on you. Please supply the same immediately.", bFont));
		para.add(Chunk.NEWLINE);
		para.add(Chunk.NEWLINE);
		
		document.add(para);
		
		PdfPTable itemTable = new PdfPTable(3);
		itemTable.setSpacingAfter(5);
		pFont.setSize(10);
		PdfPCell cell = new PdfPCell(new Phrase("Item", pFont));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		itemTable.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Quantity", pFont));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		itemTable.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Rate", pFont));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		itemTable.addCell(cell);
		
		Hibernate.initialize(orderPlacement.getDetails());
		List<OrderPlacementDetails> details = orderPlacement.getDetails();
		
		for(OrderPlacementDetails detail : details) {
			
			cell = new PdfPCell(new Phrase(detail.getRawMaterial().getName(), bFont));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			itemTable.addCell(cell);
			
			cell = new PdfPCell(new Phrase(detail.getQuantity() + " " + detail.getUnit().getUnitName(), bFont));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			itemTable.addCell(cell);
			
			cell = new PdfPCell(new Phrase(detail.getRate().toString(), bFont));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			itemTable.addCell(cell);
		}
		
		document.add(itemTable);
		
		para = new Paragraph();
		
		para.add(new Phrase("GST : Extra at actual", bFont));
		para.add(Chunk.NEWLINE);
		para.add(Chunk.NEWLINE);
		para.add(new Phrase("Freight :" + orderPlacement.getFreightChargeType(), bFont));
		para.add(Chunk.NEWLINE);
		para.add(Chunk.NEWLINE);
		para.add(new Phrase("Payment :" + orderPlacement.getPaymentTerms(), bFont));
		para.add(Chunk.NEWLINE);
		para.add(Chunk.NEWLINE);
		para.add(new Phrase("Delivary : At " + orderPlacement.getSiteId(), bFont));
		para.add(Chunk.NEWLINE);
		para.add(Chunk.NEWLINE);
		para.add(new Phrase("Regards,", bFont));
		para.add(Chunk.NEWLINE);
		para.add(Chunk.NEWLINE);
		para.add(new Phrase("FOR RABI ENGINEERING WORKS PVT. LTD.", pFont));
		para.add(Chunk.NEWLINE);
		para.add(new Phrase("(A.Sen)", pFont));
		para.add(Chunk.NEWLINE);
		para.add(Chunk.NEWLINE);
		para.add(new Phrase("Delivary Address: " + companyProfile.getDetails().stream().filter(d -> d.getSiteId().equalsIgnoreCase(orderPlacement.getSiteId())).findAny().get().getAddress(), bFont));
		para.add(Chunk.NEWLINE);
		para.add(Chunk.NEWLINE);
		para.add(new Phrase("Billing Address: " + companyProfile.getRegAddress(), bFont));
		document.add(para);
	}
	
	private void footer(CompanyProfile companyProfile, Document document,
			PdfWriter writer) throws DocumentException {
		Font bFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8);
		Rectangle rect = new Rectangle(document.left()+ 110, document.bottom() - 30, document.right(), document.bottom());
		
		ColumnText ct = new ColumnText(writer.getDirectContent());
		ct.setSimpleColumn(rect);
		ct.addElement(new Phrase("        OFFICE & WORKS: " + companyProfile.getRegAddress() + "\nP: " + companyProfile.getPrimaryContactNo() +","+ companyProfile.getSecondContactNo()+ 
	    		",E:"+companyProfile.getPrimaryEmailId() +", W: "+ companyProfile.getWebsite(), bFont));
		ct.go();
	}
	
}
