package com.sample;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author jianfeng
 * This aggregator is used for File/FTP batch consumer. 
 * It will collect all fileName consumed from endpoint and
 * put togather into "header.CamelFileNames" as List<String>.
 */
public class AwareEndAggregationStrategy implements AggregationStrategy {

	private final static Logger LOG = LoggerFactory.getLogger(AwareEndAggregationStrategy.class);
	
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange == null) {
            List<String> fileNames =  new ArrayList<String>();
            fileNames.add(newExchange.getIn().getHeader("CamelFileName",String.class));
            newExchange.getIn().setHeader("CamelFileNames",fileNames);
            LOG.debug(fileNames.toString());
            return newExchange;
        }

        @SuppressWarnings("unchecked")
		List<String> fileNames =  (List<String>) oldExchange.getIn().getHeader("CamelFileNames");
        fileNames.add(newExchange.getIn().getHeader("CamelFileName",String.class));
        oldExchange.getIn().setHeader("CamelFileNames", fileNames);
        LOG.debug(fileNames.toString());

        // check for CamelBatchComplete
        boolean complete = newExchange.getProperty("CamelBatchComplete", boolean.class);
        if (complete) {
            LOG.debug("done");
        }
        return oldExchange;
    }
}
