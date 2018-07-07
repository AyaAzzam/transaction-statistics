package com.challenge.transactionstatistics.controller;

import com.challenge.transactionstatistics.model.Statistics;
import com.challenge.transactionstatistics.model.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionsControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void saveTransactionSuccess() {

        //given
        Transaction testTransaction = new Transaction(12.5,  Instant.now().toEpochMilli());

        // when
        ResponseEntity<Statistics> response =
                restTemplate.postForEntity("/transactions",
                        testTransaction, Statistics.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }


    @Test
    public void saveTransactionFailure() {

        //given
        Transaction testTransaction = new Transaction(12.5,  1478192204000L);

        // when
        ResponseEntity<Statistics> response =
                restTemplate.postForEntity("/transactions",
                        testTransaction, Statistics.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }
}