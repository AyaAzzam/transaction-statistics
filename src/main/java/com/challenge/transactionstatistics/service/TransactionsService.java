package com.challenge.transactionstatistics.service;

import com.challenge.transactionstatistics.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TransactionsService {

    private static final Logger log = LoggerFactory.getLogger(TransactionsService.class);

    public static Long ONE_MIN_IN_MILLI_SEC = 60000L;
    private Map<Long, Transaction> transactionStore = new ConcurrentHashMap<>();


    public void addTransaction(Transaction transaction) {
        transactionStore.put(transaction.getTimestamp(), transaction);
    }

    public Map<Long, Transaction> getTransactionStore() {
        return transactionStore;
    }

    public boolean isValidTransaction(Transaction transaction) {
        Long currentTimeStamp = Instant.now().toEpochMilli();
        return currentTimeStamp >= transaction.getTimestamp()
                && (currentTimeStamp - transaction.getTimestamp()) <= ONE_MIN_IN_MILLI_SEC;
    }


    /**
     * A Simple Clean-Up Job to remove the invalid transactions.
     * Runs every 10 sec.
     */
    @Scheduled(fixedRate = 100000)
    public void transactionsCleanUpJob() {
        log.info("Invalid transactions clean up job started");
        if(transactionStore.size() > 0) {
            for (Long key : transactionStore.keySet()) {
                Transaction transaction = transactionStore.get(key);
                if(!isValidTransaction(transaction)) {
                    log.info("Remove the transaction at {}", transaction.getTimestamp());
                    transactionStore.remove(key);
                }
            }
        }
        log.info("Invalid transactions clean up job ended");
    }

}
