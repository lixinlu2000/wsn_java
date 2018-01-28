package NHSensor.NHSensorSim.util;

import java.awt.Color;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.jfree.data.xy.XYDataset;

import NHSensor.NHSensorSim.core.GlobalConstants;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPairCollections;
import NHSensor.NHSensorSim.shape.Position;

public class Util {
	static String[] PLOT_STYLE = { "r+-", "bo-", "gs-", "c*-", "yd-", "", "",
			"" };
	static String[] COLORS = { "r", "b", "g", "c", "y", "", "", "" };
	static String[] FIGURE_FORMATS = { "epsc", "jpeg", "tiff", "bitmap", "png" };
	static String[] FIGURE_FORMATS_EXT = { "eps", "jpeg", "tif", "bmp", "png" };
	static String timeFormat = "yyyy_MM_dd_hh_mm_ss";
	static String currentTimeString = null;
	static boolean OVERIDE_FINAL_RESULT = true;
	public static Color colors[] = { Color.black, Color.blue, Color.cyan,
			Color.darkGray, Color.gray, Color.green, Color.lightGray,
			Color.cyan, Color.magenta, Color.orange, Color.pink, Color.red,
			Color.yellow };

	public static Color getColor(int id) {
		return Util.colors[id % colors.length];
	}

	public static String getColors(int size) {
		StringBuffer colors = new StringBuffer();
		for (int i = 0; i < size; i++) {
			colors.append(COLORS[i]);
		}
		return colors.toString();
	}
	
	public static String getParentPackageName(String packageName) {
		int index = packageName.lastIndexOf('.');
		return packageName.substring(0, index);
	}

	public static String generateFilePath(String path) {
		StringBuffer abosolutePath = new StringBuffer(
				GlobalConstants.EXPERIMENT_RESULT_FILE_PATH);

		if (path != null && !path.startsWith("/")) {
			abosolutePath.append("/");
		}
		abosolutePath.append(path);
		return abosolutePath.toString();
	}

	public static String generateFileName(String path, String xFieldName,
			String yFieldName) {
		StringBuffer fileName = new StringBuffer(path);

		// TODO just work for windows
		if (path != null && !path.endsWith("\\")) {
			fileName.append("\\");
		}
		fileName.append(xFieldName + "_" + yFieldName + "\\");

		String currentTimeString = Util.peekCurrentTimeString();

		fileName.append(xFieldName);
		fileName.append("_");
		fileName.append(yFieldName);
		if (!OVERIDE_FINAL_RESULT) {
			fileName.append("_");
			fileName.append(currentTimeString);
		}
		fileName.append(".txt");
		return fileName.toString();
	}

	public static void generateCurrentTimeString() {
		Date currentTime = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(Util.timeFormat);
		currentTimeString = formatter.format(currentTime);
	}

	public static String peekCurrentTimeString() {
		if (currentTimeString == null)
			Util.generateCurrentTimeString();
		return currentTimeString;
	}

	/*
	 * generate matlab file name for plot figure
	 */
	public static String generateMatlabFileName(String path, String xFieldName,
			String yFieldName) {
		StringBuffer fileName = new StringBuffer();
		fileName.append(path);

		// TODO just work for windows
		if (path != null && !path.endsWith("\\")) {
			fileName.append("\\");
		}
		fileName.append(xFieldName + "_" + yFieldName + "\\");

		String currentTimeString = Util.peekCurrentTimeString();

		fileName.append(xFieldName);
		fileName.append("_");
		fileName.append(yFieldName);
		if (!OVERIDE_FINAL_RESULT) {
			fileName.append("_");
			fileName.append(currentTimeString);
		}
		fileName.append(".m");
		return fileName.toString();
	}

	/*
	 * generate matlab file name for bar figure
	 */
	public static String generateMatlabBarFileName(String path,
			String xFieldName, String yFieldName) {
		StringBuffer fileName = new StringBuffer();
		fileName.append(path);

		// TODO just work for windows
		if (path != null && !path.endsWith("\\")) {
			fileName.append("\\");
		}
		fileName.append(xFieldName + "_" + yFieldName + "\\");

		String currentTimeString = Util.peekCurrentTimeString();

		fileName.append(xFieldName);
		fileName.append("_");
		fileName.append(yFieldName);
		fileName.append("_");
		fileName.append("bar");
		if (!OVERIDE_FINAL_RESULT) {
			fileName.append("_");
			fileName.append(currentTimeString);
		}
		fileName.append(".m");
		return fileName.toString();
	}

