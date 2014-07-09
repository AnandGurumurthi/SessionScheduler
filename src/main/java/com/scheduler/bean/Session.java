package com.scheduler.bean;

/**
 * Session bean. Stores the session data. 
 * 
 * @author anandgurumurthi
 *
 */
public class Session {

	private int id;
	private String name;
	private int duration;
	private String rawName;
	
	public Session(int id, String name, int duration, String rawName) {
		this.id = id;
		this.name = name;
		this.duration = duration;
		this.rawName = rawName;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getRawName() {
		return rawName;
	}
	public void setRawName(String rawName) {
		this.rawName = rawName;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.rawName;
	}
}
