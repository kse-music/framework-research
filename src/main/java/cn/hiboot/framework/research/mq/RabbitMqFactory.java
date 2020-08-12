package cn.hiboot.framework.research.mq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMqFactory {
	
	public static Connection getConnection() throws IOException, TimeoutException{
		return getConnection("cloud", "cloud", "192.168.1.10", "/", 5672);
	}

	public static Connection getConnection(String username,String password,String host,String virtualHost,int port) throws IOException, TimeoutException{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername(username);
		factory.setPassword(password);
		factory.setHost(host);
		factory.setVirtualHost(virtualHost);
		factory.setPort(port);
		return factory.newConnection();
	}
	
}
