package com.mcl.requests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

public class ResponseTest {

    private Response testResponse;

    @Before
    public void setTestResponse() throws IOException {
        URL url = new URL("https://jsonplaceholder.typicode.com/todos/1");
        Request testRequest = new Request(url);
        testResponse = testRequest.call();
    }

    @Test
    public void testGetBodyAsStringDoesNotReturnNull() throws IOException {
        String testString = testResponse.getBodyAsString();
        Assert.assertNotNull(testString);
    }

    @Test
    public void testGetBodyAsStringReturnsCorrectString() throws IOException {
        String expected = "{\n" +
                "  \"userId\": 1,\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"delectus aut autem\",\n" +
                "  \"completed\": false\n" +
                "}";
        String actual = testResponse.getBodyAsString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetBodyAsStringDoesNotReturnEmptyString() throws IOException {
        String testString = testResponse.getBodyAsString();
        Assert.assertNotEquals(0, testString.length());
    }

}
