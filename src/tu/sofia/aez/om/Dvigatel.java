package tu.sofia.aez.om;

public class Dvigatel {
	
	public static final int VALUES_SIZE=9;
	private double pN; // nominalna moshtnost
	private double U1n; // nominalno naprejenie na statora
	private double U2n; // nominalno naprejenie na rotora
	private double Io; // fazen tok na prazen hod
	private double Nn; // nominalna skorost
	private double No;// skorost na prazen hod
	private double X2; // reaktivno saprotivlenie na razseivane
	private double R1; // aktivno saprotivlenie na statora
	private double R2; // aktivno sparotivlenie na rotora
	
	
	private String name="";
	
	public Dvigatel() {
		super();
	}

	public Dvigatel(double[] values, String name) {
		this(values);
		this.name=name;
	}
	public Dvigatel(double[] values) {
		pN=values[0];
		U1n=values[1];
		U2n=values[2];
		Io=values[3];
		Nn=values[4];
		No=values[5];
		X2=values[6];
		R1=values[7];
		R2=values[8];
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}
