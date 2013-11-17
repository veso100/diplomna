package tu.sofia.aez.util;

import java.io.ByteArrayOutputStream;
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
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import tu.sofia.aez.om.Dvigatel;
import tu.sofia.aez.ui.UIConstants;

public class ExcelService {
	private final static String filename = "engines.xlsx";

	public List<Dvigatel> loadDvigateli() throws InvalidFormatException, IOException {
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
					first = false;
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

	public void addDvigatel(Dvigatel dvg) throws InvalidFormatException, IOException {
		InputStream inp = new FileInputStream(filename);
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheetAt(0);
		int newRowNum = sheet.getLastRowNum() + 1;
		Row row = sheet.createRow(newRowNum);
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

	public void exportResultsToExcel(ResultsCalculationService resultService, String outputName) throws Exception {
		DataSetService dsService = new DataSetService(resultService);
		JFreeChart chart = new ChartingService().prepareChartPanel(dsService.createResultsDataset()).getChart();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		ChartUtilities.writeChartAsJPEG(outStream, chart, UIConstants.RESULT_WIDTH - 100, 300);

		byte[] imageInByte = outStream.toByteArray();
		outStream.close();
		Workbook wb = new XSSFWorkbook();
		int pictureIdx = wb.addPicture(imageInByte, Workbook.PICTURE_TYPE_JPEG);
		Sheet sheet = wb.createSheet();
		Row row = sheet.createRow(1);
		row.createCell(0).setCellValue("Pn");
		row.createCell(1).setCellValue("U1n");
		row.createCell(2).setCellValue("U2n");
		row.createCell(3).setCellValue("Io");
		row.createCell(4).setCellValue("Nn");
		row.createCell(5).setCellValue("No");
		row.createCell(6).setCellValue("X2");
		row.createCell(7).setCellValue("r1");
		row.createCell(8).setCellValue("R2");
		row.createCell(9).setCellValue("Ipn");
		row.createCell(10).setCellValue("Rsh/r1");
		row.createCell(11).setCellValue("Rd/r1");
		row.createCell(12).setCellValue("Imiu max");
		row.createCell(13).setCellValue("Imiu min");
		
		row = sheet.createRow(2);
		row.createCell(0).setCellValue(resultService.getDvigatel().getpN());
		row.createCell(1).setCellValue(resultService.getDvigatel().getU1n());
		row.createCell(2).setCellValue(resultService.getDvigatel().getU2n());
		row.createCell(3).setCellValue(resultService.getDvigatel().getIo());
		row.createCell(4).setCellValue(resultService.getDvigatel().getNn());
		row.createCell(5).setCellValue(resultService.getDvigatel().getNo());
		row.createCell(6).setCellValue(resultService.getDvigatel().getX2());
		row.createCell(7).setCellValue(resultService.getDvigatel().getR1());
		row.createCell(8).setCellValue(resultService.getDvigatel().getR2());
		row.createCell(9).setCellValue(resultService.getVeriga().getIpN());
		row.createCell(10).setCellValue(resultService.getVeriga().getRsh());
		row.createCell(11).setCellValue(resultService.getVeriga().getRd());
		row.createCell(12).setCellValue(resultService.getCalculatedIMiuMax());
		row.createCell(13).setCellValue(resultService.getCalculatedIMiuMin());
		
		row = sheet.createRow(3);
		row.createCell(0).setCellValue("ωn");
		row.createCell(1).setCellValue("ωo");
		row.createCell(2).setCellValue("Mn");
		row.createCell(3).setCellValue("Ke");
		row.createCell(4).setCellValue("R1/r1");
		row.createCell(5).setCellValue("Kc");
		row.createCell(6).setCellValue("Kов");
		row.createCell(7).setCellValue("Xμn");
		row.createCell(8).setCellValue("r2'");
		row.createCell(9).setCellValue("r2'*");
		row.createCell(10).setCellValue("x2'");
		row.createCell(11).setCellValue("x2'*");
		row.createCell(12).setCellValue("KΣ");
		row.createCell(13).setCellValue("KΣ²");
		row.createCell(14).setCellValue("R2'*");
		
		row = sheet.createRow(4);
		row.createCell(0).setCellValue(resultService.getWn());
		row.createCell(1).setCellValue(resultService.getWn());
		row.createCell(2).setCellValue(resultService.getMn());
		row.createCell(3).setCellValue(resultService.getKe());
		row.createCell(4).setCellValue(resultService.getVariant().getR1());
		row.createCell(5).setCellValue(resultService.getVariant().getKc());
		row.createCell(6).setCellValue(resultService.getKov());
		row.createCell(7).setCellValue(resultService.getXmiuN());
		row.createCell(8).setCellValue(resultService.getR2Prim());
		row.createCell(9).setCellValue(resultService.getR2PrimZvezda());
		row.createCell(10).setCellValue(resultService.getX2Prim());
		row.createCell(11).setCellValue(resultService.getX2PrimZvezda());
		row.createCell(12).setCellValue(resultService.getKSuma());
		row.createCell(13).setCellValue(resultService.getKSuma2());
		row.createCell(14).setCellValue(resultService.getGlavnoRPrimZvezda());

		Drawing drawing = sheet.createDrawingPatriarch();
		// add a picture shape
		CreationHelper helper = wb.getCreationHelper();

		// add a picture shape
		ClientAnchor anchor = helper.createClientAnchor();
		// set top-left corner of the picture,
		// subsequent call of Picture#resize() will operate relative to it
		anchor.setCol1(1);
		anchor.setRow1(6);
		Picture pict = drawing.createPicture(anchor, pictureIdx);

		// auto-size picture relative to its top-left corner
		pict.resize();

		FileOutputStream fileOut = new FileOutputStream(outputName);
		wb.write(fileOut);
		fileOut.close();
	}
}
