package app.screenertest.datamanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataManager {
	String filename;
	
	
	

    public  void createExcel() {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
        Object[][] datatypes = {
                {"ISIN", "Type", "Size(in bytes)"},
                {"Name", "Primitive", 2},
                {"CMP", "Primitive", 4},
                {"MCAP", "Primitive", 8},
                {"PE", "Primitive", 1}
        };

        int rowNum = 0;
        System.out.println("Creating excel");

        for (Object[] datatype : datatypes) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object field : datatype) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(this.filename);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");
    }
    
    public String[] downloadScore(FileInputStream file, String sheetName) throws IOException
    {
    	ZipSecureFile.setMinInflateRatio(0.0005);
    	int i=0;
    	try(Workbook workbooks = WorkbookFactory.create(file))
        {
        	XSSFSheet currentSheet = (XSSFSheet) workbooks.getSheet(sheetName);
        	List<Cell> cells = new ArrayList<Cell>();
        	//"NAME","MCAP","CMP","PE"
        	
        	cells.add(currentSheet.getRow(0).getCell(2));
       	 	cells.add(currentSheet.getRow(1).getCell(2));
       	 	cells.add(currentSheet.getRow(2).getCell(2));
       	 	cells.add(currentSheet.getRow(3).getCell(2));
       	 	//cells.add(currentSheet.getRow(4).getCell(2));

       	 	//"Earning1","Earning2","Earning3","Earning4"

       	 	cells.add(currentSheet.getRow(7).getCell(3));
       	 	cells.add(currentSheet.getRow(8).getCell(3));
       	 	cells.add(currentSheet.getRow(9).getCell(3));
       	 	cells.add(currentSheet.getRow(10).getCell(3));
       	 	
			//"DebtScore1","DebtScore2","DebtScore3","DebtScore4","DebtScore5"

       	 	cells.add(currentSheet.getRow(11).getCell(3));
       	 	cells.add(currentSheet.getRow(12).getCell(3));
       	 	cells.add(currentSheet.getRow(13).getCell(3));
       	 	cells.add(currentSheet.getRow(14).getCell(3));
       	 	cells.add(currentSheet.getRow(15).getCell(3));

        	//"CashScore"

       	 	cells.add(currentSheet.getRow(16).getCell(3));

        	//"IV-Max","IV-Avg","IV-Min"

       	 	cells.add(currentSheet.getRow(8).getCell(8));
       	 	cells.add(currentSheet.getRow(8).getCell(9));
       	 	cells.add(currentSheet.getRow(8).getCell(10));
       	    
       	 	//"CAGR-Max","CAGR-Avg","CAGR-Min"
       	 	
       	 	cells.add(currentSheet.getRow(10).getCell(8));
       	 	cells.add(currentSheet.getRow(10).getCell(9));
       	 	cells.add(currentSheet.getRow(10).getCell(10));
       	 	
       	 	//"ReturnMultiple-Max","ReturnMultiple-Avg","ReturnMultiple-Min"
       	 	
       	 	cells.add(currentSheet.getRow(11).getCell(8));
       	 	cells.add(currentSheet.getRow(11).getCell(9));
       	 	cells.add(currentSheet.getRow(11).getCell(10));

       	   // CellValue cellValue = new CellValue(sheetName);
       	 	String[] data = new String[23];
       	    
       	    FormulaEvaluator evaluator = workbooks.getCreationHelper().createFormulaEvaluator();
       	    
       	    for(Cell cell : cells) {
       	    	
       	    	CellValue cellValue = evaluator.evaluate(cell);
       	    	
       	    	switch (cellValue.getCellType()) 
       	    	{
	       	    	case NUMERIC:
	       	    		System.out.println(String.valueOf(cellValue.getNumberValue()));
	       	    		data[i]= String.valueOf(cellValue.getNumberValue());
	       	    		break;
	       	    	
	       	    	case STRING:
	       	    		System.out.println(cellValue.getStringValue());
	       	    		data[i]= cellValue.getStringValue().toString();
	       	    		break;
	       	    	
	       	    	default:
	       	    		break;
       	    	}
       	    	
       	    	i++;               
	        }      	
       	    
        	return data;
        }catch(Exception e) {
        	e.printStackTrace();
        }
    	finally {
    		file.close();
    	}
    	return null;	
    }  
    
    public String[] readExcel(FileInputStream file, String sheetName) throws IOException
    {
    	ZipSecureFile.setMinInflateRatio(0.0005);
    	int i=0;
    	try(Workbook workbooks = WorkbookFactory.create(file))
        {
        	XSSFSheet currentSheet = (XSSFSheet) workbooks.getSheet(sheetName);
        	List<Cell> cells = new ArrayList<Cell>();
        	cells.add(currentSheet.getRow(0).getCell(2));
       	 	cells.add(currentSheet.getRow(1).getCell(2));
       	 	cells.add(currentSheet.getRow(2).getCell(2));
       	 	cells.add(currentSheet.getRow(3).getCell(2));
       	 	cells.add(currentSheet.getRow(4).getCell(2));
       	 	cells.add(currentSheet.getRow(7).getCell(4));
       	 	cells.add(currentSheet.getRow(11).getCell(4));
       	 	cells.add(currentSheet.getRow(16).getCell(4));
       	 	cells.add(currentSheet.getRow(17).getCell(4));
       	 	cells.add(currentSheet.getRow(8).getCell(8));
       	 	cells.add(currentSheet.getRow(8).getCell(9));
       	 	cells.add(currentSheet.getRow(8).getCell(10));
       	 	cells.add(currentSheet.getRow(9).getCell(8));
       	 	cells.add(currentSheet.getRow(9).getCell(9));
       	 	cells.add(currentSheet.getRow(9).getCell(10));
       	 	cells.add(currentSheet.getRow(11).getCell(8));
       	 	cells.add(currentSheet.getRow(11).getCell(9));
       	 	cells.add(currentSheet.getRow(11).getCell(10));
       	 	cells.add(currentSheet.getRow(12).getCell(8));
       	 	cells.add(currentSheet.getRow(12).getCell(9));
       	 	cells.add(currentSheet.getRow(12).getCell(10));
       	   // CellValue cellValue = new CellValue(sheetName);
       	 	String[] data = new String[21];
       	    
       	    FormulaEvaluator evaluator = workbooks.getCreationHelper().createFormulaEvaluator();
       	    
       	    for(Cell cell : cells) {
       	    	
       	    	CellValue cellValue = evaluator.evaluate(cell);
       	    	
       	    	switch (cellValue.getCellType()) 
       	    	{
	       	    	case NUMERIC:
	       	    		System.out.println(String.valueOf(cellValue.getNumberValue()));
	       	    		data[i]= String.valueOf(cellValue.getNumberValue());
	       	    		break;
	       	    	
	       	    	case STRING:
	       	    		System.out.println(cellValue.getStringValue());
	       	    		data[i]= cellValue.getStringValue().toString();
	       	    		break;
	       	    	
	       	    	default:
	       	    		break;
       	    	}
       	    	
       	    	i++;               
	        }      	
       	    
        	return data;
        }catch(Exception e) {
        	e.printStackTrace();
        }
    	finally {
    		file.close();
    	}
    	return null;	
    }  

}
	