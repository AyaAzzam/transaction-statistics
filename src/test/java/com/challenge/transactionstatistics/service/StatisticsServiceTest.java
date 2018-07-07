package com.challenge.transactionstatistics.service;

import com.challenge.transactionstatistics.model.Statistics;
import com.challenge.transactionstatistics.model.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;



@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceTest {

    @Mock
    private TransactionsService transactionsService;

    private StatisticsService statisticsService;


    @Before
    public void setUp() throws Exception {
        statisticsService = new StatisticsService(transactionsService);
    }

    @Test
    public void getStatistics() {

        // given
        Long currentTimeStamp = Instant.now().toEpochMilli();
        ConcurrentHashMap<Long, Transaction> transactionStore = new ConcurrentHashMap<>();
        transactionStore.put(currentTimeStamp, new Transaction(200.0,  currentTimeStamp));
        transactionStore.put(currentTimeStamp - 10000, new Transaction(100.0,  currentTimeStamp - 10000));

        given(transactionsService.getTransactionStore()).willReturn(transactionStore);

        //when
        Statistics statistics = statisticsService.getStatistics();

        //then
        assertThat(statistics.getSum()).isEqualTo(300.0);
        assertThat(statistics.getAvg()).isEqualTo(150.0);
        assertThat(statistics.getMax()).isEqualTo(200.0);
        assertThat(statistics.getMin()).isEqualTo(100.0);
        assertThat(statistics.getCount()).isEqualTo(2);

    }


}