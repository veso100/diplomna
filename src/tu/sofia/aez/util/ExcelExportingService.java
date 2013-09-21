package tu.sofia.aez.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExportingService {

	private final String filename;
	private final Workbook wb;

	public ExcelExportingService(String name) throws InvalidFormatException,
			IOException, FileAlreadyExistsException {
		this.filename = "d:\\" + name + ".xlsx";
		File ff = new File(filename);
		if (ff.exists())
			throw new FileAlreadyExistsException("FileAlreadyExists");
		wb = new XSSFWorkbook();
	}

	public void exportData() {
		createSheetFromData();
		saveToFile();
	}

	private void createSheetFromData() {
		Sheet sheet = wb.createSheet("Result sheet");
		Row row = sheet.createRow((short) 2);
		row.createCell(0).setCellValue(1.1);
		row.createCell(1).setCellValue(new Date());
		row.createCell(2).setCellValue(Calendar.getInstance());
		row.createCell(3).setCellValue("a string");
		row.createCell(4).setCellValue(true);
		row.createCell(5).setCellType(Cell.CELL_TYPE_ERROR);
	}
	
	private void saveToFile() {
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(filename);
			wb.write(fileOut);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileOut != null)
					fileOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
