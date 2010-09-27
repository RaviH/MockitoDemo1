package com.mayabansi.webapp.service.spock

import com.mayabansi.webapp.domain.Cart
import com.mayabansi.webapp.domain.MainOrder
import com.mayabansi.webapp.service.FirstStep
import com.mayabansi.webapp.service.OrderProcessService
import com.mayabansi.webapp.service.SecondStep
import com.mayabansi.webapp.service.ThirdStep
import org.appfuse.dao.GenericDao
import spock.lang.Specification

/**
 * Created by IntelliJ IDEA.
 * User: rhasija
 * Date: Sep 27, 2010
 * Time: 7:13:43 PM
 * To change this template use File | Settings | File Templates.
 */
class OrderProcessServiceSpec extends Specification {

    MainOrder mainOrder = Mock();
    FirstStep firstStep = Mock();
    SecondStep secondStep = Mock();
    ThirdStep thirdStep = Mock();
    GenericDao<Cart, Long> cartDao = Mock();

    def "Show How InOrder Works"() {
        given:
            cartDao.get(1L) >> { new Cart(13.0D, 2.76D, 4, 3D) };
            mainOrder.getTotalAmount() >> { new BigDecimal(5) };
            1 * firstStep.addToCart(_, _) >> true;
            1 * secondStep.updateCartTotal(_, _) >> true;
            1 * thirdStep.justOrderIt(_) >> true;

            OrderProcessService orderProcessService = new OrderProcessService()
                    .setFirstStep(firstStep)
                    .setSecondStep(secondStep)
                    .setThirdStep(thirdStep)
                    .setCartDao(cartDao);

        when:
            BigDecimal b = orderProcessService.order(mainOrder);

        then:
            new BigDecimal(25) == b;
    }

    def "Mock and give cardinality in setup: ONLY works"() {
        given:
            List<Integer> l = Mock()
            1 * l.get(0) >>  2;

        when:
            int i = l.get(0);

        then:
            i == 2
    }

    def "Mock and give cardinality in then: ONLY works"() {
        given:
            List<Integer> l = Mock()

        when:
            int i = l.get(0);

        then:
            1 * l.get(0) >>  2;
            i == 2
    }


    def "Mocking and checking interactions does not work"() {
        given:
            List<Integer> l = Mock()
            l.get(0) >>  2;

        when:
            int i = l.get(0);

        then:
            1 * l.get(_)
            i == 2
    }


}