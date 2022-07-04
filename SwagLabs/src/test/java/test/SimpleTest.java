package test;

import org.testng.annotations.Test;

import elementsList.ElementDetails;
import junit.framework.Assert;

public class SimpleTest extends ElementDetails {
	
	@Test (priority = 0)
	public void LogIn()
	{
		enterText(ElementDetails.userName, "standard_user");
		enterText(password, "secret_sauce");
		clickEvent(loginButton);
	}
	@Test (priority = 1)
	public void selectProductToCart()
	{
		clickEvent(product);
		clickEvent(addToCart);
		setImplicit(elementWaitInSeconds);
	}
	@Test (priority = 2)
	public void checkoutAndFinsh()
	{
		clickEvent(addToCartLink);

		clickEvent(checkout);
		enterText(firstName, readExcelData.getStringData("Sheet1", 1,0));
		enterText(lastName, readExcelData.getStringData("Sheet1", 1, 1));
		enterText(postalCode, String.valueOf(readExcelData.getNumbericData("Sheet1",1, 2)));
	}
	
	@Test (priority = 3)
	public void verifyElement()
	{
		clickEvent(continueButton);
		clickEvent(finish);
		isElementDisplayed(thankYouMessage);
		String message = getText(thankYouMessage);
		
		Assert.assertEquals("THANK YOU FOR YOUR ORDER" , message);
	}
}
