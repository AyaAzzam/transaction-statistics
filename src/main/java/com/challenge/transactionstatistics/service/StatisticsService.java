package com.challenge.transactionstatistics.service;


import com.challenge.transactionstatistics.model.Statistics;
import com.challenge.transactionstatistics.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
public class StatisticsService {

    private TransactionsService transactionsService;

    @Autowired
    public StatisticsService(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    public Statistics getStatistics() {
        Map<Long, Transaction> transactionStore = transactionsService.getTransactionStore();

        if (transactionStore.size() > 0) {
            Long currentTimeStamp = Instant.now().toEpochMilli();
            List<Transaction> latestTransactions = new ArrayList<>();

            LongStream.rangeClosed(currentTimeStamp - TransactionsService.ONE_MIN_IN_MILLI_SEC, currentTimeStamp)
                    .forEach(i ->
                            {
                                Transaction transaction = transactionStore.get(i);
                                if (transaction != null) {
                                    latestTransactions.add(transaction);
                                }
                            }
                    );

            if (latestTransactions.size() > 0) {
                DoubleSummaryStatistics doubleSummaryStatistics = latestTransactions.stream()
                        .collect(Collectors.summarizingDouble(Transaction::getAmount));

                return new Statistics(
                        doubleSummaryStatistics.getSum(),
                        doubleSummaryStatistics.getAverage(),
                        doubleSummaryStatistics.getMax(),
                        doubleSummaryStatistics.getMin(),
                        doubleSummaryStatistics.getCount()
                );

            } else {
                return new Statistics();
            }
        }
        return new Statistics();
    }
}
