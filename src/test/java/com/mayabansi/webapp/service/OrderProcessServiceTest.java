package com.mayabansi.webapp.service;

import com.mayabansi.webapp.domain.Cart;
import com.mayabansi.webapp.domain.CartItem;
import com.mayabansi.webapp.domain.MainOrder;
import org.appfuse.dao.GenericDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA.
 * User: rhasija
 * Date: Sep 25, 2010
 * Time: 12:18:15 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class OrderProcessServiceTest {

    @Mock
    MainOrder order;
    @Mock
    FirstStep firstStep;
    @Mock
    SecondStep secondStep;
    @Mock
    ThirdStep thirdStep;
    @Mock
    GenericDao<Cart, Long> cartDao;

    OrderProcessService orderProcessService;

    @Before
    public void before() {
        // Stubbing was done in before method
        orderProcessService = new OrderProcessService()
                .setFirstStep(firstStep)
                .setSecondStep(secondStep)
                .setThirdStep(thirdStep)
                .setCartDao(cartDao);
    }

    @Test
    public void show_How_InOrder_Works() {

        when(cartDao.get(1L)).thenReturn(new Cart(13.0D, 2.76D, 4, 3D));
        when(order.getTotalAmount()).thenReturn(new BigDecimal(5));
        when(firstStep.addToCart(Matchers.<Cart>anyObject(), Matchers.<CartItem>anyObject())).thenReturn(true);
        when(secondStep.updateCartTotal(Matchers.<Cart>anyObject(), Matchers.<CartItem>anyObject())).thenReturn(true);
        when(thirdStep.justOrderIt(Matchers.<Cart>anyObject())).thenReturn(true);

        BigDecimal b = orderProcessService.order(order);

        InOrder inOrder = inOrder(firstStep, secondStep, thirdStep);

        inOrder.verify(firstStep).addToCart(Matchers.<Cart>anyObject(), Matchers.<CartItem>anyObject());
        inOrder.verify(secondStep).updateCartTotal(Matchers.<Cart>anyObject(), Matchers.<CartItem>anyObject());
        inOrder.verify(thirdStep).justOrderIt(Matchers.<Cart>anyObject());

        assertEquals(new BigDecimal(25), b);
    }
}