	/*
	 * generate matlab file name for bar horizontal legend figure
	 */
	public static String generateMatlabBarHorizontalLegendFileName(String path,
			String xFieldName, String yFieldName) {
		StringBuffer fileName = new StringBuffer();
		fileName.append(path);

		// TODO just work for windows
		if (path != null && !path.endsWith("\\")) {
			fileName.append("\\");
		}
		fileName.append(xFieldName + "_" + yFieldName + "\\");

		String currentTimeString = Util.peekCurrentTimeString();

		fileName.append(xFieldName);
		fileName.append("_");
		fileName.append(yFieldName);
		fileName.append("_");
		fileName.append("bar_HorizontalLegend");
		if (!OVERIDE_FINAL_RESULT) {
			fileName.append("_");
			fileName.append(currentTimeString);
		}
		fileName.append(".m");
		return fileName.toString();
	}

	public static String generateMatlabFileName(String path, String xFieldName,
			String yFieldName, int id) {
		StringBuffer fileName = new StringBuffer();
		fileName.append(path);

		// TODO just work for windows
		if (path != null && !path.endsWith("\\")) {
			fileName.append("\\");
		}
		fileName.append(xFieldName + "_" + yFieldName + "\\");

		fileName.append(xFieldName);
		fileName.append("_");
		fileName.append(yFieldName);
		fileName.append("_");
		fileName.append(id);
		fileName.append(".m");
		return fileName.toString();
	}

	public static int byteNum(int bitNum) {
		int a = bitNum / 8;
		int b = bitNum % 8;
		if (b == 0)
			return a;
		else
			return a + 1;
	}

	public static String stringReplace(String source, String from, String to) {
		StringBuffer bf = new StringBuffer("");
		StringTokenizer st = new StringTokenizer(source, from, true);
		while (st.hasMoreTokens()) {
			String tmp = st.nextToken();
			if (tmp.equals(from)) {
				bf.append(to);
			} else {
				bf.append(tmp);
			}
		}
		return bf.toString();
	}

	public static String toString(Vector vector) {
		StringBuffer sb = new StringBuffer();

		sb.append("Vector(");
		sb.append(vector.size());
		sb.append("){\n");
		for (Iterator it = vector.iterator(); it.hasNext();) {
			sb.append(it.next());
			sb.append(",\n");
		}
		sb.append("}");

		return sb.toString();
	}

	public static void writeStringToFile(String fileName, String data)
			throws IOException {
		File fo = new File(fileName);
		if (!fo.exists()) {
			fo.createNewFile();
		}
		FileWriter fw = new FileWriter(fo);
		fw.write(data);
		fw.close();
	}

	/*
	 * fix the value of the field xFieldName and calculate the average value of
	 * the field yFieldName
	 */
	public static String experimentsToMatlabPlotProgramme(
			ExperimentParamResultPairCollections ec, String xFieldName,
			String yFieldName, String xAxisLabel, String yAxisLabel)
			throws Exception {
		return Util.experimentsToMatlabPlotProgramme(ec, xFieldName,
				yFieldName, xAxisLabel, yAxisLabel, true);
	}

	/*
	 * fix the value of the field xFieldName and calculate the average value of
	 * the field yFieldName
	 */
	public static String experimentsToMatlabPlotProgramme(
			ExperimentParamResultPairCollections ec, String xFieldName,
			String yFieldName, String xAxisLabel, String yAxisLabel,
			boolean filterNonSuccessed) throws Exception {
		XYDataset dataset = ec.generateXYDataset(xFieldName, yFieldName,
				filterNonSuccessed);
		return Util.xYDatasetToMatlabPlotProgramme(dataset, xFieldName,
				yFieldName, xAxisLabel, yAxisLabel);
	}

	/*
	 * fix the value of the field xFieldName and calculate a value of the field
	 * yFieldName to minimize the value of field minimizeeFieldName
	 */
	public static String experimentsToMatlabPlotProgramme(
			ExperimentParamResultPairCollections ec, String xFieldName,
			String yFieldName, String minimizeFieldName, String xAxisLabel,
			String yAxisLabel) throws Exception {
		return Util.experimentsToMatlabPlotProgramme(ec, xFieldName,
				yFieldName, minimizeFieldName, xAxisLabel, yAxisLabel, true);
	}

