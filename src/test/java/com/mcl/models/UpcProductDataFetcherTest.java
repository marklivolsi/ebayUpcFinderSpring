package com.mcl.models;

import com.mcl.utilities.UpcProductDataFetcher;
import org.junit.Assert;
import org.junit.Test;


public class UpcProductDataFetcherTest {


    @Test
    public void testUpcValidationPassesForValidInput() {
        String validUpc1 = "883929032273";
        String validUpc2 = "0883929032273";
        UpcProductDataFetcher upcProductDataFetcher1 = new UpcProductDataFetcher(validUpc1);
        UpcProductDataFetcher upcProductDataFetcher2 = new UpcProductDataFetcher(validUpc2);
        Assert.assertEquals(validUpc1, upcProductDataFetcher1.getUpc());
        Assert.assertEquals(validUpc2, upcProductDataFetcher2.getUpc());
    }


}

