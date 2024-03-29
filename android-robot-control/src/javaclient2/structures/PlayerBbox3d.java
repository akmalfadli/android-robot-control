/*
 *  Player Java Client 2 - PlayerBbox3d.java
 *  Copyright (C) 2006 Radu Bogdan Rusu
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * $Id: PlayerBbox3d.java,v 1.2 2006/02/20 22:44:57 veedee Exp $
 *
 */
package javaclient2.structures;

/**
 * A rectangular bounding box, used to define the size of an object.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v2.0 - Player 2.0 supported
 * </ul>
 */
public class PlayerBbox3d {
    private float sw;		// Width [m]
    private float sl;		// Lenght [m]
    private float sh;		// Height [m]
    
    /**
     * 
     * @return Width [m]
     */
    public synchronized float getSw () {
    	return this.sw;
    }
    
    /**
     * 
     * @param newSw Width [m] 
     */
    public synchronized void setSw (float newSw) {
    	this.sw = newSw;
    }
    
    /**
     * 
     * @return Lenght [m] 
     */
    public synchronized float getSl () {
        return this.sl;
    }
    
    /**
     * 
     * @param newSl Lenght [m] 
     */
    public synchronized void setSl (float newSl) {
        this.sl = newSl;
    }

    /**
     * 
     * @return Height [m] 
     */
    public synchronized float getSh () {
        return this.sh;
    }
    
    /**
     * 
     * @param newSh Height [m] 
     */
    public synchronized void setSh (float newSh) {
        this.sh = newSh;
    }
}