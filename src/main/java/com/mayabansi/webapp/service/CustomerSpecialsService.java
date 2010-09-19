package com.mayabansi.webapp.service;

import com.mayabansi.webapp.dao.CustomerDao;
import com.mayabansi.webapp.domain.Book;
import org.appfuse.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: rhasija
 * Date: Sep 19, 2010
 * Time: 10:30:26 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CustomerSpecialsService {

    CustomerDao customerDao;

    @Autowired
    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }


    public List<Book> applySpecials(List<Book> bookList, User user) {

        if (customerDao.isCustomerSpecial(user.getId())) {
            for (int i = 0; i < bookList.size(); i++) {
                Book book = bookList.get(i);
                Double price = book.getPrice();
                Double priceOff = price * 0.10;
                Double newPrice = price - priceOff;
                book.setPrice(newPrice);
                bookList.set(i, book);
            }
        }

        return bookList;
    }
}
