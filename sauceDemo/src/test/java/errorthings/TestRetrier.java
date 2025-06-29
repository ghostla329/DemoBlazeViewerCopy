package errorthings;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import utility.properties_Reader;

public class TestRetrier implements IRetryAnalyzer {
	properties_Reader pred = new properties_Reader();
	int numberOFRetyry = Integer.valueOf(pred.getProperty("NumberOfRetry"));
	int counter = 0;

	@Override
	public boolean retry(ITestResult result) {
		if (counter < numberOFRetyry) {
			counter++;
			return true;
		} else {
			return false;
		}
	}

}
