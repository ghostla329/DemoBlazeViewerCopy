package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
	private static Properties prop = new Properties();

	static {
		try (FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + File.separator + "config.properties")) {

			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/** get the value attached with the given key */
	public static String getProperty(String key) {
		return prop.getProperty(key);
	}

//----------------------------------credential file--------------------------------------------------------
	private static Properties cred = new Properties();
	private final static String credPath = System.getProperty("user.dir") + File.separator
			+ "CredentialsDummy.properties";
	static {// for credential properties file

		try (FileInputStream fis = new FileInputStream(credPath);) {
			cred.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param keyname- the parameter whose value you want to update
	 * @param value    the value you want ot set it to
	 */
	public static void updateValue(String keyname, String value) {
		File ou = new File(credPath);
		// if file directory does not exist then throw exception
		if (!ou.exists()) {
			throw new RuntimeException("the filehas been removed.(couldnot find credentialDummy.properties file)");
		}
//write into the original file
		try (FileOutputStream oufi = new FileOutputStream(credPath);) {
			// if the property key exist and not different only then update
			if (cred.getProperty(keyname) != null) {
				cred.setProperty(keyname, value);
				cred.store(oufi, "Things upadted dynamically");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("value got updated");
	}

	/** to fetch value from credentail propertiesFile */
	public static String getCredProperTy(String key) {
		return cred.getProperty(key);
	}
}
