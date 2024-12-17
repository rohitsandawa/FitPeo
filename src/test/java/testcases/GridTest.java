package testcases;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class GridTest {
	public static WebDriver driver;
	public static Properties pro;

	@Parameters({ "os", "browser" })
	@Test
	public void gridtest(String Ops, String br) throws IOException {
		FileReader file = new FileReader(
				System.getProperty("user.dir") + ("\\src\\test\\resources\\Properties\\config.prop"));
		pro = new Properties();
		pro.load(file);

		if (pro.getProperty("execution_env").equalsIgnoreCase("remote")) {
			DesiredCapabilities dcap = new DesiredCapabilities();

			if (Ops.equalsIgnoreCase("windows")) {
				dcap.setPlatform(Platform.WIN11);
			} else if (Ops.equalsIgnoreCase("Linux")) {

				dcap.setPlatform(Platform.LINUX);

			}

			else if (Ops.equalsIgnoreCase("mac")) {

				dcap.setPlatform(Platform.MAC);

			}

			else {
				System.out.println("OS not found");
			}

			switch (br.toLowerCase()) {
			case "chrome":
				dcap.setBrowserName("chrome");
				break;
			case "edge":
				dcap.setBrowserName("MicrosoftEdge");
				break;
			case "firefox":
				dcap.setBrowserName("firefox");
				break;
			default:
				System.out.println("No browser found");
				return;

			}

			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dcap);

		}

		driver.get("https://www.fitpeo.com/");
		driver.manage().window().maximize();
		driver.quit();

	}

}
