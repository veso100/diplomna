package tu.sofia.aez.util;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import tu.sofia.aez.om.RejimEnum;

public class DataSetService {

	private ResultsCalculationService resultService;

	public DataSetService(ResultsCalculationService resultService) {
		this.resultService = resultService;
	}

	public DataSetService() {

	}

	/**
	 * Creates a sample dataset.
	 * 
	 * @return a sample dataset.
	 */
	public XYDataset createSampleDataset() {

		final XYSeries series1 = new XYSeries("First");
		series1.add(1.0, 1.0);
		series1.add(2.0, 4.0);
		series1.add(3.0, 3.0);
		series1.add(4.0, 5.0);
		series1.add(5.0, 5.0);
		series1.add(6.0, 7.0);
		series1.add(7.0, 7.0);
		series1.add(8.0, 8.0);

		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);

		return dataset;

	}

	public XYDataset createResultsDataset() {
		double iMiuMax = resultService.getCalculatedIMiuMax();
		double iMiuMin = resultService.getCalculatedIMiuMin();
		double[] iMiu = new double[0];
		final XYSeries seriesMiu = new XYSeries("μ");
		final XYSeries seriesE = new XYSeries("E1");
		
		if (RejimEnum.RDSPOT.equals(resultService.getRejim())) {
			double kp = (iMiuMax - iMiuMin) / (double) 49;
			iMiu = new double[50];
			double prevKp = iMiuMax;
			iMiu[0] = iMiuMax;
			for (int i = 1; i < 50; i++) {
				prevKp = prevKp - kp;
				iMiu[i] = prevKp;
			}
			resultService.setM4Const(1);
			for (int i = 0; i < iMiu.length; i++) {
				seriesMiu.add(resultService.getS(iMiu[i]), resultService.getMiu(iMiu[i]));
				seriesE.add(resultService.getS(iMiu[i]), resultService.getE1(iMiu[i]));
			}
			
			resultService.setM4Const(-1);
			for (int i = 0; i < iMiu.length; i++) {
				seriesMiu.add(resultService.getS(iMiu[i]), resultService.getMiu(iMiu[i]));
				seriesE.add(resultService.getS(iMiu[i]), resultService.getE1(iMiu[i]));
			}

		} else {
//			if(RejimEnum.DS.equals(resultService.getRejim()))
//					resultService.setM4Const(-1);
			double kp = (iMiuMax - iMiuMin) / (double) 99;
			iMiu = new double[100];
			double prevKp = iMiuMax;
			iMiu[0] = iMiuMax;
			for (int i = 1; i < 100; i++) {
				prevKp = prevKp - kp;
				iMiu[i] = prevKp;
			}
			for (int i = 0; i < iMiu.length; i++) {
				seriesMiu.add(resultService.getS(iMiu[i]), resultService.getMiu(iMiu[i]));
				seriesE.add(resultService.getS(iMiu[i]), resultService.getE1(iMiu[i]));
			}
		}

	
		

		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(seriesMiu);
		dataset.addSeries(seriesE);
		return dataset;
	}

}
