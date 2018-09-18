package labo.jim.sch;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class SchematronLoader {
	
	private static final String SCH_RESOURCES_PATH = "schematron-code/";
	
	
	public File getStepAsFile(SchematronStep step){
		String path = path(step);
		URL url = SchematronLoader.class.getClassLoader().getResource(path);
		try {
			return new File(url.toURI());
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public InputStream getStepAsStream(SchematronStep step) {
		String path = path(step);
		InputStream stream = SchematronLoader.class.getClassLoader().getResourceAsStream(path);
		return stream;
	}
	
	private String path(SchematronStep step){
		return SCH_RESOURCES_PATH + step.getStepFile();
	}

}
