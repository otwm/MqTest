package casestudy.case1;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 발송 클래스
 * 
 * @author kdo
 *
 */
public class Send {

	/**
	 * 큐 네임
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

	/**
	 * <pre>
	 * 메인 프로 세스
	 * mq 심플 케이스
	 * </pre>
	 * 
	 * @param argv
	 * @throws java.io.IOException
	 */
	public static void main(String[] argv) throws java.io.IOException {
		connection = newConnection("localhost");
		channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		String message = "Hello World!";
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");

		closeProcess();
	}

	private static void closeProcess() throws IOException {
		channel.close();
		connection.close();
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
