package com.qa.opencart.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductPageInfoTest extends BaseTest{
	
	ArrayList<String> expProdListInCart;
	
	//5053
	@BeforeClass
	public void productInfoPageSetup() {
		accountspage = loginpage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
	}
	
	
	@DataProvider
	public Object[][] getProductImagesTestData() {
		return new Object[][] {
			{"Macbook", "MacBook Pro", 4},
			{"iMac", "iMac", 3},
			{"Apple", "Apple Cinema 30\"", 6},
			{"Samsung", "Samsung SyncMaster 941BW", 1},
		};
	}
	
	
	@Test(dataProvider = "getProductImagesTestData")
	public void productImagesCountTest(String searchKey, String productName, int imagesCount) {
		searchpage = accountspage.performSearch(searchKey);
		prodinfopage = searchpage.selectProduct(productName);
		int actImagesCount = prodinfopage.getProductImagesCount();
		Assert.assertEquals(actImagesCount, imagesCount);
	}
	
	
	
	@Test
	public void productInfoTest() {
		searchpage = accountspage.performSearch("MacBook");
		prodinfopage = searchpage.selectProduct("MacBook Pro");
		Map<String, String> actProductInfoMap = prodinfopage.getProductInfo();
		softassert.assertEquals(actProductInfoMap.get("Brand"), "Apple");
		softassert.assertEquals(actProductInfoMap.get("Product Code"), "Product 18");
		softassert.assertEquals(actProductInfoMap.get("productname"), "MacBook Pro");
		softassert.assertEquals(actProductInfoMap.get("productprice"), "$2,000.00");
		
		softassert.assertAll();
	}
	
	//assert vs verify(soft assertion)
	
	
	@DataProvider
	public Object[][] getCartTestData() {
		return new Object[][] {
			{"Macbook", "MacBook Pro", 1},
			{"iMac", "iMac", 2},
		};
	}
	
	
	@Test(dataProvider = "getCartTestData")
	public void addtToCartTest(String searchKey, String productName, int quantity) {
		searchpage = accountspage.performSearch(searchKey);
		prodinfopage = searchpage.selectProduct(productName);
		prodinfopage.enterQuantity(quantity);
		String actCartMesg = prodinfopage.addProductToCart();
		//Success: You have added MacBook Pro to your shopping cart!
		softassert.assertTrue(actCartMesg.indexOf("Success")>=0);
		softassert.assertTrue(actCartMesg.indexOf(productName)>=0);
		softassert.assertEquals(actCartMesg, "Success: You have added "+productName+" to your shopping cart!");
		
		
		
		//new code: checking cart details as well:
		viewCartPopUpPage = prodinfopage.openCart();
		List<String> cartProdList = viewCartPopUpPage.getProductsValueListInCart();
		
		Object[][] data = getCartTestData();
		expProdListInCart = new ArrayList<String>();
		for(int i=0; i< data.length; i++) {
			expProdListInCart.add(data[i][1].toString());
		}
		
		System.out.println(expProdListInCart);
		softassert.assertTrue(expProdListInCart.containsAll(cartProdList));
		
		
		softassert.assertAll();

	}
	
 
}
