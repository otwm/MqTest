package casestudy.case1;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * 받기 프로그램
 * 
 * @author kdo
 *
 */
public class Recv {

	/**
	 * 큐 명칭
	 */
	private final static String QUEUE_NAME = "hello";

	/**
	 * 커넥션
	 */
	private static Connection connection = null;

	/**
	 * 채널
	 */
	private static Channel channel = null;

	public static void main(String[] argv) throws java.io.IOException,
			java.lang.InterruptedException {

		connection = newConnection("localhost");
		channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(QUEUE_NAME, true, consumer);

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println(" [x] Received '" + message + "'");
		}

	}

	/**
	 * 커텍션 생성
	 * 
	 * @param host
	 *            호스트
	 * @return 커넥션
	 * @throws IOException
	 */
	private static Connection newConnection(String host) throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		Connection connection = factory.newConnection();
		return connection;
	}

}
