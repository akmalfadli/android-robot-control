/*
 *  Player Java Client 2 - PlayerPosition2dPowerConfig.java
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
 * $Id: PlayerPosition2dPowerConfig.java,v 1.1.1.1 2006/02/15 17:51:18 veedee Exp $
 *
 */

package javaclient2.structures.position2d;

import javaclient2.structures.*;

/**
 * Request/reply: Motor power.
 * On some robots, the motor power can be turned on and off from software.
 * To do so, send a PLAYER_POSITION2D_REQ_MOTOR_POWER request with the
 * format given below, and with the appropriate state (zero for motors
 * off and non-zero for motors on).  Null response.
 * Be VERY careful with this command!  You are very likely to start the
 * robot running across the room at high speed with the battery charger
 * still attached.
 * 
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v2.0 - Player 2.0 supported
 * </ul>
 */
public class PlayerPosition2dPowerConfig implements PlayerConstants {

    // FALSE for off, TRUE for on 
    private byte state;


    /**
     * @return  FALSE for off, TRUE for on 
     **/
    public synchronized byte getState () {
        return this.state;
    }

    /**
     * @param newState  FALSE for off, TRUE for on 
     *
     */
    public synchronized void setState (byte newState) {
        this.state = newState;
    }

}