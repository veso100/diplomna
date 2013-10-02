package tu.sofia.aez.om;

import java.net.URL;

public enum DSVarianti  {
	VARIANT1("/resources/ds_var1.png",0.816,2),
	VARIANT2("/resources/ds_var2.png",0.707,1.5),
	VARIANT3("/resources/ds_var3.png",0.943,3),
	VARIANT4("/resources/ds_var4.png",0.472,0.667),
	VARIANT5("/resources/ds_var5.png",0.408,0.5), ;

	DSVarianti(String resourceLocation,double kc,double r1) {
		this.resourceLocation = resourceLocation;
		this.Kc=kc;
		this.R1=r1;
	}

	private String resourceLocation;
	private double Kc;
	private double R1;
	
	public URL getResource() {
		URL resource = getClass().getResource(resourceLocation);
		return resource;
	}

	public double getKc() {
		return Kc;
	}

	public double getR1() {
		return R1;
	}

}
