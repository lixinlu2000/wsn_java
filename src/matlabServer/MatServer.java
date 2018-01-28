package matlabServer;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class MatServer {
	MatlabControl mc = new MatlabControl();

	public void body() throws IOException {
		class Caller extends Thread {
			public void run() {
				try {
					body2();
				} catch (IOException e) {
					System.err.println(e);
				}
			}

			public void body2() throws IOException {
				ServerSocket serverSocket = null;
				boolean listening = true;
				try {
					serverSocket = new ServerSocket(4444);
				} catch (IOException e) {
					System.err.println(e);
					System.exit(1);
				}

				while (listening) {
					new workerThread(serverSocket.accept()).run();
				}
				serverSocket.close();
			} // end_run
		} // end_caller
		Caller c = new Caller();
		c.start();
	}

	public class workerThread {
		private Socket socket = null;

		public workerThread(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			try {
				PrintWriter out = new PrintWriter(socket.getOutputStream(),
						true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				String inputLine, outputLine;
				while ((inputLine = in.readLine()) != null) {
					if (inputLine.startsWith("bye"))
						break;
					outputLine = processInput(inputLine);
					out.println(outputLine);
				}

				out.close();
				in.close();
				socket.close();
			} catch (IOException e) {
				System.err.println(e);
			}
		}

		public String processInput(String req) {
			String rez = "";
			StringTokenizer st;
			if (req.startsWith("testEE")) {
				// parse request: "testEE Q B S O iter"
				st = new StringTokenizer(req, " ");
				String scriptname = "";
				if (st.hasMoreTokens()) {
					scriptname = st.nextToken();
				}
				Integer[] params = new Integer[5];
				String c;
				int i = 0;
				while (st.hasMoreTokens()) {
					c = st.nextToken();
					int n = Integer.parseInt(c);
					params[i] = new Integer(n);
					i++;
				}
				if (i < 5) {
					rez = "not sent";
				} else {
					// send it to matlab
					Object[] args = new Object[5];
					for (i = 0; i < 5; i++)
						args[i] = params[i];
					if (scriptname.equals("testEE")) {
						try {
							Object[] returnVals = new Object[1];
							returnVals[0] = mc.blockingFeval(scriptname, args);
						} catch (Exception e) {
							System.err.println(e);
						}
					}
					rez = "1";
				}
			} else {
				try {
					mc.eval(req);
				} catch (Exception e) {
					System.err.println(e);
				}
			}
			return rez;
		}
	}
}
