package com.mayabansi.webapp.service.interfaces;

import com.mayabansi.webapp.domain.Book;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: rhasija
 * Date: Sep 19, 2010
 * Time: 10:40:22 AM
 * To change this template use File | Settings | File Templates.
 */
public interface WeeklySpecialSvc {

    public List<Book> applyWeeklySpecials(List<Book> bookList);

}
