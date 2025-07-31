package app.screenertest.datamanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.opencsv.CSVWriter;

public class CSVDataManager {
	String filename;
	String[] recordcol;
	
	public CSVDataManager(String filename) {
		super();
		this.filename = filename;
	}


	public List<String[]> getCsvData(){
		String aStr = null;
        FileInputStream aFile = null;
		try {
			aFile = new FileInputStream(filename);
                } catch (FileNotFoundException e) {
                        e.printStackTrace();
                }
        InputStreamReader aInReader = new InputStreamReader(aFile);
        BufferedReader theBufReader = new BufferedReader(aInReader);
		List<String[]> rows = new ArrayList<>();
		boolean isFirst=true;
		try {
			while ((aStr = theBufReader.readLine()) != null) {
			    if(isFirst) {
			    	this.recordcol = aStr.split(",");
			    	isFirst=false;
			    }
			    else {
				String[] dataLine = aStr.split(",");
			    rows.add(dataLine);
			    }
			}
                } catch (IOException e) {
                        e.printStackTrace();
                }
		return rows;
	}
	
	public void putCsvData(List<String[]> companyinfo) {
		File file = new File(this.filename); 
		 
        try { 
            // create FileWriter object with file as parameter 
            FileWriter outputfile = new FileWriter(file); 
  
            // create CSVWriter with ';' as separator 
            CSVWriter writer = new CSVWriter(outputfile, ',', 
                                             CSVWriter.NO_QUOTE_CHARACTER, 
                                             CSVWriter.DEFAULT_ESCAPE_CHARACTER, 
                                             CSVWriter.DEFAULT_LINE_END); 
               
            System.out.println("Write initiated");
            writer.writeAll(companyinfo); 
  
            // closing writer connection 
            writer.close(); 
            System.out.println("Write completed");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}

	
}