	/*
	 * fix the value of the field xFieldName and calculate a value of the field
	 * yFieldName to minimize the value of field minimizeeFieldName
	 */
	public static String experimentsToMatlabPlotProgramme(
			ExperimentParamResultPairCollections ec, String xFieldName,
			String yFieldName, String minimizeFieldName, String xAxisLabel,
			String yAxisLabel, boolean filterNonSuccessed) throws Exception {
		XYDataset dataset = ec.generateXYDataset(xFieldName, yFieldName,
				minimizeFieldName, filterNonSuccessed);
		return Util.xYDatasetToMatlabPlotProgramme(dataset, xFieldName,
				yFieldName, xAxisLabel, yAxisLabel);
	}

	public static String experimentsToMatlabBarProgramme(
			ExperimentParamResultPairCollections ec, String xFieldName,
			String yFieldName, String xAxisLabel, String yAxisLabel)
			throws Exception {
		return Util.experimentsToMatlabBarProgramme(ec, xFieldName, yFieldName,
				xAxisLabel, yAxisLabel, true);
	}

	public static String experimentsToMatlabBarProgramme(
			ExperimentParamResultPairCollections ec, String xFieldName,
			String yFieldName, String xAxisLabel, String yAxisLabel,
			boolean filterNonSuccessed) throws Exception {
		XYDataset dataset = ec.generateXYDataset(xFieldName, yFieldName,
				filterNonSuccessed);
		return Util.xYDatasetToMatlabBarProgramme(dataset, xFieldName,
				yFieldName, xAxisLabel, yAxisLabel);
	}

	public static String experimentsToMatlabBarProgramme(
			ExperimentParamResultPairCollections ec, String xFieldName,
			String yFieldName, String minimizeFieldName, String xAxisLabel,
			String yAxisLabel) throws Exception {
		return Util.experimentsToMatlabBarProgramme(ec, xFieldName, yFieldName,
				minimizeFieldName, xAxisLabel, yAxisLabel, true);
	}

	/*
	 * fix the value of the field xFieldName and calculate a value of the field
	 * yFieldName to minimize the value of field minimizeeFieldName
	 */
	public static String experimentsToMatlabBarProgramme(
			ExperimentParamResultPairCollections ec, String xFieldName,
			String yFieldName, String minimizeFieldName, String xAxisLabel,
			String yAxisLabel, boolean filterNonSuccessed) throws Exception {
		XYDataset dataset = ec.generateXYDataset(xFieldName, yFieldName,
				minimizeFieldName, filterNonSuccessed);
		return Util.xYDatasetToMatlabBarProgramme(dataset, xFieldName,
				yFieldName, xAxisLabel, yAxisLabel);
	}

	public static String experimentsToMatlabBarHorizontalLegendProgramme(
			ExperimentParamResultPairCollections ec, String xFieldName,
			String yFieldName, String xAxisLabel, String yAxisLabel)
			throws Exception {
		return Util.experimentsToMatlabBarHorizontalLegendProgramme(ec,
				xFieldName, yFieldName, xAxisLabel, yAxisLabel, true);
	}

	public static String experimentsToMatlabBarHorizontalLegendProgramme(
			ExperimentParamResultPairCollections ec, String xFieldName,
			String yFieldName, String xAxisLabel, String yAxisLabel,
			boolean filterNonSuccessed) throws Exception {
		XYDataset dataset = ec.generateXYDataset(xFieldName, yFieldName,
				filterNonSuccessed);
		return Util.xYDatasetToMatlabBarHorizontalLegendProgramme(dataset,
				xFieldName, yFieldName, xAxisLabel, yAxisLabel);
	}

	public static String experimentsToMatlabBarHorizontalLegendProgramme(
			ExperimentParamResultPairCollections ec, String xFieldName,
			String yFieldName, String minimizeFieldName, String xAxisLabel,
			String yAxisLabel) throws Exception {
		return Util.experimentsToMatlabBarHorizontalLegendProgramme(ec,
				xFieldName, yFieldName, minimizeFieldName, xAxisLabel,
				yAxisLabel, true);
	}

	/*
	 * fix the value of the field xFieldName and calculate a value of the field
	 * yFieldName to minimize the value of field minimizeeFieldName
	 */
	public static String experimentsToMatlabBarHorizontalLegendProgramme(
			ExperimentParamResultPairCollections ec, String xFieldName,
			String yFieldName, String minimizeFieldName, String xAxisLabel,
			String yAxisLabel, boolean filterNonSuccessed) throws Exception {
		XYDataset dataset = ec.generateXYDataset(xFieldName, yFieldName,
				minimizeFieldName, filterNonSuccessed);
		return Util.xYDatasetToMatlabBarHorizontalLegendProgramme(dataset,
				xFieldName, yFieldName, xAxisLabel, yAxisLabel);
	}

