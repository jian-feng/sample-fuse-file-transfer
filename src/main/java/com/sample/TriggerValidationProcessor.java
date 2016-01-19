package com.sample;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TriggerValidationProcessor implements Processor {

  private static final Logger LOG = LoggerFactory.getLogger(TriggerValidationProcessor.class);

  @Override
  public void process(Exchange exchange) throws Exception {

    // check body is in format "yyyyMMdd"
    String body = exchange.getIn().getBody(String.class);
    String businessDate = body.substring(0, 8);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    sdf.parse(businessDate);

    // check body equals to system date
    String systemDate = sdf.format(new Date());
    if (!systemDate.equals(businessDate)) {
      throw new Exception(
          "BusinessDate(" + businessDate + ") is not equals to SystemDate(" + systemDate + ")");
    }

    // everything looks well, so going on
    LOG.info("Trigger File is valid as businessDate: " + businessDate);

  }

}
