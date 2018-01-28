package NHSensor.NHSensorSim.link;

import java.util.Random;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import NHSensor.NHSensorSim.chart.ChartFrame;

public class LinkModelOld {
	private double pt; // in dbm
	private double pld0;
	private double d0 = 1; // in m
	private double n; // path loss exponent
	private double sigma; // N(0, σ),
	private double pn; // power of noise

	private double encodingRatio = 2; // encoding ratio, 2 forManchester
										// encoding
	private double frameLength; // the frame length in bytes

	public LinkModelOld(double d0, double n, double pld0, double pt,
			double sigma) {
		super();
		this.d0 = d0;
		this.n = n;
		this.pld0 = pld0;
		this.pt = pt;
		this.sigma = sigma;
	}

	public LinkModelOld(double d0, double n, double pld0, double pt, double pn,
			double sigma, double encodingRatio, double frameLength) {
		super();
		this.d0 = d0;
		this.n = n;
		this.pld0 = pld0;
		this.pt = pt;
		this.pn = pn;
		this.sigma = sigma;
		this.encodingRatio = encodingRatio;
		this.frameLength = frameLength;
	}

	public double getEncodingRatio() {
		return encodingRatio;
	}

	public void setEncodingRatio(double encodingRatio) {
		this.encodingRatio = encodingRatio;
	}

	public double getFrameLength() {
		return frameLength;
	}

	public void setFrameLength(double frameLength) {
		this.frameLength = frameLength;
	}

	public double getPt() {
		return pt;
	}

	public void setPt(double pt) {
		this.pt = pt;
	}

	public double getPld0() {
		return pld0;
	}

	public void setPld0(double pld0) {
		this.pld0 = pld0;
	}

	public double getD0() {
		return d0;
	}

	public void setD0(double d0) {
		this.d0 = d0;
	}

	public double getN() {
		return n;
	}

	public void setN(double n) {
		this.n = n;
	}

	public double getSigma() {
		return sigma;
	}

	public void setSigma(double sigma) {
		this.sigma = sigma;
	}

	public double getPn() {
		return pn;
	}

	public void setPn(double pn) {
		this.pn = pn;
	}

	public LinkModelOld(double sigma, double frameLength, double n,
			double encodingRatio, double pn, double pt) {
		super();
		this.sigma = sigma;
		this.frameLength = frameLength;
		this.n = n;
		this.encodingRatio = encodingRatio;
		this.pn = pn;
		this.pt = pt;
	}

	/*
	 * average signal to noise ratio
	 */
	public double averageSignalToNoiseRatio(double d) {
		return pt - pld0 - 10 * n * Math.log(d / d0) - pn;
	}

	public double log(double x) {
		return Math.log(x) / Math.log(10);
	}

	/*
	 * random signal to noise ratio
	 */
	public double signalToNoiseRatio(double d) {
		return pt - pld0 - 10 * n * log(d / d0) - pn
				+ this.Norm_rand1(0, this.sigma);
	}

	/*
	 * random received power
	 */
	public double pr(double d) {

		return pt - pld0 - 10 * n * log(d / d0)
				+ this.Norm_rand1(0, this.sigma);
	}

	// In paper Efficient Geographic Routing over Lossy
	// Links in Wireless Sensor Networks
	public double prr(double d) {
		double x = -1 / 1.28 * Math.pow(10, this.signalToNoiseRatio(d) / 10);
		double a = 1 - 0.5 * Math.exp(x);
		double b = this.encodingRatio * 8 * this.frameLength;
		return Math.pow(a, b);
	}

	// In paper Analyzing the Transitional Region in Low Power
	// Wireless Links
	public double prr1(double d) {
		double x = -1.0 / 1.28 * this.signalToNoiseRatio(d);
		double a = 1 - 0.5 * Math.exp(x);
		double b = 8 * this.frameLength;
		return Math.pow(a, b);
	}

	// 正态分布
	public double Norm_rand1(double miu, double sigma) {
		double N = 12;
		double x = 0, temp = N;
		do {
			x = 0;
			for (int i = 0; i < N; i++)
				x = x + (Math.random());
			x = (x - temp / 2) / (Math.sqrt(temp / 12));
			x = miu + x * sigma;
		} while (x <= 0);
		return x;
	}

