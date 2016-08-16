package textToExcel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TxtReader{
	
	private ExcelWriter writer;
	private HashSet<String> processedPrinters;
	private boolean numberFound;
	private boolean dateFound;
	
	
	public TxtReader(){
		writer = new ExcelWriter();
		processedPrinters = new HashSet<>();
		numberFound = false;
		dateFound = false;
	}
	
	
	
	public void start(String path) throws IOException {		
		
		File textFile = new File(path);
		BufferedReader reader;
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(textFile), "UTF-8"));
		Pattern invoiceNr = Pattern.compile("^.*Rechnungsdetail\\s*([0-9a-zA-Z]*)\\s*");
		Pattern invoiceDate = Pattern.compile("^.*Datum\\s*([0-9a-zA-Z\\-]*)\\s*");
		Pattern serialNum = Pattern.compile("^.*[Ss]eriennummer:\\s([a-zA-Z0-9 ]+)$");
		Pattern pagesPrinted = Pattern.compile("^.*\\(.\\)\\s(.+)\\(([0-9]*)\\)\\s+([0-9]+)\\s+([0-9]+).+$");
		Pattern printerLocation = Pattern.compile("^.*[Ss]tandort:\\s*(.*)\\s\\s.*");
		Matcher printerFound;
		Matcher pageDetails;
		Matcher location;
		Matcher number;
		Matcher date;
		String line = reader.readLine();		
				
		while((line = reader.readLine()) != null){
			printerFound = serialNum.matcher(line);
			pageDetails = pagesPrinted.matcher(line);
			location = printerLocation.matcher(line);
			number = invoiceNr.matcher(line);
			date = invoiceDate.matcher(line);
			
			if(!numberFound && number.matches()){
				writer.writeInvoiceNumber(number.group(1));
				numberFound = true;
			}else if(!dateFound && date.matches()){
				writer.writeInvoiceDate(date.group(1));
				dateFound = true;
			}else if(printerFound.matches()){
				if(!processedPrinters.contains(printerFound.group(1).replace("ONU", "QNU"))){
					processedPrinters.add(printerFound.group(1).replace("ONU", "QNU"));
					writer.writePrinter(printerFound.group(1).replace("ONU", "QNU"));
				}
			}else if(location.matches()){
				if(!processedPrinters.contains(location.group(1).replace(" ", ""))){
					processedPrinters.add(location.group(1).replace(" ", ""));
					writer.writeLocation(location.group(1));
				}
			}else if(pageDetails.matches()){
				writer.writeDetails(Integer.parseInt(pageDetails.group(2)), Double.parseDouble(pageDetails.group(3)), Double.parseDouble(pageDetails.group(4)));
			}
		}
		writer.clearEmptyCells();
		writer.export(createXlsFileExtension(path));
		reader.close();
	}
	
	public String createXlsFileExtension(String path){
		Pattern extension = Pattern.compile("^(.*)\\.(.*)");
		Matcher extMatcher = extension.matcher(path);
		if(extMatcher.matches()){
			return extMatcher.group(1);
		}else{
			return "path_not_found";
		}
	}
}