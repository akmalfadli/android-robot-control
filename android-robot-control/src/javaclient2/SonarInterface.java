/*
 *  Player Java Client 2 - SonarInterface.java
 *  Copyright (C) 2002-2006 Radu Bogdan Rusu, Maxim Batalin
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
 * $Id: SonarInterface.java,v 1.5 2006/03/10 19:05:00 veedee Exp $
 *
 */
package javaclient2;

import java.io.IOException;

import javaclient2.xdr.OncRpcException;
import javaclient2.xdr.XdrBufferDecodingStream;
import javaclient2.xdr.XdrBufferEncodingStream;

import javaclient2.structures.PlayerMsgHdr;
import javaclient2.structures.PlayerPose;
import javaclient2.structures.sonar.PlayerSonarData;
import javaclient2.structures.sonar.PlayerSonarGeom;

/**
 * The sonar interface provides access to a collection of fixed range 
 * sensors, such as a sonar array.
 * @author Radu Bogdan Rusu, Maxim Batalin 
 * @version
 * <ul>
 *      <li>v2.0 - Player 2.0 supported
 * </ul>
 */
public class SonarInterface extends PlayerDevice {
	
    private static final boolean isDebugging = PlayerClient.isDebugging;

    private PlayerSonarData psdata;
    private boolean      	readyPsdata = false;
    private PlayerSonarGeom psgeom;
    private boolean      	readyPsgeom = false;

    /**
     * Constructor for SonarInterface.
     * @param pc a reference to the PlayerClient object
     */
    public SonarInterface (PlayerClient pc) { super(pc); }
	
    /**
     * Read the sonar values.
     */
    public synchronized void readData (PlayerMsgHdr header) {
        try {
        	switch (header.getSubtype ()) {
        		case PLAYER_SONAR_DATA_RANGES: {
        			// Buffer for reading ranges_count
        			byte[] buffer = new byte[4];
        			// Read ranges_count
        			is.readFully (buffer, 0, 4);
        			
        			// Begin decoding the XDR buffer
        			XdrBufferDecodingStream xdr = new XdrBufferDecodingStream (buffer);
        			xdr.beginDecoding ();
        			int rangesCount = xdr.xdrDecodeInt ();
        			xdr.endDecoding   ();
        			xdr.close ();
        			
        			// Buffer for reading range values
        			buffer = new byte[PLAYER_SONAR_MAX_SAMPLES * 4 + 4];
        			// Read range values
        			is.readFully (buffer, 0, rangesCount * 4 + 4);
        			xdr = new XdrBufferDecodingStream (buffer);
        			xdr.beginDecoding ();
        			float[] ranges = xdr.xdrDecodeFloatVector ();
        			xdr.endDecoding   ();
        			xdr.close ();
        			
        			psdata = new PlayerSonarData ();
        			
        			psdata.setRanges_count (rangesCount);
        			psdata.setRanges       (ranges);
        			
        			readyPsdata = true;
        			break;
        		}
        	}
        } catch (IOException e) {
        	throw new PlayerException 
        		("[Sonar] : Error reading payload: " + 
        				e.toString(), e);
        } catch (OncRpcException e) {
        	throw new PlayerException 
        		("[Sonar] : Error while XDR-decoding payload: " + 
        				e.toString(), e);
        }
    }
	
    /**
     * Get the state data.
     * @return an object of type PlayerSonarData containing the requested data 
     */
    public PlayerSonarData getData () { return this.psdata; }
    
    /**
     * Get the geometry data.
     * @return an object of type PlayerSonarGeom containing the requested geometry data 
     */
    public PlayerSonarGeom getGeom () { return this.psgeom; }
    
    /**
     * Check if data is available.
     * @return true if ready, false if not ready 
     */
    public boolean isDataReady () {
        if (readyPsdata) {
        	readyPsdata = false;
            return true;
        }
        return false;
    }
    
    /**
     * Check if geometry data is available.
     * @return true if ready, false if not ready 
     */
    public boolean isGeomReady () {
        if (readyPsgeom) {
        	readyPsgeom = false;
            return true;
        }
        return false;
    }
    
