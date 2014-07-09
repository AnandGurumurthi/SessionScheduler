package com.scheduler.bean;

import java.util.LinkedList;
import java.util.List;

/**
 * Track object. Holds the track information.
 * 
 * @author anandgurumurthi
 *
 */
public class Track {
	
	private int number;
	private List<Session> morningSessions;
	private List<Session> afternoonSessions;
	
	public Track(int number) {
		this.number = number;
		this.morningSessions = new LinkedList<Session>();
		this.afternoonSessions = new LinkedList<Session>();
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public List<Session> getMorningSessions() {
		return morningSessions;
	}
	public void setMorningSessions(List<Session> morningSessions) {
		this.morningSessions = morningSessions;
	}
	public List<Session> getAfternoonSessions() {
		return afternoonSessions;
	}
	public void setAfternoonSessions(List<Session> afternoonSessions) {
		this.afternoonSessions = afternoonSessions;
	}
}
