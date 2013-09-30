package tu.sofia.aez.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import tu.sofia.aez.om.Dvigatel;

public class ExcelService {
	private final static String filename="e:\\engines.xlsx";


	public List<Dvigatel> loadDvigateli()  throws InvalidFormatException, IOException{
		File excelFile = new File(filename); 
		Workbook wb = WorkbookFactory.create(excelFile);
		Sheet firstSheet = wb.getSheetAt(0);
		List<Dvigatel> resultList = new ArrayList<Dvigatel>();
		Iterator<Row> rows = firstSheet.rowIterator();
		while (rows.hasNext()) {
			Row row = rows.next();
			Iterator<Cell> cells = row.cellIterator();
			int index = 0;
			boolean first = true;
			double[] values = new double[Dvigatel.VALUES_SIZE];
			String name = "";
			while (cells.hasNext()) {
				Cell cell = cells.next();
				if (first) {
					name = cell.getStringCellValue();
					first=false;
				} else {
					if (Dvigatel.VALUES_SIZE < index - 1)
						break;
					try {
						values[index] = cell.getNumericCellValue(); // Skip rows
																	// with not
																	// numeric
																	// values
					} catch (Exception e) {
						break;
					}

					index++;
				}
			}
			if (Dvigatel.VALUES_SIZE != index)
				continue; // Skip rows with more or less values than needed for
							// an engine

			Dvigatel newDvigatel = new Dvigatel(values, name);
			resultList.add(newDvigatel);
		}
		return resultList;
	}
	
	public void addDvigatel(Dvigatel dvg) throws InvalidFormatException, IOException{
		InputStream inp = new FileInputStream(filename);  
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheetAt(0);
		int newRowNum = sheet.getLastRowNum()+1;    
		Row row=sheet.createRow(newRowNum);
		row.createCell(0).setCellValue(dvg.getName());
		row.createCell(1).setCellValue(dvg.getpN());
		row.createCell(2).setCellValue(dvg.getU1n());
		row.createCell(3).setCellValue(dvg.getU2n());
		row.createCell(4).setCellValue(dvg.getIo());
		row.createCell(5).setCellValue(dvg.getNn());
		row.createCell(6).setCellValue(dvg.getNo());
		row.createCell(7).setCellValue(dvg.getX2());
		row.createCell(8).setCellValue(dvg.getR1());
		row.createCell(9).setCellValue(dvg.getR2());
		
		FileOutputStream fileOut = new FileOutputStream(filename);
	    wb.write(fileOut);
	    fileOut.close();
		
	}
	
}