    /**
     * Request/reply: Query geometry.
     * <br><br>
     * See the player_sonar_geom structure from player.h
     */
    public void queryGeometry () {
        try {
            sendHeader (PLAYER_MSGTYPE_REQ, PLAYER_SONAR_REQ_GET_GEOM, 0);
            os.flush ();
        } catch (IOException e) {
        	throw new PlayerException 
        		("[Sonar] : Couldn't request PLAYER_SONAR_REQ_GET_GEOM: " + 
        				e.toString(), e);
        }
    }
	
    /**
     * Request/reply: Sonar power.
     * <br><br>
     * On some robots, the sonars can be turned on and off from software.
     * <br>
     * To do so, send a PLAYER_SONAR_REQ_POWER request.
     * <br><br>
     * See the player_sonar_power_config structure from player.h
     * @param state turn power off (0) or on (>0)
     */
    public void setSonarPower (int state) {
        try {
        	sendHeader (PLAYER_MSGTYPE_REQ, PLAYER_SONAR_REQ_POWER, 4);
        	XdrBufferEncodingStream xdr = new XdrBufferEncodingStream (4);
        	xdr.beginEncoding (null, 0);
        	xdr.xdrEncodeByte ((byte)state);
        	xdr.endEncoding ();
        	os.write (xdr.getXdrData (), 0, xdr.getXdrLength ());
        	xdr.close ();
        	os.flush ();
        } catch (IOException e) {
        	throw new PlayerException 
        		("[Sonar] : Couldn't request PLAYER_SONAR_REQ_POWER: " + 
        				e.toString(), e);
        } catch (OncRpcException e) {
        	throw new PlayerException 
        		("[Sonar] : Error while XDR-encoding POWER request: " + 
        				e.toString(), e);
        }
    }
	
    /**
     * Handle acknowledgement response messages
     * @param header Player header
     */
    public void handleResponse (PlayerMsgHdr header) {
        try {
            switch (header.getSubtype ()) {
                case PLAYER_SONAR_REQ_GET_GEOM: {
                	// Buffer for reading poses_count
                	byte[] buffer = new byte[4];
                	// Read poses_count
                	is.readFully (buffer, 0, 4);
                	
                	// Begin decoding the XDR buffer
                	XdrBufferDecodingStream xdr = new XdrBufferDecodingStream (buffer);
                	xdr.beginDecoding ();
                	int posesCount = xdr.xdrDecodeInt ();
                	xdr.endDecoding   ();
                	xdr.close ();
                	
                	// Buffer for reading sonar poses
                	buffer = new byte[PLAYER_SONAR_MAX_SAMPLES * 12];
                	// Read sonar poses
                	is.readFully (buffer, 0, posesCount * 12);
                	xdr = new XdrBufferDecodingStream (buffer);
                	xdr.beginDecoding ();
                	PlayerPose[] pps = new PlayerPose[posesCount];
                	for (int i = 0; i < posesCount; i++) {
                		PlayerPose pp = new PlayerPose ();
                		pp.setPx (xdr.xdrDecodeFloat ());
                		pp.setPy (xdr.xdrDecodeFloat ());
                		pp.setPa (xdr.xdrDecodeFloat ());
                		pps[i] = pp;
                	}
                	xdr.endDecoding   ();
                	xdr.close ();
                	
                	psgeom = new PlayerSonarGeom (); 
                	psgeom.setPoses_count (posesCount);
                	psgeom.setPoses (pps);
                	
                	readyPsgeom = true;
                	break;
                }
                case PLAYER_SONAR_REQ_POWER: {
                    break;
                }
                default:{
                	if (isDebugging)
                		System.err.println ("[Sonar]Debug] : " +
                				"Unexpected response " + header.getSubtype () + 
                				" of size = " + header.getSize ());
                    break;
                }
            }
        } catch (IOException e) {
        	throw new PlayerException 
        		("[Sonar] : Error reading payload: " + 
        				e.toString(), e);
        } catch (OncRpcException e) {
        	throw new PlayerException 
        		("[Sonar] : Error while XDR-decoding payload: " + 
        				e.toString(), e);
        }
    }
}

