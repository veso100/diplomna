package tu.sofia.aez.util;

import java.io.File;
import java.io.IOException;
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

public class ExcelImportingService {
	private final Workbook wb;

	public ExcelImportingService(String filename) throws InvalidFormatException, IOException {
		wb = WorkbookFactory.create(new File(filename));
	}

	public List<Dvigatel> loadDvigateli() {
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
						e.printStackTrace();
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
}