	public static String xYDatasetToMatlabPlotProgrammeOld(XYDataset dataset,
			String xfieldName, String xAxisLabel, String yAxisLabel)
			throws Exception {
		int seriesCount = dataset.getSeriesCount();
		if (seriesCount == 0)
			throw new Exception("dataset is empty");

		StringBuffer programme = new StringBuffer();

		String xArray = Util.xYDatasetXToMatlabArray(dataset, xfieldName);
		programme.append(xArray + "\n");
		String[] yArrays = new String[seriesCount];
		String[] yArraysName = new String[seriesCount];
		for (int i = 0; i < seriesCount; i++) {
			yArraysName[i] = dataset.getSeriesKey(i).toString();
			yArrays[i] = Util.xYDatasetYToMatlabArray(dataset, i,
					yArraysName[i]);
			programme.append(yArrays[i] + "\n");
		}

		programme.append("figure1 = figure;\n");
		programme.append("set(figure1,'Color',[1,1,1]);\n");
		programme.append("axes1 = axes('XTick'," + xfieldName
				+ ",'Parent',figure1,'Box','on');\n");
		programme.append("xlim(axes1,[min(" + xfieldName + "),max("
				+ xfieldName + ")]);\n");
		StringBuffer legendString = new StringBuffer();
		for (int i = 0; i < seriesCount; i++) {
			programme.append("hold on;\n");
			programme.append("plot(" + xfieldName + "," + yArraysName[i] + ",'"
					+ Util.PLOT_STYLE[i] + "','Parent',axes1);\n");
			if (i != seriesCount - 1)
				legendString.append("'" + yArraysName[i] + "',");
			else
				legendString.append("'" + yArraysName[i] + "'");
		}
		programme.append("xlabel('" + xAxisLabel + "');\n");
		programme.append("ylabel('" + yAxisLabel + "');\n");
		programme.append("legend("
				+ Util.stringReplace(legendString.toString(), "_", "\\_")
				+ ",0);\n");
		return programme.toString();
	}

	/*
	 * 2010-12-1 Add
	 */
	public static String xYDatasetToMatlabPlotProgramme(XYDataset dataset,
			String xfieldName, String yFieldName, String xAxisLabel,
			String yAxisLabel) throws Exception {
		int seriesCount = dataset.getSeriesCount();
		if (seriesCount == 0)
			throw new Exception("dataset is empty");

		StringBuffer programme = new StringBuffer();

		String xArray = Util.xYDatasetXToMatlabArray(dataset, xfieldName);
		programme.append(xArray + "\n");
		String[] yArrays = new String[seriesCount];
		String[] yArraysName = new String[seriesCount];
		for (int i = 0; i < seriesCount; i++) {
			yArraysName[i] = dataset.getSeriesKey(i).toString();
			yArrays[i] = Util.xYDatasetYToMatlabArray(dataset, i,
					yArraysName[i]);
			programme.append(yArrays[i] + "\n");
		}

		programme.append("figure1 = figure;\n");
		programme.append("%figure1 = figure;\n");
		programme.append("fontSize = 18;\n");
		programme.append("set(figure1,'Color',[1,1,1]);\n");
		programme.append("axes1 = axes('XTick'," + xfieldName
				+ ",'Parent',figure1,'Box','on','FontSize', fontSize);\n");
		programme.append("xlim(axes1,[min(" + xfieldName + "),max("
				+ xfieldName + ")]);\n");
		StringBuffer legendString = new StringBuffer();
		for (int i = 0; i < seriesCount; i++) {
			programme.append("hold on;\n");
			programme.append("plot(" + xfieldName + "," + yArraysName[i] + ",'"
					+ Util.PLOT_STYLE[i] + "','Parent',axes1);\n");
			if (i != seriesCount - 1)
				legendString.append("'" + yArraysName[i] + "',");
			else
				legendString.append("'" + yArraysName[i] + "'");
		}
		programme
				.append("xlabel('" + xAxisLabel + "','FontSize', fontSize);\n");
		programme
				.append("ylabel('" + yAxisLabel + "','FontSize', fontSize);\n");
		programme.append("hl = legend("
				+ Util.stringReplace(legendString.toString(), "_", "\\_")
				+ ");\n");
		programme.append("set(hl,'Location','Best','FontSize', fontSize);\n");
		for (int i = 0; i < FIGURE_FORMATS.length; i++) {
			if (Util.OVERIDE_FINAL_RESULT) {
				programme.append("print(gcf,'-d" + FIGURE_FORMATS[i] + "','"
						+ xfieldName + "_" + yFieldName + "."
						+ FIGURE_FORMATS_EXT[i] + "');\n");
			} else {
				programme.append("print(gcf,'-d" + FIGURE_FORMATS[i] + "','"
						+ xfieldName + "_" + yFieldName + "_"
						+ Util.peekCurrentTimeString() + "."
						+ FIGURE_FORMATS_EXT[i] + "');\n");
			}
		}
		return programme.toString();
	}

