package tu.sofia.aez.util;

import tu.sofia.aez.om.DSVarianti;
import tu.sofia.aez.om.Dvigatel;
import tu.sofia.aez.om.NamagnitvashtaKriva;
import tu.sofia.aez.om.RejimEnum;
import tu.sofia.aez.om.Veriga;

public class ResultsCalculationService {
	private Dvigatel dvigatel;
	private DSVarianti variant;
	private Veriga veriga;
	private RejimEnum rejim;
	private NamagnitvashtaKriva kriva = new NamagnitvashtaKriva();

	public ResultsCalculationService(Dvigatel dvigatel, Veriga veriga, DSVarianti variant, RejimEnum rejim) {
		super();
		this.dvigatel = dvigatel;
		this.veriga = veriga;
		this.variant = variant;
		this.rejim = rejim;
	}

	public RejimEnum getRejim() {
		return rejim;
	}

	public void setRejim(RejimEnum rejim) {
		this.rejim = rejim;
	}

	public Dvigatel getDvigatel() {
		return dvigatel;
	}

	public void setDvigatel(Dvigatel dvigatel) {
		this.dvigatel = dvigatel;
	}

	public Veriga getVeriga() {
		return veriga;
	}

	public void setVeriga(Veriga veriga) {
		this.veriga = veriga;
	}

	public DSVarianti getVariant() {
		return variant;
	}

	public void setVariant(DSVarianti variant) {
		this.variant = variant;
	}

	public double getI1() {
		return variant.getKc() * veriga.getIpN();
	}

	public double getWn() { // nominalna skorost
		return (dvigatel.getNn() * 3.14) / (double) 30;
	}

	public double getWo() { // sinhronna skorost
		return (dvigatel.getNo() * 3.14) / (double) 30;
	}

	public double getMn() { // nominalen moment
		return (dvigatel.getpN() / getWn());
	}

	public double getKe() { // koeficient na transformacia
		return 0.95 * dvigatel.getU1n() / dvigatel.getU2n();
	}

	public double getKe2() {
		return getKe() * getKe();
	}

	public double getXmiuN() {
		double num = 0.95 * dvigatel.getU1n();
		double den = 1.73 * dvigatel.getIo();
		return num / den;
	}

	public double getR2Prim() {
		return getKe2() * dvigatel.getR2();
	}

	public double getR2PrimZvezda() {
		return getR2Prim() / getXmiuN();
	}

	public double getX2Prim() {
		return getKe2() * dvigatel.getX2();
	}

	public double getX2PrimZvezda() {
		return getX2Prim() / getXmiuN();
	}

	public double getKov() {
		return (1.224 * veriga.getRsh()) / ((0.35 * variant.getR1() * dvigatel.getR1()) + veriga.getRsh());
	}

	public double getKSuma() {
		return getKe() * variant.getKc() * getKov();
	}

	public double getM1(double Imiu) {
		return 1 + (2 * getX2PrimZvezda() / kriva.getXmiuPoI(Imiu));
	}

	public double getM2(double Imiu) {
		return 1 - ((Math.pow(variant.getKc(), 2) * Math.pow(veriga.getIpN(), 2)) / (Math.pow(Imiu, 2))); // CHECK!
	}

	public double getM3(double Imiu) {
		return (getKSuma() * variant.getKc() * veriga.getIpN()) / Imiu;
	}

	public double getM4(double Imiu) {
		return getM4Const() * (Math.pow((getKSuma2() - (getM1(Imiu) * getM2(Imiu))), 0.5));
	}

	public double getF1(double Imiu) {
		return (getM4(Imiu) - getM3(Imiu)) / (getKSuma2() - getM1(Imiu));
	}

	public double getI2PrimZvezda(double Imiu) {
		return getF1(Imiu) * Imiu;
	}

	public double getS(double Imiu) {
		return getGlavnoRPrimZvezda()
				/ (Math.pow(((Math.pow(kriva.getXmiuPoI(Imiu), 2) / Math.pow(getF1(Imiu), 2)) - Math.pow(
						getX2PrimZvezda(), 2)), 0.5));
	}

	public double getMiu(double Imiu) {
		return ((3 * getXmiuN() * Math.pow(dvigatel.getIo(), 2) * getGlavnoRPrimZvezda()) / getWo())
				* (Math.pow(getI2PrimZvezda(Imiu), 2) / getS(Imiu)) / getMn();
	}

	public double getE1(double Imiu) {
		return kriva.getE1(Imiu);
	}

	public double getImiuZvezdaMin() {
		return 1.001 * variant.getKc() * veriga.getIpN();
	}

	public double getKSuma2() {
		return getKSuma() * getKSuma();
	}

	private double getRsh() {
		return veriga.getRsh() * dvigatel.getR2();
	}

	private double getRd() {
		return veriga.getRd() * dvigatel.getR2();
	}

	public double getGlavnoRPrimZvezda() {
		return (getKe2() / getXmiuN())
				* (dvigatel.getR2() + getRd() + ((getDvigatel().getR1() * getRsh()) / (getDvigatel().getR1() + getRsh())));
	}

	private double M4Const = 1;

	public double getM4Const() {
		return M4Const;
	}

	public void setM4Const(double m4Const) {
		M4Const = m4Const;
	}

	public double getCalculatedIMiuMax() {
		if (!RejimEnum.DS.equals(rejim))
			return veriga.getImiuMax();
		return 0.999 * variant.getKc() * veriga.getIpN();
	}

	public double getCalculatedIMiuMin() {
		if (RejimEnum.RDSPOT.equals(rejim))
			return veriga.getImiuMin();
		if (RejimEnum.RDSS.equals(rejim)) {
			return variant.getKc() * veriga.getIpN() + 0.1;
		}
		
		
		return variant.getKc()
				* veriga.getIpN()
				/ Math.pow((1 + ((1.3 * 1.3 * (1 + 2 * getX2PrimZvezda() / 1.3)) / (getGlavnoRPrimZvezda()
						* getGlavnoRPrimZvezda() / 4 + getX2PrimZvezda() * getX2PrimZvezda()))), 0.5);

	}

}