	public double Norm_rand(double miu, double sigma) {
		Random rand = new Random();
		return sigma * rand.nextGaussian() + miu;
	}

	// 泊松分布
	public double P_rand(double Lamda) {
		double x = 0, b = 1, c = Math.exp(-Lamda), u;
		do {
			u = Math.random();
			b *= u;
			if (b >= c)
				x++;
		} while (b >= c);
		return x;
	}

	public static void test() {
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries xYSeries = new XYSeries("test");
		dataset.addSeries(xYSeries);

		double x, y;
		for (int j = 1; j < 40; j++) {
			for (int i = 0; i < 20; i++) {
				x = j;
				y = i;
				xYSeries.add(x, y);
			}
		}

		System.out.println("Link Model");
		String frameTitle = "";
		String xAxisLabel = "";
		String yAxisLabel = "";
		String chartTitle = "";
		ChartFrame.showDatasetByPlot(dataset, frameTitle, xAxisLabel,
				yAxisLabel, chartTitle);
	}

	// In paper Efficient Geographic Routing over Lossy
	// Links in Wireless Sensor Networks
	public static void t() {
		double pt = 0;
		double pld0 = 55;
		double d0 = 1;
		double n = 4;
		double sigma = 4;
		double pn = -105;

		double encodingRatio = 1;
		double frameLength = 50;

		LinkModelOld lm = new LinkModelOld(d0, n, pld0, pt, pn, sigma,
				encodingRatio, frameLength);
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries xYSeries = new XYSeries("test");
		dataset.addSeries(xYSeries);

		double x, y;
		for (int dist = 1; dist < 60; dist++) {
			for (int i = 0; i < 10; i++) {
				x = dist;
				y = lm.prr(x);
				xYSeries.add(x, y);
				System.out.println(x + " " + y);
			}
		}

		System.out.println("Link Model");
		String frameTitle = "";
		String xAxisLabel = "";
		String yAxisLabel = "";
		String chartTitle = "";
		ChartFrame.showDatasetByPlot(dataset, frameTitle, xAxisLabel,
				yAxisLabel, chartTitle);
	}

	// In paper Analyzing the Transitional Region in Low Power
	// Wireless Links
	public static void t1() {
		double pt = 0;
		double pld0 = 55;
		double d0 = 1;
		double n = 4;
		double sigma = 4;
		double pn = -105;

		double encodingRatio = 2;
		double frameLength = 50;

		LinkModelOld lm = new LinkModelOld(d0, n, pld0, pt, pn, sigma,
				encodingRatio, frameLength);
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries xYSeries = new XYSeries("test");
		dataset.addSeries(xYSeries);

		double x, y;
		for (int dist = 1; dist < 40; dist++) {
			for (int i = 0; i < 20; i++) {
				x = dist;
				y = lm.prr1(x);
				xYSeries.add(x, y);
				System.out.println(x + " " + y);
			}
		}

		System.out.println("Link Model");
		String frameTitle = "";
		String xAxisLabel = "";
		String yAxisLabel = "";
		String chartTitle = "";
		ChartFrame.showDatasetByPlot(dataset, frameTitle, xAxisLabel,
				yAxisLabel, chartTitle);
	}

	public static void main(String[] args) {
		/*
		 * double pt = 0; double pld0 = 55; double d0 = 1; double n = 4; double
		 * sigma = 4; double pn = -105;
		 * 
		 * double encodingRatio = 2; double frameLength = 50;
		 * 
		 * LinkModel lm = new LinkModel(d0, n, pld0, pt, sigma);
		 * XYSeriesCollection dataset = new XYSeriesCollection(); XYSeries
		 * xYSeries = new XYSeries("test"); dataset.addSeries(xYSeries);
		 * 
		 * double x,y; for(int dist=1;dist<40;dist++) { for(int i=0;i<20;i++) {
		 * x = dist; y = lm.pr(x); xYSeries.add(x,y);
		 * System.out.println(x+" "+y); } }
		 * 
		 * System.out.println("Link Model"); String frameTitle = ""; String
		 * xAxisLabel = ""; String yAxisLabel = ""; String chartTitle = "";
		 * ChartFrame.showDatasetByPlot(dataset, frameTitle, xAxisLabel,
		 * yAxisLabel, chartTitle);
		 */
		// test();
		t();
	}

}
