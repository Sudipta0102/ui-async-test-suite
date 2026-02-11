package org.myApp.ui.smoke;

import org.myApp.ui.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SmokeTest extends BaseTest {

    @Test(groups = "smoke")
    public void applicationStarts(){

        // Base Test opens url

        String title = driver.getTitle();

        Assert.assertNotNull(title, "Page title should not be null");
    }
}
