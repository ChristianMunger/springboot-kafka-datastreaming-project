package com.kafka.springboot;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

// spring bean
@Service
public class WikimediaChangesProducer {
    // logger instance to log messages
    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesProducer.class);

    // inject kafka template to send messages to kafka broker
    private KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() throws InterruptedException{
        // given topic name
        String topic = "wikimedia_recentchange";

        // to read real time-data from wikimedia
        // we will use event source
        EventHandler eventHander = new WikimediaChangesHandler(kafkaTemplate, topic);
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";

        // pull data from url
        EventSource.Builder builder = new EventSource.Builder(eventHander, URI.create(url));
        EventSource eventSource = builder.build();
        eventSource.start();

        //timeout after 10 minutes
        TimeUnit.MINUTES.sleep(10);
    }





}
