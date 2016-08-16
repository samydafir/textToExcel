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
        extractedText = extractedText.replaceAll("[�]+", "ue");
        extractedText = extractedText.replaceAll("[�]+", "Ue");
        extractedText = extractedText.replaceAll("[�]+", "Oe");
        extractedText = extractedText.replaceAll("[�]+", "oe");
        extractedText = extractedText.replaceAll("[�]+", "ae");
        extractedText = extractedText.replaceAll("[�]+", "Ae");
        extractedText = extractedText.replaceAll("[�]+", "ss");
        extractedText = extractedText.replaceAll("[^A-Za-z0-9.:() \n]+", "");
        tempOutput.write(extractedText);
        tempOutput.close();
        if (cosDocument != null)
        	cosDocument.close();
        if (pdDocument != null)
            pdDocument.close();
	}
}