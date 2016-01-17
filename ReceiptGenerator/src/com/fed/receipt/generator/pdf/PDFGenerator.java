package com.fed.receipt.generator.pdf;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.fed.dev.utilities.DataUtilities;
import com.fed.receipt.generator.obj.Product;
import com.fed.receipt.generator.utils.Utilities;
import com.fed.receipt.generator.views.receipt.table.TableData;

public class PDFGenerator {
	private static final int Product_Font_Size = 10;
	private static float currentTextYPos;
	private static float currentTextXPos;
//	private static float marginTop = 20;
	private static float marginLeft = 20;
//	private static float marginBottom = 20;
	private static float marginRight = 20;
	private static float writeablePageWidth;
	private static String businessName = "OBET & LODIES MARKETING";
	private static String[] receiptHeader = {"Kingsville Hills Mahogany St. San Jose Antipolo City", "ROBERTO L. CABIGAO - Prop Tel. 630-3009", "NONVAT Reg. TIN: 109-349-024-000"};
	private static String textOrderSlip = "ORDER SLIP";
	private static String textOrderName = "RAMIL";
	private static String deliveredTo = "DELIVERED TO ";
	private static String textDate = "Date ";
	private static String date = " January 01 2015";
	private static String textAddress ="Address ";
	private static String address = " Angono";
	private static String textPoNumber = "P.O. No. ";
	private static String poNumber = "";
	private static String textTerms = "Terms ";
	
	private static String qty = getPadding(4)+"Qty."+getPadding(4);
	private static String unit = getPadding(8)+"Unit"+getPadding(8);
	private static String desc = getPadding(9)+"Description"+getPadding(9);
	private static String unitPrice = getPadding(7)+"Unit Price"+getPadding(7);
	private static String amount = getPadding(5)+"Amount"+getPadding(5);
	
	private static PDFont fontHelvetica = PDType1Font.HELVETICA;
	private static PDFont fontHelveticaBold = PDType1Font.HELVETICA_BOLD;
	
	private static float qtyWidth;
	private static float unitWidth;
	private static float descWidth;
	private static float unitPriceWidth;
	private static float amountWidth;
	
	private File pdfFile;
	
	
	static DecimalFormat df = new DecimalFormat("###,##0.00");
	{
		df.setGroupingUsed(true);
		qtyWidth = getTextWidth(10, fontHelveticaBold, qty);
		unitWidth = getTextWidth(10, fontHelveticaBold, unit);
		descWidth = getTextWidth(10, fontHelveticaBold, desc);
		unitPriceWidth = getTextWidth(10, fontHelveticaBold, unitPrice);
		amountWidth = getTextWidth(10, fontHelveticaBold, amount);
	}

	public PDFGenerator(TableData data) throws IOException, COSVisitorException {
		PDDocument document = new PDDocument();
		PDPage page = getNewPdfPage(document);
		PDPageContentStream contentStream = writePdfHeader(document, page);
		
		for (Product product : data) {
			if(currentTextYPos-(getFontHeight(Product_Font_Size, fontHelvetica))<0){
				contentStream.close();
				page = getNewPdfPage(document);
				contentStream = writePdfHeader(document, page);
			}
			currentTextYPos = addReceiptRow(page, fontHelvetica, fontHelveticaBold, contentStream,
					qtyWidth, unitWidth, descWidth, unitPriceWidth, amountWidth, product);
			
			System.out.println(product.getDescription()+":"+currentTextYPos);
		}
		/*Product prod1 = new Product();
		prod1.setDescription("Product1");
		prod1.setPrice(new BigDecimal(100.25));
		prod1.setQty(100);
		prod1.setUnit("Bottle");
		
		currentTextYPos = addReceiptRow(page, fontHelvetica, fontHelveticaBold, contentStream,
				qtyWidth, unitWidth, descWidth, unitPriceWidth, amountWidth, prod1);
		
		
		Product prod2 = new Product();
		prod2.setDescription("Product2");
		prod2.setPrice(new BigDecimal(10));
		prod2.setQty(100);
		prod2.setUnit("Box");
		currentTextYPos = addReceiptRow(page, fontHelvetica, fontHelveticaBold, contentStream,
				qtyWidth, unitWidth, descWidth, unitPriceWidth, amountWidth, prod2);*/
		
		
		contentStream.close();
		String fileName = Utilities.getInstance().getFileUtil().getTimestampAsFileName("pdf");
		File pdfFile = new File("printedFiles/"+fileName);
		setPdfFile(pdfFile);
		document.save(pdfFile);
		document.close();
	}