	/*
	 * 2010-12-1 Add
	 */
	public static String xYDatasetToMatlabBarHorizontalLegendProgramme(
			XYDataset dataset, String xfieldName, String yFieldName,
			String xAxisLabel, String yAxisLabel) throws Exception {
		int seriesCount = dataset.getSeriesCount();
		if (seriesCount == 0)
			throw new Exception("dataset is empty");

		StringBuffer programme = new StringBuffer();

		String xArray = Util.xYDatasetXToMatlabArray(dataset, xfieldName);
		programme.append(xArray + "\n");
		programme.append("x = " + xfieldName + "';\n");

		String[] yArrays = new String[seriesCount];
		String[] yArraysName = new String[seriesCount];
		for (int i = 0; i < seriesCount; i++) {
			yArraysName[i] = dataset.getSeriesKey(i).toString();
			yArrays[i] = Util.xYDatasetYToMatlabArray(dataset, i,
					yArraysName[i]);
			programme.append(yArrays[i] + "\n");
			programme.append("y(:," + (i + 1) + ") = " + yArraysName[i]
					+ "';\n");
		}

		programme.append("figure1 = figure;\n");
		programme.append("fontSize = 18;\n");
		programme.append("set(figure1,'Color',[1,1,1]);\n");
		programme.append("axes1 = axes('XTick'," + xfieldName
				+ ",'Parent',figure1,'Box','on','FontSize', fontSize);\n");
		programme.append("xgap = (max(x)-min(x))/size(x,2)/2;\n");
		programme.append("xlim(axes1,[min(x)-xgap max(x)+xgap]);\n");
		programme.append("maxy = max(max(y));\n");
		programme.append("miny = min(min(y));\n");
		programme.append("ygap1 = (maxy - miny)/10;\n");
		programme.append("ygap2 = (maxy - miny)/5;\n");
		programme.append("ylim(axes1,[miny-ygap1 maxy+ygap2]);\n");
		StringBuffer legendString = new StringBuffer();
		for (int i = 0; i < seriesCount; i++) {
			if (i != seriesCount - 1)
				legendString.append("'" + yArraysName[i] + "',");
			else
				legendString.append("'" + yArraysName[i] + "'");
		}
		programme.append("hold on;\n");
		programme.append("bar1 = bar(x,y,'Parent',axes1);\n");
		for (int i = 0; i < seriesCount; i++) {
			programme.append("set(bar1(" + (i + 1) + "),'facecolor','"
					+ COLORS[i] + "')\n;");
		}
		programme.append("set(bar1,'BarWidth',1);\n");
		programme
				.append("xlabel('" + xAxisLabel + "','FontSize', fontSize);\n");
		programme
				.append("ylabel('" + yAxisLabel + "','FontSize', fontSize);\n");
		programme.append("hl = legend("
				+ Util.stringReplace(legendString.toString(), "_", "\\_")
				+ ");\n");
		programme.append("%Legend Location North,South,East,West\n");
		programme
				.append("set(hl,'Orientation','horizontal','Location','North','FontSize', fontSize);\n");
		for (int i = 0; i < FIGURE_FORMATS.length; i++) {
			if (Util.OVERIDE_FINAL_RESULT) {
				programme.append("print(gcf,'-d" + FIGURE_FORMATS[i] + "','"
						+ xfieldName + "_" + yFieldName
						+ "_bar_HorizontalLegend." + FIGURE_FORMATS_EXT[i]
						+ "');\n");
			} else {
				programme.append("print(gcf,'-d" + FIGURE_FORMATS[i] + "','"
						+ xfieldName + "_" + yFieldName
						+ "_bar_HorizontalLegend."
						+ Util.peekCurrentTimeString() + "."
						+ FIGURE_FORMATS_EXT[i] + "');\n");
			}
		}
		return programme.toString();
	}

