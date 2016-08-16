package textToExcel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PdfToText {
	
	public void parsePdf(String path) throws IOException{

	    PDFParser parser;
	    PDDocument pdDocument;
	    COSDocument cosDocument;
	    PDFTextStripper textStripper;

	    BufferedWriter tempOutput = new BufferedWriter(new FileWriter(new File("temp.txt")));
	    String extractedText;
	    File file = new File(path);
	    FileInputStream input = new FileInputStream(file);
        parser = new PDFParser(input);
        parser.parse();
        cosDocument = parser.getDocument();
        textStripper = new PDFTextStripper();
        pdDocument = new PDDocument(cosDocument);
        extractedText = textStripper.getText(pdDocument);
        extractedText = extractedText.replaceAll("[ü]+", "ue");
        extractedText = extractedText.replaceAll("[Ü]+", "Ue");
        extractedText = extractedText.replaceAll("[Ö]+", "Oe");
        extractedText = extractedText.replaceAll("[ö]+", "oe");
        extractedText = extractedText.replaceAll("[ä]+", "ae");
        extractedText = extractedText.replaceAll("[Ä]+", "Ae");
        extractedText = extractedText.replaceAll("[ß]+", "ss");
        extractedText = extractedText.replaceAll("[^A-Za-z0-9.:() \n]+", "");
        tempOutput.write(extractedText);
        tempOutput.close();
        if (cosDocument != null)
        	cosDocument.close();
        if (pdDocument != null)
            pdDocument.close();
	}
}