	private PDPage getNewPdfPage(PDDocument document) {
		currentTextXPos = marginLeft;
		PDPage page = new PDPage(PDPage.PAGE_SIZE_A5);
		document.addPage(page);
		return page;
	}

	private PDPageContentStream writePdfHeader(PDDocument document, PDPage page)
			throws IOException {
		currentTextYPos = (page.getMediaBox().getHeight())-20;
		writeablePageWidth = ((page.getMediaBox().getWidth() - marginLeft) - marginRight);
		
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		contentStream.setLineWidth(0.1f);
		writeCenterText(15, fontHelveticaBold, page, contentStream, 2, businessName);
		writeCenterText(9, fontHelvetica, page, contentStream, receiptHeader);
		writeCenterText(10, fontHelveticaBold, page, contentStream, textOrderSlip);
		
		writeLeftText(9, fontHelvetica, page, contentStream, deliveredTo);
		writeLeftTextUnderline(10, fontHelveticaBold, page, contentStream, textOrderName+getPadding(50));
		writeLeftText(9, fontHelvetica, page, contentStream, textDate);
		writeLeftTextUnderline(10, fontHelveticaBold, page, contentStream, date+getRowPadding(date,fontHelveticaBold));
		
		writeLeftTextNewLine(9, fontHelvetica, page, contentStream, textAddress);
		writeLeftTextUnderline(10, fontHelveticaBold, page, contentStream, address+getPadding(59));
		writeLeftText(9, fontHelvetica, page, contentStream, textPoNumber);
		writeLeftTextUnderline(10, fontHelveticaBold, page, contentStream, poNumber+getRowPadding(poNumber,fontHelveticaBold));
		
		writeLeftTextNewLine(9, fontHelveticaBold, page, contentStream, getPadding(14));
		writeLeftTextUnderline(11, fontHelveticaBold, page, contentStream, getPadding(59+(address.length()+1)));
		writeLeftText(9, fontHelvetica, page, contentStream, textTerms);
		writeLeftTextUnderline(10, fontHelveticaBold, page, contentStream, getRowPadding(textTerms,fontHelveticaBold));
		
		writeLeftTextNewLine(9, fontHelvetica, page, contentStream, "");
		writeLeftTextUnderline(10, fontHelveticaBold, page, contentStream, getRowPadding("",fontHelveticaBold));
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(qty);
		sb.append(unit);
		sb.append(desc);
		sb.append(unitPrice);
		sb.append(amount);
		
		
		writeLeftTextNewLine(10, fontHelveticaBold, page, contentStream, "");
		writeLeftTextUnderline(10, fontHelveticaBold, page, contentStream, sb.toString());
		return contentStream;
	}

	private float addReceiptRow(PDPage page, PDFont fontHelvetica,
			PDFont fontHelveticaBold, PDPageContentStream contentStream,
			float qtyWidth, float unitWidth, float descWidth,
			float unitPriceWidth, float amountWidth, Product product) throws IOException {
		writeLeftTextNewLine(10, fontHelveticaBold, page, contentStream, "");
		float dataToReturn = processRowColumn(page, fontHelvetica,
				contentStream, qtyWidth, DataUtilities.toStringValue(product.getQty()));
		
		dataToReturn = processRowColumn(page, fontHelvetica,
				contentStream, unitWidth, DataUtilities.toStringValue(product.getUnit()));
		
		dataToReturn = processRowColumn(page, fontHelvetica,
				contentStream, descWidth, DataUtilities.toStringValue(product.getDescription()));
		
		dataToReturn = processRowColumn(page, fontHelvetica,
				contentStream, unitPriceWidth, df.format(product.getPrice()));
		
		dataToReturn = processRowColumn(page, fontHelvetica,
				contentStream, amountWidth, df.format(product.getTotalAmount()));
		
		return dataToReturn;
	}

