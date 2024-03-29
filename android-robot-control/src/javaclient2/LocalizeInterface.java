/*
 *  Player Java Client 2 - LocalizeInterface.java
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
 * $Id: LocalizeInterface.java,v 1.4 2006/03/10 19:05:00 veedee Exp $
 *
 */
package javaclient2;

import java.io.IOException;

import javaclient2.xdr.OncRpcException;
import javaclient2.xdr.XdrBufferDecodingStream;
import javaclient2.xdr.XdrBufferEncodingStream;

import javaclient2.structures.PlayerMsgHdr;
import javaclient2.structures.PlayerPose;
import javaclient2.structures.localize.PlayerLocalizeParticle;
import javaclient2.structures.localize.PlayerLocalizeData;
import javaclient2.structures.localize.PlayerLocalizeHypoth;
import javaclient2.structures.localize.PlayerLocalizeSetPose;
import javaclient2.structures.localize.PlayerLocalizeGetParticles;

/**
 * The localize interface provides pose information for the robot. Generally 
 * speaking, localization drivers will estimate the pose of the robot by 
 * comparing observed sensor readings against a pre-defined map of the 
 * environment. See, for the example, the amcl driver, which implements a 
 * probabilistic Monte-Carlo localization algorithm. 
 * <br><br>
 * This interface accepts no commands.
 * @author Radu Bogdan Rusu, Maxim Batalin
 * @version
 * <ul>
 *      <li>v2.0 - Player 2.0 supported
 * </ul>
 */
public class LocalizeInterface extends PlayerDevice {

    private static final boolean isDebugging = PlayerClient.isDebugging;
    
    private PlayerLocalizeData         pldata;
    private boolean                    readyPldata = false;
    private PlayerLocalizeGetParticles plgp;
    private boolean                    readyPlgp = false;

    /**
     * Constructor for LocalizeInterface.
     * @param pc a reference to the PlayerClient object
     */
    public LocalizeInterface (PlayerClient pc) { super(pc); }
    
    /**
     * Read an array of hypotheses.
     */
    public synchronized void readData (PlayerMsgHdr header) {
        try {
        	switch (header.getSubtype ()) {
        		case PLAYER_LOCALIZE_DATA_HYPOTHS: {
        			pldata = new PlayerLocalizeData ();
        			
        			// Buffer for reading pending_count, pending_time, hypoths_count
        			byte[] buffer = new byte[4+8+4];
        			// Read pending_count, pending_time, hypoths_count
        			is.readFully (buffer, 0, 4+8+4);
        			
        			// Begin decoding the XDR buffer
        			XdrBufferDecodingStream xdr = new XdrBufferDecodingStream (buffer);
        			xdr.beginDecoding ();
        			pldata.setPending_count (xdr.xdrDecodeInt    ());
        			pldata.setPending_time  (xdr.xdrDecodeDouble ());
        			int hypothsCount = xdr.xdrDecodeInt ();
        			xdr.endDecoding   ();
        			xdr.close ();
        			
        			// Buffer for reading the hypotheses
        			buffer = new byte[PLAYER_BUMPER_MAX_SAMPLES * (12 + 3*8 + 8)];
        			// Read the hypotheses
        			is.readFully (buffer, 0, hypothsCount * (12 + 3*8 + 8));
        			xdr = new XdrBufferDecodingStream (buffer);
        			xdr.beginDecoding ();
        			// hypotheses values
        			PlayerLocalizeHypoth[] plhs = new PlayerLocalizeHypoth[hypothsCount];
        			for (int i = 0; i < hypothsCount; i++) {
        				PlayerLocalizeHypoth plh = new PlayerLocalizeHypoth ();
        				
        				PlayerPose pp = new PlayerPose ();
        				pp.setPx (xdr.xdrDecodeFloat ());
        				pp.setPy (xdr.xdrDecodeFloat ());
        				pp.setPa (xdr.xdrDecodeFloat ());
        				
        				plh.setMean (pp);
        				double[] cov = new double[3];
        				cov[0] = xdr.xdrDecodeDouble ();
        				cov[1] = xdr.xdrDecodeDouble ();
        				cov[2] = xdr.xdrDecodeDouble ();
        				
        				plh.setCov (cov);
        				plh.setAlpha (xdr.xdrDecodeDouble ());
        				
        				plhs[i] = plh;
        			}
        			xdr.endDecoding   ();
        			xdr.close ();
        			
        			pldata.setHypoths_count (hypothsCount);
        			pldata.setHypoths       (plhs);
        			
        			readyPldata = true;
        			break;
        		}
        	}
        } catch (IOException e) {
        	throw new PlayerException 
        		("[Localize] : Error reading payload: " + 
        				e.toString(), e);
        } catch (OncRpcException e) {
        	throw new PlayerException 
        		("[Localize] : Error while XDR-decoding payload: " + 
        				e.toString(), e);
        }
    }
    
