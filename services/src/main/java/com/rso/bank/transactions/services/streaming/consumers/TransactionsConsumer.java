package com.rso.bank.transactions.services.streaming.consumers;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.logs.cdi.Log;
import com.kumuluz.ee.streaming.common.annotations.StreamListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONObject;

import com.rso.bank.transactions.services.TransactionsBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@Log
public class TransactionsConsumer {

    private Logger log = LogManager.getLogger(TransactionsConsumer.class.getName());

    @Inject
    private TransactionsBean transactionsBean;

    @StreamListener(topics = {"xtmm0ew0-default"})
    public void onMessage(ConsumerRecord<String, String> record) {

        log.info(String.format("Consumed message: offset = %d, key = %s, value = %s%n", record.offset(), record.key()
                , record.value()));

        JSONObject message = new JSONObject(record.value());

        String id = message.getString("id");
        String status = message.getString("status");


        log.info("Status for transaction " + id + " set to " + status);

        transactionsBean.setTransactionStatus(id, status);

    }

}
