package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

public class configDateProvider {

	Properties prop;

	public void getConfigProperty() throws IOException
	{
		File file = new File("./config/config.properties");

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			prop  = new Properties();
			prop.load(reader);
			reader.close();

		} catch (FileNotFoundException e) {
			System.out.println("Unable to Read The file config.Properites"+e.getMessage());
		}

	}

	public String getDateFromConfig(String key)
	{
		return prop.getProperty(key);

	}

	public String getStagingURL()
	{
		return prop.getProperty("url");

	}

	public String getBrowser()
	{

		String browserName =  prop.getProperty("browser");

		return browserName;	
	}
}
