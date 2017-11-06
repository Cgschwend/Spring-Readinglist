package com.example.readinglistdemo2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

//import static org.junit.Assert;

//import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
//@ContextConfiguration(classes = ReadingListDemo2Application.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = ReadingListDemo2Application.class)
public class SimpleWebTest2 {

    @Value("${local.server.port}")
    private int port;

    @Test(expected=HttpClientErrorException.class)
    public void pageNotFound() {
        try {
            RestTemplate rest = new RestTemplate();
            rest.getForObject("http://localhost:{port}/bogusPage", String.class, port);
            fail("Should result in HTTP 404");
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
            throw e;
        }
    }

    @Test
    public void testRestTemplate() throws Exception {

        TestRestTemplate rest = new TestRestTemplate();
        String s = rest.getForObject("http://localhost:{port}/bogusPage", String.class, port);
        System.out.println(s);

    }

}