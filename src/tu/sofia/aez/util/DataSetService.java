package tu.sofia.aez.util;

import java.util.Arrays;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

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
		double iMiuMax = resultService.getVeriga().getImiuMax();
		double iMiuMin = resultService.getVeriga().getImiuMin();
		double iMiuZvezdaMin = resultService.getImiuZvezdaMin();
		double kp1 = Math.pow(iMiuMax / iMiuZvezdaMin, (double)1/(double)9);
		double kp2 = (iMiuMax - iMiuMin) / 9;

		double[] iMiu = new double[20];
		iMiu[0] = iMiuMax;
		iMiu[10] = iMiuMax;
		double prevKp1 = iMiuMax;
		double prevKp2 = iMiuMax;
		for (int i = 1; i < 10; i++) {
			prevKp1 = prevKp1 / kp1;
			iMiu[i] = prevKp1;
			prevKp2 = prevKp2 - kp2;
			iMiu[i + 10] = prevKp2;
		}
		
		final XYSeries seriesMiu = new XYSeries("Miu");
		final XYSeries seriesE = new XYSeries("E1");
		for (int i = 0; i < 20; i++) {
			seriesMiu.add(resultService.getS(iMiu[i]), resultService.getMiu(iMiu[i]));
			seriesE.add(resultService.getS(iMiu[i]), resultService.getE1(iMiu[i]));
			
			System.out.println(" N:"+i+"\ts:"+resultService.getS(iMiu[i])+"\tMiu:"+resultService.getMiu(iMiu[i])+"\tE1:"+resultService.getE1(iMiu[i]));
		}

		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(seriesMiu);
		dataset.addSeries(seriesE);
		return dataset;
	}

}
