package cn.hiboot.framework.research.mq;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * 交换机：
 *  Direct:默认的交换机模式，也是最简单的模式，根据key全文匹配去寻找队列。
 *  Topic:转发消息主要是根据通配符。 在这种交换机下，队列和交换机的绑定会定义一种路由模式，那么，通配符就要在这种路由模式和路由键之间匹配后交换机才能转发消息。
 *  Headers:自定义匹配规则的类型.在队列与交换器绑定时, 会设定一组键值对规则, 消息中也包括一组键值对( headers 属性), 当这些键值对有一对, 或全部匹配时, 消息被投送到对应队列.
 *  Fanout: 消息广播的模式，不管路由键或者是路由模式，会把消息发给绑定给它的全部队列，如果配置了 routing_key 会被忽略。
 */
public class RabbitMqDemo {

    private final static String EXCHANGE_NAME = "exchange_hello";
    private final static String QUEUE_NAME = "queue_hello";
    private final static String ROUTING_KEY = "rout_hello";

    @Test
    public void publish() throws Exception {

        Connection connection = RabbitMqFactory.getConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true);

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        //已经在transaction事务模式的channel是不能再设置成confirm模式的，即这两种模式是不能共存的。
        channel.confirmSelect();

        channel.addConfirmListener(new ConfirmListener() {

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("nack: deliveryTag = " + deliveryTag + " multiple: " + multiple);
            }

            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("ack: deliveryTag = " + deliveryTag + " multiple: " + multiple);
            }

        });

        String message = "Hello World!";

        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

        System.out.println("Publish " + message);
    }

    @Test
    public void consumer() throws Exception {

        Connection connection = RabbitMqFactory.getConnection();

        Channel channel = connection.createChannel();

        channel.basicConsume(QUEUE_NAME, false, new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received " + message);
                channel.basicAck(envelope.getDeliveryTag(), false);
            }

        });

    }

}
