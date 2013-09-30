package tu.sofia.aez.util;

import tu.sofia.aez.om.DSVarianti;
import tu.sofia.aez.om.Dvigatel;
import tu.sofia.aez.om.Veriga;

public class CalculationService {
	
	public CalculationService(Dvigatel dvigatel, Veriga veriga, DSVarianti variant) {
		super();
		this.dvigatel = dvigatel;
		this.veriga = veriga;
		this.variant = variant;
	}
	private Dvigatel dvigatel;
	private Veriga veriga;
	private DSVarianti variant;
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

}
