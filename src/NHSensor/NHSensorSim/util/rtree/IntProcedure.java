//   IntProcedure.java
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
//  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

package NHSensor.NHSensorSim.util.rtree;

/**
 * Interface that defines a procedure to be executed, that takes an int
 * parameter
 * 
 * @author aled@sourceforge.net
 * @version 1.0b2p1
 */
public interface IntProcedure {
	/**
	 * @param id
	 *            integer value
	 * 
	 * @return flag to indicate whether to continue executing the procedure.
	 *         Return true to continue executing, or false to prevent any more
	 *         calls to this method.
	 */
	public boolean execute(int id);
}