	/*
	 * 2010-12-1 Add
	 */
	public static String xYDatasetToMatlabBarProgramme(XYDataset dataset,
			String xfieldName, String yFieldName, String xAxisLabel,
			String yAxisLabel) throws Exception {
		int seriesCount = dataset.getSeriesCount();
		if (seriesCount == 0)
			throw new Exception("dataset is empty");

		StringBuffer programme = new StringBuffer();

		String xArray = Util.xYDatasetXToMatlabArray(dataset, xfieldName);
		programme.append(xArray + "\n");
		programme.append("x = " + xfieldName + "';\n");

		String[] yArrays = new String[seriesCount];
		String[] yArraysName = new String[seriesCount];
		for (int i = 0; i < seriesCount; i++) {
			yArraysName[i] = dataset.getSeriesKey(i).toString();
			yArrays[i] = Util.xYDatasetYToMatlabArray(dataset, i,
					yArraysName[i]);
			programme.append(yArrays[i] + "\n");
			programme.append("y(:," + (i + 1) + ") = " + yArraysName[i]
					+ "';\n");
		}

		programme.append("figure1 = figure;\n");
		programme.append("fontSize = 18;\n");
		programme.append("set(figure1,'Color',[1,1,1]);\n");
		programme.append("axes1 = axes('XTick'," + xfieldName
				+ ",'Parent',figure1,'Box','on','FontSize', fontSize);\n");
		programme.append("xgap = (max(x)-min(x))/size(x,2)/2;\n");
		programme.append("xlim(axes1,[min(x)-xgap max(x)+xgap]);\n");
		programme.append("maxy = max(max(y));\n");
		programme.append("miny = min(min(y));\n");
		programme.append("ygap1 = (maxy - miny)/10;\n");
		programme.append("ygap2 = (maxy - miny)/10;\n");
		programme.append("ylim(axes1,[miny-ygap1 maxy+ygap2]);\n");
		StringBuffer legendString = new StringBuffer();
		for (int i = 0; i < seriesCount; i++) {
			if (i != seriesCount - 1)
				legendString.append("'" + yArraysName[i] + "',");
			else
				legendString.append("'" + yArraysName[i] + "'");
		}
		programme.append("hold on;\n");
		programme.append("bar1 = bar(x,y,'Parent',axes1);\n");
		for (int i = 0; i < seriesCount; i++) {
			programme.append("set(bar1(" + (i + 1) + "),'facecolor','"
					+ COLORS[i] + "');\n");
		}
		programme.append("set(bar1,'BarWidth',1);\n");
		programme
				.append("xlabel('" + xAxisLabel + "','FontSize', fontSize);\n");
		programme
				.append("ylabel('" + yAxisLabel + "','FontSize', fontSize);\n");
		programme.append("hl = legend("
				+ Util.stringReplace(legendString.toString(), "_", "\\_")
				+ ");\n");
		programme.append("%Legend Location North,South,East,West\n");
		programme.append("set(hl,'Location','Best','FontSize', fontSize);\n");
		for (int i = 0; i < FIGURE_FORMATS.length; i++) {
			if (Util.OVERIDE_FINAL_RESULT) {
				programme.append("print(gcf,'-d" + FIGURE_FORMATS[i] + "','"
						+ xfieldName + "_" + yFieldName + "_bar."
						+ FIGURE_FORMATS_EXT[i] + "');\n");
			} else {
				programme.append("print(gcf,'-d" + FIGURE_FORMATS[i] + "','"
						+ xfieldName + "_" + yFieldName + "_bar_"
						+ Util.peekCurrentTimeString() + "."
						+ FIGURE_FORMATS_EXT[i] + "');\n");
			}
		}
		return programme.toString();
	}

