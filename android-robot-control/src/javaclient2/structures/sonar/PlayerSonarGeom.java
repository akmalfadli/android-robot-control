/*
 *  Player Java Client 2 - PlayerSonarGeom.java
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
 * $Id: PlayerSonarGeom.java,v 1.1.1.1 2006/02/15 17:51:19 veedee Exp $
 *
 */

package javaclient2.structures.sonar;

import javaclient2.structures.*;

/**
 * Data AND Request/reply: geometry.
 * To query the geometry of the sonar transducers, send a null 
 * PLAYER_SONAR_REQ_GET_GEOM request.  Depending on the underlying
 * driver, this message can also be sent as data with the subtype
 * PLAYER_SONAR_DATA_GEOM. 
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v2.0 - Player 2.0 supported
 * </ul>
 */
public class PlayerSonarGeom implements PlayerConstants {

    // The number of valid poses. 
    private int poses_count;
    // Pose of each sonar, in robot cs 
    private PlayerPose[] poses = new PlayerPose[PLAYER_SONAR_MAX_SAMPLES];


    /**
     * @return  The number of valid poses. 
     **/
    public synchronized int getPoses_count () {
        return this.poses_count;
    }

    /**
     * @param newPoses_count  The number of valid poses. 
     *
     */
    public synchronized void setPoses_count (int newPoses_count) {
        this.poses_count = newPoses_count;
    }
    /**
     * @return  Pose of each sonar, in robot cs 
     **/
    public synchronized PlayerPose[] getPoses () {
        return this.poses;
    }

    /**
     * @param newPoses  Pose of each sonar, in robot cs 
     *
     */
    public synchronized void setPoses (PlayerPose[] newPoses) {
        this.poses = newPoses;
    }

}