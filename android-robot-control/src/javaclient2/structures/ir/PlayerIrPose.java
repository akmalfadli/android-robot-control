/*
 *  Player Java Client 2 - PlayerIrPose.java
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
 * $Id: PlayerIrPose.java,v 1.1.1.1 2006/02/15 17:51:18 veedee Exp $
 *
 */

package javaclient2.structures.ir;

import javaclient2.structures.*;

/**
 * Request/reply: get pose
 * To query the pose of the IRs, send a null PLAYER_IR_POSE request.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v2.0 - Player 2.0 supported
 * </ul>
 */
public class PlayerIrPose implements PlayerConstants {

    // the number of ir samples returned by this robot 
    private int poses_count;
    // the pose of each IR detector on this robot 
    private PlayerPose[] poses = new PlayerPose[PLAYER_IR_MAX_SAMPLES];


    /**
     * @return  the number of ir samples returned by this robot 
     **/
    public synchronized int getPoses_count () {
        return this.poses_count;
    }

    /**
     * @param newPoses_count  the number of ir samples returned by this robot 
     *
     */
    public synchronized void setPoses_count (int newPoses_count) {
        this.poses_count = newPoses_count;
    }
    /**
     * @return  the pose of each IR detector on this robot 
     **/
    public synchronized PlayerPose[] getPoses () {
        return this.poses;
    }

    /**
     * @param newPoses  the pose of each IR detector on this robot 
     *
     */
    public synchronized void setPoses (PlayerPose[] newPoses) {
        this.poses = newPoses;
    }

}