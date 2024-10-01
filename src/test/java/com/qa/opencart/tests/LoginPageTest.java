package com.qa.opencart.tests;

import org.testng.Assert;

import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class LoginPageTest extends BaseTest {

	@Test

	public void loginPageTitleTest() throws InterruptedException {

		String actualTitle = loginPage.getLoginPageTitle();

		Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE);
	}

	@Test
	public void loginPageURLTest() throws InterruptedException {

		String actualURL = loginPage.getLoginPageURL();

		Assert.assertTrue(actualURL.contains(AppConstants.LOGIN_PAGE_FRACTION_URL), "URL Not Matched ");

	}

	@Test
	public void forgotpwdLinkTest() {

		Assert.assertTrue(loginPage.isForgotPwdLinkExist());

	}

	@Test(priority=Integer.MAX_VALUE)
	public void loginTest() {

		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("pwd"));
		Assert.assertEquals(accPage.getAccountsPageTitle(), AppConstants.ACCOUNT_PAGE_TITLE);
	}
}
