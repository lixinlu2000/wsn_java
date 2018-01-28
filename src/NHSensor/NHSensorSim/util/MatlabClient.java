package NHSensor.NHSensorSim.util;

import java.io.*;
import java.net.*;

public class MatlabClient {
	public static final String BYE = "bye";
	Socket sock;
	PrintWriter out;
	BufferedReader in;

	public MatlabClient() {
		this("localhost");
	}

	public MatlabClient(String serverIP) {
		sock = null;
		out = null;
		in = null;

		try {
			sock = new Socket(serverIP, 4444);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(
					new InputStreamReader(sock.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("no localhost");
			System.exit(1);
		} catch (IOException e) {
			System.err.println(e);
			System.exit(1);
		}
	}

	public void close() throws IOException {
		out.close();
		in.close();
		sock.close();
	}

	public String eval(String command) throws IOException {
		String sendRequest, fromServer;

		sendRequest = command;
		out.println(sendRequest);
		StringBuffer result = new StringBuffer();

		while ((fromServer = in.readLine()) != null) {
			if (fromServer.startsWith("bye")) {
				close();
				break;
			} else {
				// process answer from server
				if (sendRequest.startsWith("testDM"))
					result.append(fromServer);

				System.out.println("Result Returned");
				break;
			}
		}
		return result.toString();
	}

}
