package Controller;

//Importing the Java FileInputStream & File classes  
import java.io.FileInputStream;
import java.util.Arrays;
import java.io.File;
//Importing the required classes of Apache POI   
import org.apache.tika.metadata.Metadata;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.parser.ParseContext;

//main class  
public class PDFReader {
//Main method  
	public static void main(String argvs[]) {

		//Creating a file object  
		File fl = new File("/Users/rohajain/Desktop/mht-cet-cap-round-2-MAHA.pdf");
		
		String parsedPdfString = null;
		
		try {
			parsedPdfString = parsePDF(fl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(parsedPdfString == null) {
			System.out.println("Check the PDF file please.");
			System.exit(0);
		}
		
		//converting using String.split() method with line break as a delimiter
		String[] splittedPDFString = parsedPdfString.split("\n");   
		
		splittedPDFString = filterPDF(splittedPDFString);
		
		for (int i = 0; i < splittedPDFString.length; i++) {
			System.out.println(i + " : " + splittedPDFString[i]);
		}
		
	}
	
	public static String[]  filterPDF(String[] data) {

		String[] processedData = Arrays.asList(data)
				.stream()
				.filter(e -> !e.isEmpty())
				.filter(e -> !(e.equalsIgnoreCase("D") || e.equalsIgnoreCase("R") || e.equalsIgnoreCase("I")))
				.filter(e -> !(e.startsWith("*") || e.startsWith("Cut Off")
						|| e.startsWith("Legends") || e.startsWith("PWDR")
						|| e.startsWith("Engineering")))
				.toArray(String[]::new);
		
		return processedData;

	}

	// Method to parse PDF
	public static String parsePDF(File fl) throws Exception {
		// Creating an object of the BodyContentHandler class
		BodyContentHandler ch = new BodyContentHandler(-1);

		// Create a FileInputStream object on
		// the path specified using the created file object file
		FileInputStream fs = new FileInputStream(fl);
		// Creating an object of the type Metadata
		Metadata md = new Metadata();
		// Creating an object of the ParseContext class
		ParseContext pc = new ParseContext();
		// creating an object of the class PDFParser
		PDFParser pp = new PDFParser();
		// calling the parse() method using the
		// object of the PDFParser class
		pp.parse(fs, ch, md, pc);
		// Displaying the contents of the pdf file by invoking the toString() method
		return ch.toString();

	}
}