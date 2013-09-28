package tu.sofia.aez.om;

import java.net.URL;

public enum DSVarianti  {
	VARIANT1("/resources/ds_var1.png"),
	VARIANT2("/resources/ds_var2.png"),
	VARIANT3("/resources/ds_var3.png"),
	VARIANT4("/resources/ds_var4.png"),
	VARIANT5("/resources/ds_var5.png"), ;

	DSVarianti(String resourceLocation) {
		this.resourceLocation = resourceLocation;
	}

	private String resourceLocation;
	
	public URL getResource() {
		URL resource = getClass().getResource(resourceLocation);
		return resource;
	}

}