	private float processRowColumn(PDPage page, PDFont fontHelvetica,
			PDPageContentStream contentStream, float columnWidth, String columnValue)
			throws IOException {
		float dataToReturn = currentTextYPos;
		float currentRowYPos = writeCenterColumnText(Product_Font_Size, fontHelvetica, page, contentStream, columnWidth, columnValue);
		if(currentTextYPos<currentRowYPos) {
			dataToReturn = currentRowYPos;
		}
		return dataToReturn;
	}

	public static String getPadding(int distance) {
		return String.format("%-"+distance+"s", " ");
	}
	
	public String getPadding(String text, PDFont font) throws IOException {
		int tmpWidth = (int) getTextWidth(10, font, text);
		int paddingSpace = (int) (currentTextXPos+tmpWidth+marginRight+marginLeft+5);
		int distance = Math.abs(Math.round(writeablePageWidth-paddingSpace));
		return String.format("%-"+distance+"s", " ");
	}
	
	public String getRowPadding(String text, PDFont font) throws IOException {
		int tmpWidth = (int) getTextWidth(10, font, text);
		int paddingSpace = (int) (currentTextXPos+tmpWidth+marginRight+marginLeft+5);
		int distance = Math.abs(Math.round(writeablePageWidth-paddingSpace));
		int requiredDistance = (int) Math.abs(writeablePageWidth-currentTextXPos);
		if(requiredDistance!=distance) {
			distance = requiredDistance;
		}
		return String.format("%-"+distance+"s", " ");
	}
	
	public void newLine(PDFont font, int fontSize, PDPageContentStream contentStream) throws IOException {
		float fontHeight = getFontHeight(fontSize, font);
		contentStream.appendRawCommands(fontHeight + " TL\n");
		contentStream.appendRawCommands("T*\n");
	}
	
	public void writeCenterText(int fontSize, PDFont font, PDPage page, PDPageContentStream contentStream, String... text) throws IOException {
		contentStream.setFont( font, fontSize );
		float fontHeight = getFontHeight(fontSize, font);
		currentTextYPos -= fontHeight;
		for(String each:text) {
			float textWidth = getTextWidth(fontSize, font, each);
			float currentTextXPos = (page.getMediaBox().getWidth() - textWidth) / 2;
			contentStream.beginText();
			contentStream.moveTextPositionByAmount(currentTextXPos, currentTextYPos);
			contentStream.drawString(each);
			contentStream.endText();
			currentTextYPos -= fontHeight;
		}
	}
	
	private float determineCursorLocation(float textWidth, float locationWidth) {
		return currentTextXPos+((locationWidth - textWidth) / 2);
	}
	
	public float writeCenterColumnText(int fontSize, PDFont font, PDPage page, PDPageContentStream contentStream, float locationWidth, String value) throws IOException {
		contentStream.setFont( font, fontSize );
		float textWidth = getTextWidth(fontSize, font, value);
		float cursorLocation = determineCursorLocation(textWidth, locationWidth);
		float fontHeight = getFontHeight(fontSize, font);
		float tmpcurrentTextYPos = currentTextYPos;
		if(locationWidth<textWidth) {
			int part = (int)Math.floor(textWidth/locationWidth);
			int modulo = value.length()/part;
			int section = value.length()/part;
			
			for(int i=0; i<part; i++) {
				int start = (i*section);
				String sectionValue = value.substring(start, (start+(section)));
				cursorLocation = determineCursorLocation(getTextWidth(fontSize, font, sectionValue), locationWidth);
				writeText(contentStream, cursorLocation, tmpcurrentTextYPos,
						sectionValue);
				tmpcurrentTextYPos -= fontHeight;
			}
			
			if(modulo != 0) {
				int start = (part)*section;
				String sectionValue = value.substring(start, value.length());
				cursorLocation = determineCursorLocation(getTextWidth(fontSize, font, sectionValue), locationWidth);
				writeText(contentStream, cursorLocation, tmpcurrentTextYPos,
						sectionValue);
			} 
		} else {
			writeText(contentStream, cursorLocation, tmpcurrentTextYPos, value);
		}
		currentTextXPos+=locationWidth;
		return tmpcurrentTextYPos;
	}

	private float getFontHeight(int fontSize, PDFont font) {
		float fontHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
		return fontHeight;
	}

	private void writeText(PDPageContentStream contentStream,
			float cursorLocation, float tmpcurrentTextYPos, String sectionValue)
			throws IOException {
		contentStream.beginText();
		contentStream.moveTextPositionByAmount(cursorLocation, tmpcurrentTextYPos);
		contentStream.drawString(sectionValue);
		contentStream.endText();
	}
	
	
	
