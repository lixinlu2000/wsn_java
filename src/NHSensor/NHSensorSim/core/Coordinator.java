package NHSensor.NHSensorSim.core;

import java.awt.geom.*;

/*
 * <p>convert user axis to screen axis</p>
 */
public class Coordinator {
	private double ox;
	private double oy;
	private double width;
	private double height;
	private double viewWidth;
	private double viewHeight;
	private double xscale = 1;
	private double yscale = 1;

	public double getXscale() {
		return xscale;
	}

	public void setXscale(double xscale) {
		this.xscale = xscale;
	}

	public double getYscale() {
		return yscale;
	}

	public void setYscale(double yscale) {
		this.yscale = yscale;
	}

	public Coordinator(double viewWidth, double viewHeight) {
		this.viewHeight = viewHeight;
		this.viewWidth = viewWidth;
		this.ox = 0;
		this.oy = 0;
		this.width = viewWidth;
		this.height = viewHeight;
	}

	public Coordinator(double viewWidth, double viewHeight, double ox, double oy) {
		this.viewWidth = viewWidth;
		this.viewHeight = viewHeight;
		this.ox = ox;
		this.oy = oy;
		this.width = viewWidth;
		this.height = viewHeight;
	}

	public Coordinator(double viewWidth, double viewHeight, double ox,
			double oy, double width, double height) {
		this.viewWidth = viewWidth;
		this.viewHeight = viewHeight;
		this.ox = ox;
		this.oy = oy;
		this.width = width;
		this.height = height;
	}

	protected double scaleX(double x) {
		return x * this.getXscale();
	}

	protected double scaleY(double y) {
		return y * this.getYscale();
	}

	public Rectangle2D.Double rectangle(Rectangle2D.Double rect) {
		return new Rectangle2D.Double(this.scaleX(rect.getMinX())
				+ this.getOx(), this.getViewHeight()
				- this.scaleY(rect.getMaxY()) - this.getOy(), this.scaleX(rect
				.getWidth()), this.scaleY(rect.getHeight()));
	}

	public Rectangle2D.Double network(Rectangle2D.Double rect) {
		return new Rectangle2D.Double(rect.getMinX() + this.getOx(), this
				.getViewHeight()
				- this.scaleY(rect.getMaxY()) - this.getOy(), this.scaleX(rect
				.getWidth()), this.scaleY(rect.getHeight()));
	}

	public Ellipse2D.Double ellipse(Ellipse2D.Double e) {
		return new Ellipse2D.Double(this.scaleX(e.getMinX()) + this.getOx(),
				this.getViewHeight() - this.scaleY(e.getMaxY()) - this.getOy(),
				this.scaleX(e.getWidth()), this.scaleY(e.getHeight()));
	}

	public Arc2D.Double arc(Arc2D.Double a) {
		return new Arc2D.Double(this.scaleX(a.getX()) + this.getOx(), this
				.getViewHeight()
				- this.scaleY(a.getY()) - this.getOy(), this.scaleX(a
				.getWidth()), this.scaleY(a.getHeight()), a.getAngleStart(), a
				.getAngleExtent(), a.getArcType());
	}

	public Line2D.Double line(Line2D.Double l) {
		return new Line2D.Double(this.scaleX(l.getP1().getX()) + this.getOx(),
				this.getViewHeight() - this.scaleY(l.getP1().getY())
						- this.getOy(), this.scaleX(l.getP2().getX())
						+ this.getOx(), this.getViewHeight()
						- this.scaleY(l.getP2().getY()) - this.getOy());
	}

	public Point2D.Double point(Point2D.Double p) {
		return new Point2D.Double(this.scaleX(p.getX()) + this.getOx(), this
				.getViewHeight()
				- this.scaleY(p.getY()) - this.getOy());
	}

	public Point2D.Double toCoordinatorPoint(Point2D.Double screenPoint) {
		double y = this.getViewHeight() - screenPoint.getY();
		y = y - this.getOy();
		double x = screenPoint.getX() - this.getOx();
		return new Point2D.Double((int) (x / this.xscale),
				(int) (y / this.yscale));
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getOx() {
		return ox;
	}

	public void setOx(double ox) {
		this.ox = ox;
	}

	public double getOy() {
		return oy;
	}

	public void setOy(double oy) {
		this.oy = oy;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getViewHeight() {
		return viewHeight;
	}

	public void setViewHeight(double viewHeight) {
		this.viewHeight = viewHeight;
	}

	public double getViewWidth() {
		return viewWidth;
	}

	public void setViewWidth(double viewWidth) {
		this.viewWidth = viewWidth;
	}

}
