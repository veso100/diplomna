package tu.sofia.aez.om;

public class NamagnitvashtaKriva {
	private double[] ImiuImin = { 0, 0.2, 0.4, 0.6, 0.8, 1, 1.2, 1.4, 1.6, 1.8, 2 };
	private double[] ImiuIMax = { 0.2, 0.4, 0.6, 0.8, 1, 1.2, 1.4, 1.6, 1.8, 2 };
	private double[] E1Imin = { 0, 0.26, 0.52, 0.736, 0.895, 1, 1.07, 1.22, 1.163, 1.196, 1.223 };
	private double[] E1Imax = { 0.26, 0.52, 0.736, 0.895, 1, 1.07, 1.22, 1.163, 1.196, 1.223 };

	public double XmiuId(int index) {
		return (E1Imax[index] - E1Imin[index]) / (ImiuIMax[index] - ImiuImin[index]);
	}

	public double getImiuImin(int index) {
		return ImiuImin[index];
	}

	public double getImiuMax(int index) {
		return ImiuIMax[index];
	}

	public double getE1Imin(int index) {
		return E1Imin[index];
	}

	public double getE1Imax(int index) {
		return E1Imax[index];
	}

	public double getE1(double Imiu) {

		if (Double.compare(Imiu, 2) >= 0) { // Max iMax
			return 1.0356 + 0.2673 * Math.log10(Imiu);
		}

		for (int i = 0; i <= 9; i++) {
			if (Double.compare(ImiuImin[i], Imiu) < 0 && Double.compare(ImiuIMax[i], Imiu) >= 0) {
				return E1Imin[i] + XmiuId(i) * (Imiu - ImiuImin[i]);
			}
		}

		return 0;
	}

	public double getXmiuPoI(double Imiu) {
		return Imiu / getE1(Imiu);

	}

}
