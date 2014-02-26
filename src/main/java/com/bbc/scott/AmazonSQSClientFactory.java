package com.bbc.scott;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.sqs.AmazonSQSClient;

public class AmazonSQSClientFactory {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AmazonSQSClientFactory.class);

    private String proxyHost;
    private String proxyPort;
    
	/**
	 * Creates a new AmazonSQSClient, getting the AWS key and secret from one of:
	 * 
	 * Environment Variables - AWS_ACCESS_KEY_ID and AWS_SECRET_KEY
	 * Java System Properties - aws.accessKeyId and aws.secretKey
	 * Instance profile credentials delivered through the Amazon EC2 metadata service
	 * 
	 * The proxy is also set if the proxyHost and proxyPort properties are set on this factory
	 * 
	 * @return An AmazonSQSClient instance
	 */
    public AmazonSQSClient createSQSClient(String endpoint) {
    	AmazonSQSClient sqsClient = new AmazonSQSClient();  

		configureProxy(sqsClient);
    	sqsClient.setEndpoint(endpoint);
    	
		return sqsClient;
    }

	private void configureProxy(AmazonSQSClient sqsClient) {
		if (StringUtils.isNotEmpty(proxyHost) && StringUtils.isNoneEmpty(proxyPort)) {
            LOGGER.info("Setting proxy for AmazonSQSClient to be " + proxyHost + ":" + proxyPort);

            ClientConfiguration clientConfiguration = new ClientConfiguration();
            clientConfiguration.setProxyHost(proxyHost);
            clientConfiguration.setProxyPort(Integer.valueOf(proxyPort));
            sqsClient.setConfiguration(clientConfiguration);
        } else {
            LOGGER.info("Not setting proxy for AmazonSQSClient");
        }
	}
    
    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }
    
	
}