    /**
     * Request/reply: Set the robot pose estimate.
     * <br><br>
     * Set the current robot pose hypothesis by sending a 
     * PLAYER_LOCALIZE_REQ_SET_POSE request. Null response.
     * @param plsp a PlayerLocalizeSetPose structure holding the required data
     */
    public void setPose (PlayerLocalizeSetPose plsp) {
        try {
        	sendHeader (PLAYER_MSGTYPE_REQ, PLAYER_LOCALIZE_REQ_SET_POSE, 12+24);
        	XdrBufferEncodingStream xdr = new XdrBufferEncodingStream (12+24);
        	xdr.beginEncoding (null, 0);
        	xdr.xdrEncodeFloat (plsp.getMean ().getPx ());
        	xdr.xdrEncodeFloat (plsp.getMean ().getPy ());
        	xdr.xdrEncodeFloat (plsp.getMean ().getPa ());
        	xdr.xdrEncodeDouble (plsp.getCov ()[0]);
        	xdr.xdrEncodeDouble (plsp.getCov ()[1]);
        	xdr.xdrEncodeDouble (plsp.getCov ()[2]);
        	xdr.endEncoding ();
        	os.write (xdr.getXdrData (), 0, xdr.getXdrLength ());
        	xdr.close ();
        	os.flush ();
        } catch (IOException e) {
        	throw new PlayerException 
        		("[Localize] : Couldn't request PLAYER_LOCALIZE_SET_POSE_REQ: "
        				+ e.toString(), e);
        } catch (OncRpcException e) {
        	throw new PlayerException 
        		("[Localize] : Error while XDR-encoding SET_POSE request: " + 
        				e.toString(), e);
        }
    }
    
    /**
     * Request/reply: Get particles.
     * <br><br>
     * The get (usually a subset of) the current particle set (assuming that 
     * the underlying driver uses a particle filter), send a null 
     * PLAYER_LOCALIZE_REQ_GET_PARTICLES request.
     */
    public void queryParticles () {
        try {
            sendHeader (PLAYER_MSGTYPE_REQ, PLAYER_LOCALIZE_REQ_GET_PARTICLES, 0);
            os.flush ();
        } catch (IOException e) {
        	throw new PlayerException 
        		("[Localize] : Couldn't request " + 
        				"PLAYER_LOCALIZE_REQ_GET_PARTICLES: "
        				+ e.toString(), e);
        }
    }
    
    /**
     * Handle acknowledgement response messages.
     * @param header Player header
     */
    protected void handleResponse (PlayerMsgHdr header) {
        try {
            switch (header.getSubtype ()) {
                case PLAYER_LOCALIZE_REQ_SET_POSE:{
                	break;
                }
                case PLAYER_LOCALIZE_REQ_GET_PARTICLES:{
                	// Buffer for reading mean, variance, particles_count
                	byte[] buffer = new byte[12+8+4];
                	// Read mean, variance, particles_count
                	is.readFully (buffer, 0, 12+8+4);
                	
                	plgp = new PlayerLocalizeGetParticles (); 
                	PlayerPose mean = new PlayerPose ();
                	
                	// Begin decoding the XDR buffer
                	XdrBufferDecodingStream xdr = new XdrBufferDecodingStream (buffer);
                	xdr.beginDecoding ();
                	
                	mean.setPx (xdr.xdrDecodeFloat ());
                	mean.setPy (xdr.xdrDecodeFloat ());
                	mean.setPa (xdr.xdrDecodeFloat ());
                	
                	plgp.setMean (mean);
                	plgp.setVariance (xdr.xdrDecodeDouble ());
                	int particlesCount = xdr.xdrDecodeInt ();
                	
                	xdr.endDecoding   ();
                	xdr.close ();
                	
                	// Buffer for reading particle values
                	buffer = new byte[PLAYER_LOCALIZE_PARTICLES_MAX * 20];
                	// Read particle values
                	is.readFully (buffer, 0, particlesCount * 20);
                	xdr = new XdrBufferDecodingStream (buffer);
                	xdr.beginDecoding ();
                	PlayerLocalizeParticle[] plps = new PlayerLocalizeParticle[particlesCount];
                	for (int i = 0; i < particlesCount; i++) {
                		PlayerLocalizeParticle plp = new PlayerLocalizeParticle ();
                		PlayerPose pp = new PlayerPose ();
                		pp.setPx (xdr.xdrDecodeFloat ());
                		pp.setPy (xdr.xdrDecodeFloat ());
                		pp.setPa (xdr.xdrDecodeFloat ());
                		
                		plp.setPose  (pp);
                		plp.setAlpha (xdr.xdrDecodeDouble ());
                		
                		plps[i] = plp;
                	}
                	xdr.endDecoding   ();
                	xdr.close ();
                	
                	plgp.setParticles_count (particlesCount);
                	plgp.setParticles       (plps);
                	
                	readyPlgp = true;
                	break;
                }
                default:{
                	if (isDebugging)
                		System.err.println ("[Localize][Debug] : " +
                				"Unexpected response " + header.getSubtype () + 
                				" of size = " + header.getSize ());
                    break;
                }
            }
        } catch (IOException e) {
        	throw new PlayerException 
        		("[Localize] : Error reading payload: " + 
        				e.toString(), e);
        } catch (OncRpcException e) {
        	throw new PlayerException 
        		("[Localize] : Error while XDR-decoding payload: " + 
        				e.toString(), e);
        }
    }
    
    /**
     * Get the Localize data.
     * @return an object of type PlayerLocalizeData containing the requested data 
     */
    public PlayerLocalizeData getData () { return this.pldata; }
    
    /**
     * Get the particle data.
     * @return an object of type PlayerLocalizeGetParticles containing the requested data 
     */
    public PlayerLocalizeGetParticles getParticleData () { return this.plgp; }
    
    /**
     * Check if data is available.
     * @return true if ready, false if not ready 
     */
    public boolean isDataReady () {
        if (readyPldata) {
        	readyPldata = false;
            return true;
        }
        return false;
    }
    
    /**
     * Check if particle data is available.
     * @return true if ready, false if not ready 
     */
    public boolean isParticleDataReady () {
        if (readyPlgp) {
        	readyPlgp = false;
            return true;
        }
        return false;
    }
}

