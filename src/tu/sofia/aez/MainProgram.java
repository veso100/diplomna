package tu.sofia.aez;

import tu.sofia.aez.util.ExcelExportingService;

public class MainProgram {

	public static void main(String[] args) {
		//  javax.swing.SwingUtilities.invokeLater(new MainWindow().getRunnable());
			try {
				new ExcelExportingService("testExcel123").exportData();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
