package com.mayabansi.webapp.service;

import com.mayabansi.webapp.dao.BookDao;
import com.mayabansi.webapp.dao.CustomerDao;
import com.mayabansi.webapp.domain.Book;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    BookDao bookDao;
    @Mock
    CustomerDao customerDao;

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
                new Book("Grails!", 25.00D, 2005),
                new Book("Java", 20.00D, 2010),
                new Book("Spring", 50.00D, 2006),
                new Book("Hibernate", 25.00D, 2004),
        };

        booksOnSpecialPromotionList = Arrays.asList(booksOnSpecialPromotionArr);
    }

    @Before
    public void beforeEachMethod() {
    }

    @Test
    public void testNullUser() {

        CustomerSpecialsService customerSpecialsService = mock(CustomerSpecialsService.class);
        WeeklySpecialsService weeklySpecialsService = mock(WeeklySpecialsService.class);

        when(bookDao.getTop5BooksOnSale()).thenReturn(top5BooksOnSaleList);
        when(bookDao.getSpecialPromotionsBasedOnUser(null)).thenReturn(booksOnSpecialPromotionList);

        final PromotionsService promotionsService = new PromotionsService();
        promotionsService.setBookDao(bookDao);
        promotionsService.setCustomerSpecialsService(customerSpecialsService);
        promotionsService.setWeeklySpecialsService(weeklySpecialsService);

        final List<Book> promotionList = promotionsService.getPromotions(null);

        verify(bookDao).getTop5BooksOnSale();
        verify(bookDao, times(1)).getTop5BooksOnSale();
        verify(bookDao, never()).getSpecialPromotionsBasedOnUser(null);

        assertNotNull(promotionList);
        assertTrue(promotionList.size() == 5);
    }
}
