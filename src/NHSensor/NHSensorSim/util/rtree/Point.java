//   Point.java
//   Java Spatial Index Library
//   Copyright (C) 2002 Infomatiq Limited
//  
//  This library is free software; you can redistribute it and/or
//  modify it under the terms of the GNU Lesser General Public
//  License as published by the Free Software Foundation; either
//  version 2.1 of the License, or (at your option) any later version.
//  
//  This library is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//  Lesser General Public License for more details.
//  
//  You should have received a copy of the GNU Lesser General Public
//  License along with this library; if not, write to the Free Software
//  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA

package NHSensor.NHSensorSim.util.rtree;

/**
 * Currently hardcoded to 2 dimensions, but could be extended.
 * 
 * @author aled@sourceforge.net
 * @version 1.0b2p1
 */
public class Point {
	/**
	 * Number of dimensions in a point. In theory this could be exended to three
	 * or more dimensions.
	 */
	private final static int DIMENSIONS = 2;

	/**
	 * The (x, y) coordinates of the point.
	 */
	public float[] coordinates;

	/**
	 * Constructor.
	 * 
	 * @param x
	 *            The x coordinate of the point
	 * @param y
	 *            The y coordinate of the point
	 */
	public Point(float x, float y) {
		coordinates = new float[DIMENSIONS];
		coordinates[0] = x;
		coordinates[1] = y;
	}
}
