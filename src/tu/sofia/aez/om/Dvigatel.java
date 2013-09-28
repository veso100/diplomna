package tu.sofia.aez.om;

public class Dvigatel {
	
	public static final int VALUES_SIZE=15;
	private double pN; // nominalna moshtnost
	private double U1n; // nominalno naprejenie na statora
	private double U2n; // nominalno naprejenie na rotora
	private double Io; // fazen tok na prazen hod
	private double ImN; // nominalen namagnitvasht tok
	private double Nn; // nominalna skorost
	private double No;// skorost na prazen hod
	private double X2; // reaktivno saprotivlenie na razseivane
	private double R1; // aktivno saprotivlenie na statora
	private double R2; // aktivno sparotivlenie na rotora
	private double Wn; // nominalna aglova skorost
	private double Wo; // sinhronna aglova skorost
	private double Ipn; //
	
	private String name;
	private double Rd;
	private double Rsh;

	public Dvigatel() {
		super();
	}

	public Dvigatel(double[] values, String name) {
		pN=values[0];
		U1n=values[1];
		U2n=values[2];
		Io=values[3];
		ImN=values[4];
		Nn=values[5];
		No=values[6];
		X2=values[7];
		R1=values[8];
		R2=values[9];
		Wn=values[10];
		Wo=values[11];
		Ipn=values[12];
		this.name=name;
	}
	public Dvigatel(double[] values) {
		pN=values[0];
		U1n=values[1];
		U2n=values[2];
		Io=values[3];
		ImN=values[4];
		Nn=values[5];
		No=values[6];
		X2=values[7];
		R1=values[8];
		R2=values[9];
		Wn=values[10];
		Wo=values[11];
		Ipn=values[12];
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getIpn() {
		return Ipn;
	}

	public void setIpn(double ipn) {
		Ipn = ipn;
	}

	/**
	 * koeficient na transformacia
	 * 
	 * @return Ke
	 */
	public double getKe() {
		return 0.95 * U1n / U2n;
	}

	/**
	 * nominalen reaktans na namagnitvane
	 * 
	 * @return XmN
	 */
	public double getXmN() {
		return (0.95 * U1n) / (Io * Math.sqrt(3));
	}

	/**
	 * Absoliutna stoinost na Rd v omovoe
	 */
	public double RdOhm() {
		return Rd * R2;
	}

	/**
	 * Absoliutna stoinost na Rsh v omovoe
	 */
	public double RshOhm() {
		return Rsh * R2;
	}

	public double getWn() {
		return Wn;
	}

	public void setWn(double wn) {
		Wn = wn;
	}

	public double getWo() {
		return Wo;
	}

	public void setWo(double wo) {
		Wo = wo;
	}

	public double getpN() {
		return pN;
	}

	public void setpN(double pN) {
		this.pN = pN;
	}

	public double getU1n() {
		return U1n;
	}

	public void setU1n(double u1n) {
		U1n = u1n;
	}

	public double getU2n() {
		return U2n;
	}

	public void setU2n(double u2n) {
		U2n = u2n;
	}

	public double getIo() {
		return Io;
	}

	public void setIo(double io) {
		Io = io;
	}

	public double getImN() {
		return ImN;
	}

	public void setImN(double imN) {
		ImN = imN;
	}

	public double getNn() {
		return Nn;
	}

	public void setNn(double nn) {
		Nn = nn;
	}

	public double getNo() {
		return No;
	}

	public void setNo(double no) {
		No = no;
	}

	public double getX2() {
		return X2;
	}

	public void setX2(double x2) {
		X2 = x2;
	}

	public double getR1() {
		return R1;
	}

	public void setR1(double r1) {
		R1 = r1;
	}

	public double getR2() {
		return R2;
	}

	public void setR2(double r2) {
		R2 = r2;
	}

	public double getRd() {
		return Rd;
	}

	public void setRd(double rd) {
		Rd = rd;
	}

	public double getRsh() {
		return Rsh;
	}

	public void setRsh(double rsh) {
		Rsh = rsh;
	}

}
