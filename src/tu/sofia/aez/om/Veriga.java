package tu.sofia.aez.om;

import java.net.URL;


public class Veriga {

	public Veriga() {
		super();
	}

	private double IpN;
	private double Rsh;
	private double Rd;
	private double ImiuMin;
	private double ImiuMax;

	public double getRsh() {
		return Rsh;
	}

	public void setRsh(double rsh) {
		Rsh = rsh;
	}

	public double getRd() {
		return Rd;
	}

	public void setRd(double rd) {
		Rd = rd;
	}

	public double getIpN() {
		return IpN;
	}

	public void setIpN(double ipN) {
		IpN = ipN;
	}

	public double getImiuMin() {
		return ImiuMin;
	}

	public void setImiuMin(double imiuMin) {
		ImiuMin = imiuMin;
	}

	public double getImiuMax() {
		return ImiuMax;
	}

	public void setImiuMax(double imiuMax) {
		ImiuMax = imiuMax;
	}

	public URL getVariantResource() {
		if (Double.compare(Rsh, 0.0001) < 0) {
			return getClass().getResource("/resources/shema1.png");
		}
		if(Double.compare(Rsh, Rd*50000)>0){
			return getClass().getResource("/resources/shema2.png");	
		}
		return getClass().getResource("/resources/shema3.png");	
		
	}
}
