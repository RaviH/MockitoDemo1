package com.mayabansi.webapp.service;

import com.mayabansi.webapp.dao.BookDao;
import com.mayabansi.webapp.dao.CustomerDao;
import com.mayabansi.webapp.domain.Book;
import org.appfuse.dao.GenericDao;
import org.appfuse.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: rhasija
 * Date: Sep 19, 2010
 * Time: 1:22:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class PromotionsService {

    BookDao bookDao;
    CustomerDao customerDao;

    @Autowired
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Autowired
    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;        
    }

    public List<Book> getPromotions(final User user) {
        List<Book> bookList = null;

        if (user == null) {
            bookList = bookDao.getAll();
        } else {
            bookList = bookDao.getPromotionsBasedOnUser(user.getId());
        }

        if (customerDao.isCustomerSpecial(user.getId())) {
            for (int i=0; i<bookList.size(); i++) {
                Book book = bookList.get(i);
                Double price = book.getPrice();
                Double priceOff = price*0.10;
                Double newPrice = price - priceOff;
                book.setPrice(newPrice);
                bookList.set(i, book);
            }
        }

        return bookList;
    }

}
