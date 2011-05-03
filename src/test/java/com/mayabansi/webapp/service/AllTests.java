package com.mayabansi.webapp.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by IntelliJ IDEA.
 * User: Ravi Hasija
 * Date: May 2, 2011
 * Time: 8:51:39 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( {
        ArgumentCaptorDemoTest.class,
        DemoSpyTest.class,
        OrderProcessServiceTest.class,
        PromotionServiceTest.class
    })
public class AllTests {
}
