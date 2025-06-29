package utility;

import java.io.FileInputStream;
import java.util.Properties;

public class properties_Reader {
	Properties prop;

	public properties_Reader() {
		prop = new Properties();
		setter();
	}

	private void setter() {
		FileInputStream fis;
		try {
			fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\config.properties");
			prop.load(fis);
		
		} catch (Exception e) {
		}

	}

	public String getProperty(String key) {
		return prop.getProperty(key);
	}
}
