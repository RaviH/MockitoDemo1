package com.mayabansi.webapp.dao;

import com.mayabansi.webapp.domain.Book;
import org.appfuse.dao.GenericDao;
import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: rhasija
 * Date: Sep 19, 2010
 * Time: 1:38:24 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository("bookDao")
public class BookDaoHibernate extends GenericDaoHibernate<Book, Long> implements BookDao  {

    public BookDaoHibernate() {
        super(Book.class);
    }

    public List<Book> getPromotionsBasedOnUser(Long userId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
