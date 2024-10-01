package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

//SRP: 
public class MyAccountPageTest extends BaseTest {

	@BeforeClass
	public void accSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("pwd"));
	}

	@Test
	public void accPageTitleTest() {
		String actTitle = accPage.getAccountsPageTitle();
		Assert.assertEquals(actTitle, AppConstants.ACCOUNTS_PAGE_TITLE);
	}

	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}

	@Test
	public void accPageHeadersCountTest() {
		Assert.assertEquals(accPage.getTotalAccountsPageHeader(), AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);

	}

	@Test
	public void accPageHeadersTest() {
		List<String> actualHeadersList = accPage.getAccPageHeaders();
		Assert.assertEquals(actualHeadersList, AppConstants.EXPECTED_ACC_PAGE_HEADERS_LIST);
	}
	@DataProvider
	public Object[][] getSearchData() {
		return new Object[][] {
			{"macbook", "MacBook Pro"},
			{"macbook", "MacBook Air"},
			{"imac", "iMac"},
			{"samsung", "Samsung SyncMaster 941BW"},
			{"samsung", "Samsung Galaxy Tab 10.1"}
		};
	}
	
	

	@Test (dataProvider = "getSearchData")
	public void searchTest(String searchKey,String productName) {

		resultPage = accPage.doSearch(searchKey);
		productInfoPage = resultPage.selectProduct(productName);
		String actualProductHeader = productInfoPage.getProductHeader();
		System.out.println("Actual Product Header :" +actualProductHeader);
		Assert.assertEquals(actualProductHeader, productName);
	}

	@Test(dataProvider = "getSearchKey")
	public void searchCountTest(String searchKey,int searchCount) {

		resultPage = accPage.doSearch(searchKey);

		Assert.assertEquals(resultPage.getSearchResultsCount(),searchCount);
	}

	@DataProvider
	public Object[][] getSearchKey() {
		return new Object[][] {
			{"macbook", 3},
			{"imac", 1},
			{"samsung", 2},
			
		};
	}

//	@Test(dataProvider = "getSearchKey")
//	public void searchCountTest(String searchKey, int searchCount) {
//		resultsPage = accPage.doSearch(searchKey);
//		Assert.assertEquals(resultsPage.getSearchResultsCount(), searchCount);
//	}
	
	@DataProvider
	public Object[][] getSearcExcelData() {
		return ExcelUtil.getTestData(AppConstants.SEARCH_SHEET_NAME);
	}
	

	@Test(dataProvider = "getSearcExcelData")
	public void searchTestExcel(String searchKey, String productName) {
		resultPage= accPage.doSearch(searchKey);
		productInfoPage = resultPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductHeader(), productName);
	}

}
