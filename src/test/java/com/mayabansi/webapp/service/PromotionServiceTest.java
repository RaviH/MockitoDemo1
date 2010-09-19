package com.mayabansi.webapp.service;

import com.mayabansi.webapp.dao.BookDao;
import com.mayabansi.webapp.domain.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: rhasija
 * Date: Sep 19, 2010
 * Time: 1:07:42 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class PromotionServiceTest {

    @Mock BookDao bookDao;

    @Before
    public void beforeEachMethod() {
    }


    @Test
    public void testSomething() {
        List<Book> allBooks = new ArrayList<Book>();
        when(bookDao.getAll()).thenReturn(allBooks);
    }

}
