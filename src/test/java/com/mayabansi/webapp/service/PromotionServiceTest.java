package com.mayabansi.webapp.service;

import com.mayabansi.webapp.dao.BookDao;
import com.mayabansi.webapp.dao.CustomerDao;
import com.mayabansi.webapp.domain.Book;
import org.appfuse.model.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


/**
 * Created by IntelliJ IDEA.
 * User: rhasija
 * Date: Sep 19, 2010
 * Time: 1:07:42 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class PromotionServiceTest {

    @Mock
    BookDao mockedBookDao;

    @Mock
    CustomerDao customerDao;

    CustomerSpecialsService realCustomerSpecialsService = new CustomerSpecialsService();
    WeeklySpecialsService realWeeklySpecialsService = new WeeklySpecialsService();

    static List<Book> top5BooksOnSaleList;
    static List<Book> booksOnSpecialPromotionList;


    @BeforeClass
    public static void beforeClass() {
        final Book[] bookArr = new Book[]{
                new Book("Beautiful life", 25.00D, 2005),
                new Book("Sarasota rocks", 15.00D, 2010),
                new Book("Music gives soul", 125.00D, 2006),
                new Book("Mocking using Mockito", 20.00D, 2004),
                new Book("Why we should?", 99.00D, 2009)
        };

        top5BooksOnSaleList = Arrays.asList(bookArr);

        final Book[] booksOnSpecialPromotionArr = new Book[]{
                new Book("Beautiful life", 20.00D, 2005),
                new Book("Sarasota rocks", 10.00D, 2010),
                new Book("Music gives soul", 100.00D, 2006),
                new Book("Mocking using Mockito", 15.00D, 2004),
        };

        booksOnSpecialPromotionList = Arrays.asList(booksOnSpecialPromotionArr);
    }

    @Before
    public void beforeEachMethod() {
    }


    /**
     * Test to show if we do not stub methods in a mocked object,
     * we will get the defaults:
     * <p/>
     * From mockito website:
     * <p/>
     * By default, for all methods that return value, mock returns null, an empty collection or
     * appropriate primitive/primitive wrapper value (e.g: 0, false, ... for int/Integer, boolean/Boolean, ...).
     */
    @Test
    public void simple_Non_Stubbed_Mocks_Will_Cause_Default_Values_To_Be_Returned() {
        // Mocking the BookDao
        BookDao mockedBookDao = mock(BookDao.class);

        final PromotionsService promotionsService = new PromotionsService();
        // Injecting mocked DAO
        promotionsService.setBookDao(mockedBookDao);
        promotionsService.setCustomerSpecialsService(realCustomerSpecialsService);
        promotionsService.setWeeklySpecialsService(realWeeklySpecialsService);

        // Business logic under test - Testing passing of null user.
        final List<Book> promotionList = promotionsService.getSimplePromotions(null);

        // Verification of behavior
        verify(mockedBookDao).getTop5BooksOnSale();
        verify(mockedBookDao, times(1)).getTop5BooksOnSale();
        verify(mockedBookDao, never()).getSpecialPromotionsBasedOnUser(null);

        // Regular JUnit Asserts
        assertNotNull(promotionList);
        assertTrue(promotionList.size() == 0); // <= 0 because the mock returned 0 size list.
    }


    @Test
    public void show_How_Stubbing_Works() {
        // Stubbing
        when(mockedBookDao.getTop5BooksOnSale()).thenReturn(top5BooksOnSaleList);
        when(mockedBookDao.getSpecialPromotionsBasedOnUser(null)).thenReturn(booksOnSpecialPromotionList);

        final PromotionsService promotionsService = new PromotionsService();
        // Injecting mocked DAO
        promotionsService.setBookDao(mockedBookDao);
        promotionsService.setCustomerSpecialsService(new CustomerSpecialsService()); // <--Real Service
        promotionsService.setWeeklySpecialsService(new WeeklySpecialsService()); // <-- Real Service

        // Passing in null user
        final List<Book> promotionList = promotionsService.getSimplePromotions(null);

        // Verification of behavior
        verify(mockedBookDao).getTop5BooksOnSale();
        verify(mockedBookDao, times(1)).getTop5BooksOnSale();
        verify(mockedBookDao, never()).getSpecialPromotionsBasedOnUser(null);

        assertNotNull(promotionList);
        assertTrue(promotionList.size() == 5); // Stubbed method being called because the size is 3.
    }

    @Test
    public void test_NullUser_3() {
        //Mock
        CustomerSpecialsService customerSpecialsService = mock(CustomerSpecialsService.class);
        WeeklySpecialsService weeklySpecialsService = mock(WeeklySpecialsService.class);

        // Stubbing
        when(mockedBookDao.getTop5BooksOnSale()).thenReturn(top5BooksOnSaleList);
        when(mockedBookDao.getSpecialPromotionsBasedOnUser(null)).thenReturn(booksOnSpecialPromotionList);
        when(customerSpecialsService.getSpecials()).thenReturn(booksOnSpecialPromotionList);

        final PromotionsService promotionsService = new PromotionsService();
        promotionsService.setBookDao(mockedBookDao); // Look mocked DAO
        promotionsService.setCustomerSpecialsService(customerSpecialsService); // This is mocked too!
        promotionsService.setWeeklySpecialsService(weeklySpecialsService); // And this one too is mocked!

        // Passing in null user
        final List<Book> promotionList = promotionsService.getPromotions(null);

        verify(mockedBookDao).getTop5BooksOnSale();
        verify(mockedBookDao, times(1)).getTop5BooksOnSale();
        verify(mockedBookDao, never()).getSpecialPromotionsBasedOnUser(null);
        verify(customerSpecialsService, never()).applySpecials(anyList(), Matchers.<User>anyObject());
        verify(customerSpecialsService).getSpecials();


        assertNotNull(promotionList);
        assertTrue(promotionList.size() == 4);
    }


    @Test
    public void testNonNullUser() {

        //Mock
        CustomerSpecialsService customerSpecialsService = mock(CustomerSpecialsService.class);
        WeeklySpecialsService weeklySpecialsService = new WeeklySpecialsService();

        User user = new User("ravi.hasija@gmail.com");
        user.setId(1L);

        // Stubbing
        when(mockedBookDao.getTop5BooksOnSale()).thenReturn(top5BooksOnSaleList);
        when(mockedBookDao.getSpecialPromotionsBasedOnUser(user)).thenReturn(booksOnSpecialPromotionList);
        //Look below is
        //when(customerSpecialsService.applySpecials(anyList(), Matchers.<User>anyObject())).thenReturn(booksOnSpecialPromotionList);


        final PromotionsService promotionsService = new PromotionsService();
        promotionsService.setBookDao(mockedBookDao); // Look mocked DAO
        promotionsService.setCustomerSpecialsService(customerSpecialsService); // This is mocked too!
        promotionsService.setWeeklySpecialsService(weeklySpecialsService); // And this one too is mocked!

        // Passing in null user
        final List<Book> promotionList = promotionsService.getPromotions(user);

        verify(mockedBookDao, never()).getTop5BooksOnSale();
        verify(mockedBookDao, times(1)).getSpecialPromotionsBasedOnUser(user);

        assertNotNull(promotionList);
        System.out.println("Size" + promotionList.size());
        assertTrue(promotionList.size() == 4);
    }

}
