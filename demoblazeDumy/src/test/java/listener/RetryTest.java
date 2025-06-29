package listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryTest implements IRetryAnalyzer {

	@Override
	public boolean retry(ITestResult result) {
//retries 2 times
		int count = 0;
		while (count < 2) {
			count++;
			return true;
		}
		return false;
	}

}