	public static String xYDatasetToMatlabScatterProgramme(XYDataset dataset,
			String xfieldName, String xAxisLabel, String yAxisLabel)
			throws Exception {
		int seriesCount = dataset.getSeriesCount();
		if (seriesCount == 0)
			throw new Exception("dataset is empty");

		StringBuffer programme = new StringBuffer();

		String xArray = Util.xYDatasetXToMatlabArray(dataset, xfieldName);
		programme.append(xArray + "\n");
		String[] yArrays = new String[seriesCount];
		String[] yArraysName = new String[seriesCount];
		for (int i = 0; i < seriesCount; i++) {
			yArraysName[i] = dataset.getSeriesKey(i).toString();
			yArrays[i] = Util.xYDatasetYToMatlabArray(dataset, i,
					yArraysName[i]);
			programme.append(yArrays[i] + "\n");
		}

		programme.append("figure1 = figure;\n");
		programme.append("set(figure1,'Color',[1,1,1]);\n");
		programme.append("axes1 = axes('XTick'," + xfieldName
				+ ",'Parent',figure1,'Box','on');\n");
		programme.append("xlim(axes1,[min(" + xfieldName + "),max("
				+ xfieldName + ")]);\n");
		StringBuffer legendString = new StringBuffer();
		for (int i = 0; i < seriesCount; i++) {
			programme.append("hold on;\n");
			programme.append("scatter(" + xfieldName + "," + yArraysName[i]
					+ ");\n");
			if (i != seriesCount - 1)
				legendString.append("'" + yArraysName[i] + "',");
			else
				legendString.append("'" + yArraysName[i] + "'");
		}
		programme.append("xlabel('" + xAxisLabel + "');\n");
		programme.append("ylabel('" + yAxisLabel + "');\n");
		programme.append("legend(" + legendString.toString() + ",0);\n");
		return programme.toString();
	}

	public static String xYDatasetXToMatlabArray(XYDataset dataset,
			int seriesIndex, String arrayName) throws Exception {
		int seriesCount = dataset.getSeriesCount();
		if (seriesIndex > seriesCount - 1)
			throw new Exception("seriesIndex overflow dataset seriesCount");

		StringBuffer arrayString = new StringBuffer();
		arrayString.append(arrayName);
		arrayString.append(" = [");

		int itemCount = dataset.getItemCount(seriesIndex);
		for (int i = 0; i < itemCount; i++) {
			arrayString.append(dataset.getX(seriesIndex, i));
			if (i != itemCount - 1)
				arrayString.append(";");
		}
		arrayString.append("];");
		return arrayString.toString();
	}

	public static String xYDatasetXToMatlabArray(XYDataset dataset,
			String arrayName) throws Exception {
		return Util.xYDatasetXToMatlabArray(dataset, 0, arrayName);
	}

	public static String xYDatasetYToMatlabArray(XYDataset dataset,
			int seriesIndex, String arrayName) throws Exception {
		int seriesCount = dataset.getSeriesCount();
		if (seriesIndex > seriesCount - 1)
			throw new Exception("seriesIndex overflow dataset seriesCount");

		StringBuffer arrayString = new StringBuffer();
		arrayString.append(arrayName);
		arrayString.append(" = [");

		int itemCount = dataset.getItemCount(seriesIndex);
		for (int i = 0; i < itemCount; i++) {
			arrayString.append(dataset.getY(seriesIndex, i));
			if (i != itemCount - 1)
				arrayString.append(";");
		}
		arrayString.append("];");
		return arrayString.toString();
	}

	public static String xYDatasetYToMatlabArray(XYDataset dataset,
			int seriesIndex) throws Exception {
		String arrayName = dataset.getSeriesKey(seriesIndex).toString();
		return Util.xYDatasetXToMatlabArray(dataset, seriesIndex, arrayName);
	}

