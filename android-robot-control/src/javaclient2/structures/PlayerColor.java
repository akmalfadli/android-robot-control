/*
 *  Player Java Client 2 - PlayerColor.java
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
 * $Id: PlayerColor.java,v 1.1 2006/02/28 21:45:48 veedee Exp $
 *
 */
package javaclient2.structures;

/**
 * A color descriptor.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v2.0 - Player 2.0 supported
 * </ul>
 */
public class PlayerColor {
	
	private byte alpha;		// Alpha (transparency) channel
	private byte red;		// Red color channel
	private byte green;		// Green color channel
	private byte blue;		// Blue color channel
    
    /**
     * 
     * @return Alpha (transparency) channel
     */
    public synchronized float getAlpha () {
    	return this.alpha;
    }
    
    /**
     * 
     * @param newAlpha Alpha (transparency) channel 
     */
    public synchronized void setAlpha (int newAlpha) {
    	this.alpha = (byte)newAlpha;
    }

    /**
     * 
     * @return Red color channel
     */
    public synchronized float getRed () {
    	return this.red;
    }
    
    /**
     * 
     * @param newRed Red color channel 
     */
    public synchronized void setRed (int newRed) {
    	this.red = (byte)newRed;
    }

    /**
     * 
     * @return Green color channel
     */
    public synchronized float getGreen () {
    	return this.green;
    }
    
    /**
     * 
     * @param newGreen Green color channel 
     */
    public synchronized void setGreen (int newGreen) {
    	this.green = (byte)newGreen;
    }

    /**
     * 
     * @return Blue color channel
     */
    public synchronized float getBlue () {
    	return this.blue;
    }
    
    /**
     * 
     * @param newBlue Blue color channel 
     */
    public synchronized void setBlue (int newBlue) {
    	this.blue = (byte)newBlue;
    }
}