package com.mayabansi.webapp.service.spock

import com.mayabansi.webapp.dao.BookDao
import com.mayabansi.webapp.domain.Book
import com.mayabansi.webapp.service.CustomerSpecialsService
import com.mayabansi.webapp.service.PromotionsService
import com.mayabansi.webapp.service.WeeklySpecialsService
import org.appfuse.model.User
import spock.lang.Specification

/**
 * Created by IntelliJ IDEA.
 * User: rhasija
 * Date: Sep 26, 2010
 * Time: 1:59:30 PM
 * To change this template use File | Settings | File Templates.
 */
class PromotionServiceSpec extends Specification {

    PromotionsService promotionsService
    User user = new User("ravi.hasija@gmail.com");

    BookDao bookDao = Mock();
    CustomerSpecialsService customerSpecialsService = Mock()
    WeeklySpecialsService weeklySpecialsService = Mock()

    List<Book> top5BooksOnSaleList
    List<Book> booksOnSpecialPromotionList;

    def setup() {
        promotionsService = new PromotionsService();
        user.setId(1L);

        top5BooksOnSaleList = [
                new Book("Beautiful life", 25.00D, 2005),
                new Book("Sarasota rocks", 15.00D, 2010),
                new Book("Music gives soul", 125.00D, 2006),
                new Book("Mocking using Mockito", 20.00D, 2004),
                new Book("Why we should?", 99.00D, 2009)
        ]

        booksOnSpecialPromotionList = [
                new Book("Beautiful life", 20.00D, 2005),
                new Book("Sarasota rocks", 10.00D, 2010),
                new Book("Music gives soul", 100.00D, 2006)
        ]
    }

    def "Show How Stubbing Works in Spock"() {
        setup:
        promotionsService.setBookDao(bookDao)
        when:
        def list = promotionsService.getSimplePromotions(null)
        then:
        1 * bookDao.getTop5BooksOnSale() >> top5BooksOnSaleList
        0 * bookDao.getSpecialPromotionsBasedOnUser(_ as User)
        list.size() == 5
    }


    def "Multiple Stubbing #1"() {
        setup:
        Book book1 = new Book().setTitle("Book #1");
        Book book2 = new Book().setTitle("Book #2");

        when:
        def title1 = bookDao.get(1L).getTitle();
        def title2 = bookDao.get(1L).getTitle();
        then:
        2 * bookDao.get(1L) >>> [book1, book2];
        "Book #1".equals(title1)
        "Book #2".equals(title2)
    }

    def "Multiple Stubbing 2 and 3 from PromotionServiceTest are the same"() {
        setup:
        Book book1 = new Book().setTitle("Book #1");
        Book book2 = new Book().setTitle("Book #2");

        when:
        def title1 = bookDao.get(1L).getTitle();
        def title2 = bookDao.get(1L).getTitle();
        def title3 = bookDao.get(1L).getTitle();

        then:
        3 * bookDao.get(1L) >>> [book1, book2, book1];
        "Book #1".equals(title1)
        "Book #2".equals(title2)
        "Book #1".equals(title3)
    }


    def "In Spock First Stubbing Rules"() {
        setup:
        Book book1 = new Book().setTitle("Book #1");
        Book book2 = new Book().setTitle("Book #2");

        when:
        def title1 = bookDao.get(1L).getTitle();
        def title2 = bookDao.get(1L).getTitle();

        then:
        2 * bookDao.get(1L) >> book1
        bookDao.get(1L) >> book2
        "Book #1".equals(title1)
        "Book #1".equals(title2)
    }

    def "Stub Exceptions in mock methods that return some value"() {
        when:
        bookDao.get(1L)
        then:
        1 * bookDao.get(1L) >> {throw new RuntimeException()}
        thrown(RuntimeException)
    }

    def "Stub exceptions in mock methods that are void"() {
        when:
        bookDao.remove(1L)
        then:
        1 * bookDao.remove(1L) >> { throw new RuntimeException() }
        thrown(RuntimeException)
    }

    def "Demo of strict cardinality in spock"() {
        setup:
        promotionsService.setBookDao(bookDao)
        when:
        def list = promotionsService.getSimplePromotions(null)
        then:
        // If we do not specify a cardinality then it becomes optional
        // For ex: bookDao.getTop5BooksOnSale() >> top5BooksOnSaleList
        // assumes call to getTop5BooksOnSale is optional
        1 * bookDao.getTop5BooksOnSale() >> top5BooksOnSaleList
        0 * bookDao.getSpecialPromotionsBasedOnUser(_ as User)
    }

    def "At least n times cardinality in spock"() {
        setup:
        promotionsService.setBookDao(bookDao)
        when:
        def list = promotionsService.getSimplePromotions(null)
        then:
        (1.._) * bookDao.getTop5BooksOnSale() >> top5BooksOnSaleList
    }

    def "At most n times cardinality in spock"() {
        setup:
        promotionsService.setBookDao(bookDao)
        when:
        def list = promotionsService.getSimplePromotions(null)
        list = promotionsService.getSimplePromotions(null)
        then:
        (_..2) * bookDao.getTop5BooksOnSale() >> top5BooksOnSaleList
    }


    def "Get Promotions is passed a null user"() {
        setup:
        promotionsService.bookDao = bookDao
        promotionsService.customerSpecialsService = customerSpecialsService
        promotionsService.weeklySpecialsService = weeklySpecialsService

        when:
        final List<Book> promotionList = promotionsService.getPromotions(null);

        then:
        1 * bookDao.getTop5BooksOnSale() >> top5BooksOnSaleList
        0 * bookDao.getSpecialPromotionsBasedOnUser(null) >> booksOnSpecialPromotionList
        0 * customerSpecialsService.applySpecials()
        1 * customerSpecialsService.getSpecials() >> booksOnSpecialPromotionList
        promotionList.size() == 3

    }

    def "Get Promotions is passed a non null user"() {
        setup:
        promotionsService.bookDao = bookDao
        promotionsService.customerSpecialsService = customerSpecialsService
        promotionsService.weeklySpecialsService = weeklySpecialsService

        when:
        final List<Book> promotionList = promotionsService.getPromotions(user);

        then:
        0 * bookDao.getTop5BooksOnSale() >> top5BooksOnSaleList
        1 * bookDao.getSpecialPromotionsBasedOnUser(user) >> booksOnSpecialPromotionList
        1 * customerSpecialsService.applySpecials(_, _) >> booksOnSpecialPromotionList
        0 * customerSpecialsService.getSpecials();
        promotionList.size() == 6
    }
}