	public void writeCenterText(int fontSize, PDFont font, PDPage page, PDPageContentStream contentStream, int spacing, String... text) throws IOException {
		contentStream.setFont( font, fontSize );
		float fontHeight = getFontHeight(fontSize, font);
		currentTextYPos -= fontHeight;
		for(String each:text) {
			float textWidth = getTextWidth(fontSize, font, each);
			float currentTextXPos = (page.getMediaBox().getWidth() - textWidth) / 2;
			contentStream.beginText();
			contentStream.moveTextPositionByAmount(currentTextXPos, currentTextYPos);
			contentStream.drawString(each);
			contentStream.endText();
			currentTextYPos -= spacing;
		}
	}
	
	public void writeLeftText(int fontSize, PDFont font, PDPage page, PDPageContentStream contentStream, String... text) throws IOException {
		contentStream.setFont( font, fontSize );
//		float fontHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
		writeText(fontSize, font, contentStream,text);
	}
	
	public void writeLeftTextUnderline(int fontSize, PDFont font, PDPage page, PDPageContentStream contentStream, String text) throws IOException {
		contentStream.setFont( font, fontSize );
//		float fontHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
		writeText(fontSize, font, contentStream, text);
		/*float textWidth = getTextWidth(fontSize, font, text);
		float tmpX = currentTextXPos-textWidth;
		contentStream.drawLine(tmpX, currentTextYPos - 2, tmpX+textWidth-2, currentTextYPos - 2);*/
		
		float textWidth = getTextWidth(fontSize, font, text);
		float startX = currentTextXPos-textWidth;
		float startY = currentTextYPos - 2;
		float endX = startX+textWidth-2;
		float endY = currentTextYPos - 2;
		if(endX>writeablePageWidth) {
			endX = writeablePageWidth;
		}
		contentStream.drawLine(startX, startY, endX, endY);
	}
	
	public void writeLeftTextNewLine(int fontSize, PDFont font, PDPage page, PDPageContentStream contentStream, String... text) throws IOException {
		contentStream.setFont( font, fontSize );
		float fontHeight = getFontHeight(fontSize, font);
		currentTextYPos -= fontHeight;
		currentTextXPos = marginLeft;
		writeText(fontSize, font, contentStream, text);
	}

	private void writeText(int fontSize, PDFont font,
			PDPageContentStream contentStream, 
			String... text) throws IOException {
		contentStream.beginText();
		contentStream.moveTextPositionByAmount(currentTextXPos, currentTextYPos);
		float currentTextWidth = 0;
		for(String each:text) {
			StringBuilder textToWrite = new StringBuilder();
			for(String word:each.split("\n", -1)) {
				float textWidth = getTextWidth(fontSize, font, word);
				currentTextWidth += textWidth;
				if(currentTextWidth<=writeablePageWidth) {
					textToWrite.append(word);
				} else {
					writeTextNextLine(fontSize, font,
							contentStream, textToWrite);
					textToWrite.append(word);
				}
			}
			
			if(textToWrite.length()>0) {
				/*LineObj line = new LineObj();
				line.setStartX(currentTextXPos + 2);
				line.setStartY(currentTextYPos - 2);
				line.setEndX(currentTextWidth);
				line.setEndY(currentTextYPos - 2);
				underLines.add(line);*/
				contentStream.drawString(textToWrite.toString());
				float textWidth = getTextWidth(fontSize, font, textToWrite.toString());
				currentTextXPos += textWidth;
			}
		}
		contentStream.endText();
	}

	private static float getTextWidth(int fontSize, PDFont font, String word)
			throws IOException {
		float textWidth = font.getStringWidth(word) / 1000 * fontSize;
		return textWidth;
	}
	
	

	private void writeTextNextLine(int fontSize, PDFont font,
			PDPageContentStream contentStream, StringBuilder textToWrite)
			throws IOException {
		contentStream.drawString(textToWrite.toString());
		newLine(font, fontSize, contentStream);
		currentTextXPos = 20;
	}

	public File getPdfFile() {
		return pdfFile;
	}

	public void setPdfFile(File pdfFile) {
		this.pdfFile = pdfFile;
	}
}