	public static void objectXmlEncoder(Object obj, String fileName)
			throws FileNotFoundException, IOException, Exception {
		File fo = new File(fileName);
		if (!fo.exists()) {
			String path = fileName.substring(0, fileName.lastIndexOf('.'));
			File pFile = new File(path);
			pFile.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(fo);
		XMLEncoder encoder = new XMLEncoder(fos);
		encoder.writeObject(obj);
		encoder.flush();
		encoder.close();
		fos.close();
	}

	public static List objectXmlDecoder(String objSource)
			throws FileNotFoundException, IOException, Exception {
		List objList = new ArrayList();
		File fin = new File(objSource);
		FileInputStream fis = new FileInputStream(fin);
		XMLDecoder decoder = new XMLDecoder(fis);
		Object obj = null;
		try {
			while ((obj = decoder.readObject()) != null) {
				objList.add(obj);
			}
		} catch (Exception e) {
		}
		fis.close();
		decoder.close();
		return objList;
	}

	/**
	 * Determine if the segment (x1, y1) - (x2, y2) and (x3,y3) - (x4, y4) has
	 * an intersection, and return the intersection point if yes, and return
	 * null otherwise. Parallel lines are always treated as no intersections.
	 * copy from j-sim
	 */
	public static boolean crossSegment(double x1, double y1, double x2,
			double y2, double x3, double y3, double x4, double y4,
			Position crossPosition) {
		double dy0, dy1, dx0, dx1, m0 = 0, m1 = 0, b0 = 0, b1 = 0;
		double xint, yint;

		dy0 = y2 - y1;
		dx0 = x2 - x1;
		dy1 = y4 - y3;
		dx1 = x4 - x3;
		if (dx0 == 0.0 && dx1 == 0.0)
			return false;
		else if (dx0 == 0.0) {
			m1 = dy1 / dx1;
			b1 = y3 - m1 * x3;
			xint = x1;
			yint = m1 * x1 + b1;
		} else if (dx1 == 0.0) {
			m0 = dy0 / dx0;
			b0 = y1 - m0 * x1;
			xint = x3;
			yint = m0 * x3 + b0;
		} else {
			m0 = dy0 / dx0;
			b0 = y1 - m0 * x1;
			m1 = dy1 / dx1;
			b1 = y3 - m1 * x3;
			if (m0 == m1)
				return false;
			else {
				xint = (b0 - b1) / (m1 - m0);
				yint = m1 * xint + b1;
			}
		}

		// is intercept in both line segments?
		if ((xint <= Math.max(x1, x2)) && (xint >= Math.min(x1, x2))
				&& (yint <= Math.max(y1, y2)) && (yint >= Math.min(y1, y2))
				&& (xint <= Math.max(x3, x4)) && (xint >= Math.min(x3, x4))
				&& (yint <= Math.max(y3, y4)) && (yint >= Math.min(y3, y4))) {

			if (crossPosition != null) {
				crossPosition.setX(xint);
				crossPosition.setY(yint);
				return true;
			}
		}
		return false;
	}

	/**
	 * Write an object to disk.
	 * 
	 * @param name
	 *            name of file to write
	 * @param o
	 *            object to write
	 * @throws IOException
	 *             on any input error
	 */
	public static void writeObject(String name, Object o) throws IOException {
		XMLEncoder e = new XMLEncoder(new BufferedOutputStream(
				new FileOutputStream(name)));
		e.writeObject(o);
		e.close();
	}

	public static Object readObject(String name) throws IOException {
		XMLDecoder e = new XMLDecoder(new BufferedInputStream(
				new FileInputStream(name)));
		Object result = e.readObject();
		e.close();
		return result;

	}

	public static Vector arrayToVector(Object[] objects) {
		Vector vector = new Vector();
		for (int i = 0; i < objects.length; i++) {
			vector.add(objects[i]);
		}
		return vector;
	}

	/*
	 * the max rect that node £¨x0,y0) can communicate.
	 */
	public static double minDx(double x0, double y0, double miny, double maxy,
			double radius) {
		double dy1Square = (maxy - y0) * (maxy - y0);
		double dy2Square = (miny - y0) * (miny - y0);
		double radiusSquare = radius * radius;
		double dx1, dx2;

		if (radiusSquare <= dy1Square) {
			dx1 = 0;
		} else {
			dx1 = Math.sqrt(radiusSquare - dy1Square);
		}
		if (radiusSquare <= dy2Square) {
			dx2 = 0;
		} else {
			dx2 = Math.sqrt(radiusSquare - dy2Square);
		}
		return Math.min(dx1, dx2);
	}

	/*
	 * the max rect that node £¨x0,y0) can communicate
	 */
	public static double minDy(double x0, double y0, double minx, double maxx,
			double radius) {
		double dx1Square = (maxx - x0) * (maxx - x0);
		double dx2Square = (minx - x0) * (minx - x0);
		double radiusSquare = radius * radius;
		double dy1, dy2;

		if (radiusSquare <= dx1Square) {
			dy1 = 0;
		} else {
			dy1 = Math.sqrt(radiusSquare - dx1Square);
		}
		if (radiusSquare <= dx2Square) {
			dy2 = 0;
		} else {
			dy2 = Math.sqrt(radiusSquare - dx2Square);
		}
		return Math.min(dy1, dy2);
	}

	/*
	 * the max rect that node £¨x0,y0) can communicate and the nodes in this rect
	 * can communicate with each other
	 */

	public static int sign(double d1, double d2) {
		if (d1 > d2)
			return 1;
		else if (d1 == d2)
			return 0;
		else
			return -1;
	}

}