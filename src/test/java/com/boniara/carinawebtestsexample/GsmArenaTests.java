package com.boniara.carinawebtestsexample;

import com.boniara.carinawebtestsexample.gui.gsmarena.ComparePage;
import com.boniara.carinawebtestsexample.gui.gsmarena.HomePage;
import com.boniara.carinawebtestsexample.gui.gsmarena.ProductPage;
import com.boniara.carinawebtestsexample.gui.gsmarena.ProductsPage;
import com.qaprosoft.carina.core.foundation.UITest;
import com.qaprosoft.carina.core.foundation.dataprovider.annotations.XlsDataSourceParameters;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class GsmArenaTests extends UITest {

    @Test(dataProvider = "DataProvider", description = "Test compares phones specifications")
    @XlsDataSourceParameters(path = "demo.xlsx", sheet = "gui", dsUid = "TUID", dsArgs = "producer, brand, display, " +
            "camera, expansion, battery")
    public void checkProductSpecifications(String producer, String brand, String display, String camera,
                                           String expansion, String battery) {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(16), "Home page is not opened");
        ProductsPage productsPage = homePage.clickBrandByName(producer);
        ProductPage productPage = productsPage.clickProductByName(brand);
        productPage.getReviewHeader().checkElementText("display", display);
        productPage.getReviewHeader().checkElementText("camera", camera);
        productPage.getReviewHeader().checkElementText("expansion", expansion);
        productPage.getReviewHeader().checkElementText("battery", battery);
    }

    @Test(description = "Test checks compare logic and output with product latest release date")
    @Parameters({"tableName", "rowName"})
    public void checkCompareLogic(String tableName, String rowName) {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(16), "Home page is not opened");
        ComparePage comparePage = homePage.getHomeMenu().clickMenuElement("compare");
        comparePage.getCompareCondidatesBlock().getCompareColumn(1).sendKeysToInputFieldAndClick("Samsung", 1);
        comparePage.getCompareCondidatesBlock().getCompareColumn(2).sendKeysToInputFieldAndClick("IPhone", 1);
        comparePage.getCompareCondidatesBlock().getCompareColumn(3).sendKeysToInputFieldAndClick("HTC", 1);

        comparePage.getCondidateDescriptionBlock().getCompareDescriptionTable(tableName).getCompareRow(rowName).printRows();
    }
}