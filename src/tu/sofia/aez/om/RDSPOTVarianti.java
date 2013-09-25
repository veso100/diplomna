package tu.sofia.aez.om;

import java.net.URL;

public enum RDSPOTVarianti {
	VARIANT1("/resources/RDSPOT-var1.png"),
	VARIANT2("/resources/RDSPOT-var2.png"),
	VARIANT3("/resources/RDSPOT-var3.png");
	
	RDSPOTVarianti(String resourceLocation) {
		this.resourceLocation = resourceLocation;
	}

	private String resourceLocation;
	
	public URL getResource() {
		URL resource = getClass().getResource(resourceLocation);
		return resource;
	}
}
