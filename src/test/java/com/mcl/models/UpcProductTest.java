package com.mcl.models;

import org.junit.Assert;
import org.junit.Test;


public class UpcProductTest {


    @Test
    public void testUpcValidationPassesForValidInput() {
        String validUpc1 = "883929032273";
        String validUpc2 = "0883929032273";
        UpcProduct upcProduct1 = new UpcProduct(validUpc1);
        UpcProduct upcProduct2 = new UpcProduct(validUpc2);
        Assert.assertEquals(validUpc1, upcProduct1.getUpc());
        Assert.assertEquals(validUpc2, upcProduct2.getUpc());
    }


}

