package com.mayabansi.webapp.dao;

import com.mayabansi.webapp.domain.Book;
import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: rhasija
 * Date: Sep 19, 2010
 * Time: 1:38:24 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository("bookDao")
public class BookDaoHibernate extends GenericDaoHibernate<Book, Long> implements BookDao {

    public BookDaoHibernate() {
        super(Book.class);
    }

    public List<Book> getSpecialPromotionsBasedOnUser(Long userId) {
        return new ArrayList<Book>();
    }

    public List<Book> getTop5BooksOnSale() {
        return new ArrayList<Book>();
    }
}
