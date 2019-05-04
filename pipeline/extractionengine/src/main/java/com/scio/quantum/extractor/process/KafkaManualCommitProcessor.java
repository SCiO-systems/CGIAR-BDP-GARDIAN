package com.scio.quantum.extractor.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.component.kafka.KafkaManualCommit;

public class KafkaManualCommitProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Boolean lastOne = exchange.getIn().getHeader(KafkaConstants.LAST_RECORD_BEFORE_COMMIT, Boolean.class);
        if (lastOne) {
            KafkaManualCommit manual =
                    exchange.getIn().getHeader(KafkaConstants.MANUAL_COMMIT, KafkaManualCommit.class);
            if (manual != null) {
                manual.commitSync();
            }
        }
    }
}