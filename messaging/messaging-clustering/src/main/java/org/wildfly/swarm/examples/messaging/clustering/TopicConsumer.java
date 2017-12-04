package org.wildfly.swarm.examples.messaging.clustering;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author Ken Finnigan
 */
@MessageDriven(name = "MyTopicConsumer", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = MyResource.TOPIC),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})
public class TopicConsumer implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("received: " + ((TextMessage) message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
