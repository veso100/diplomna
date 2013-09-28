package tu.sofia.aez.util;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JPanel;

import tu.sofia.aez.ui.ResultsWindow;

public class PrinterService implements Printable {
	private JPanel panelToPrint;

	public PrinterService(JPanel frameToPrint) {
		this.panelToPrint = frameToPrint;
	}

	public void printFrame() {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(this);
		boolean ok = job.printDialog();
		if (ok) {
			try {
				job.print();
			} catch (PrinterException ex) {

			}
		}
	}

	@Override
	public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
		if (page > 0) {
			return NO_SUCH_PAGE;
		}

		Dimension compSize = panelToPrint.getPreferredSize();
		panelToPrint.setSize(compSize);
		Dimension printSize = new Dimension();
		printSize.setSize(pf.getImageableWidth(), pf.getImageableHeight());

		double scaleFactor = getScaleFactorToFit(compSize, printSize);
		// Don't want to scale up, only want to scale down
		if (scaleFactor > 1d) {
			scaleFactor = 1d;
		}

		double scaleWidth = compSize.width * scaleFactor;
		double scaleHeight = compSize.height * scaleFactor;

		Graphics2D g2 = (Graphics2D) g.create();
		double x = ((pf.getImageableWidth() - scaleWidth) / 2d) + pf.getImageableX();
		double y = ((pf.getImageableHeight() - scaleHeight) / 2d) + pf.getImageableY();
		AffineTransform at = new AffineTransform();
		at.translate(x, y);
		at.scale(scaleFactor, scaleFactor);
		g2.transform(at);
		panelToPrint.printAll(g2);
		g2.dispose();

		panelToPrint.revalidate();
		return PAGE_EXISTS;
	}

	public static double getScaleFactorToFit(Dimension original, Dimension toFit) {

		double dScale = 1d;

		if (original != null && toFit != null) {

			double dScaleWidth = getScaleFactor(original.width, toFit.width);
			double dScaleHeight = getScaleFactor(original.height, toFit.height);

			dScale = Math.min(dScaleHeight, dScaleWidth);

		}

		return dScale;

	}

	public static double getScaleFactor(int iMasterSize, int iTargetSize) {

		double dScale = 1;
		if (iMasterSize > iTargetSize) {

			dScale = (double) iTargetSize / (double) iMasterSize;

		} else {

			dScale = (double) iTargetSize / (double) iMasterSize;

		}

		return dScale;

	}

}