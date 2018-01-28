package NHSensor.NHSensorSim.test;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;

public class StreamTokenizerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StreamTokenizer st = new StreamTokenizer(new StringReader(
				"ab, , c1 23 d ?,"));
		st.eolIsSignificant(false);
		st.wordChars('\'', '}');
		st.wordChars('?', '?');
		st.wordChars('"', '.');
		st.ordinaryChars('0', '9');
		st.wordChars('0', '9');
		String nextToken;

		try {
			while (st.nextToken() != StreamTokenizer.TT_EOF) {
				if (st.ttype == StreamTokenizer.TT_WORD) {
					nextToken = st.sval;
					System.out.println(nextToken);
				}

				else
					continue;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
