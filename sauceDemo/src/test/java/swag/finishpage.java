package swag;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pomClasses.FinishpagePom;

public class finishpage extends BaseTest {

	@BeforeMethod
	public void site_launch() {
		driver.get(pred.getProperty("finalpageUrl"));
	}

	@Test
	public void alltextVisibleTest() {
		FinishpagePom finter = new FinishpagePom(driver);
		asrt.assertEquals(finter.subheader(), pred.getProperty("subheader"));
		asrt.assertEquals(finter.title_header(), pred.getProperty("titilehead"));
		asrt.assertTrue(finter.LastText().equals(pred.getProperty("lasttext")));
		asrt.assertAll();
	}
}
