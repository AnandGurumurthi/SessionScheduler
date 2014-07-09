package com.scheduler.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.scheduler.bean.Session;
import com.scheduler.bean.Track;

/**
 * Utility for getting the schedule for given Track. 
 * 
 * @author anandgurumurthi
 *
 */
public class TrackUtil {
	
	/**
	 * Utility to process the track and returns a printable format schedule. 
	 * 
	 * @param track
	 * @return
	 */
	public static String getPrintableSchedule(Track track) {
		StringBuilder output = new StringBuilder();
		Map<String, Session> sessions = getSchedule(track);
		if(sessions != null && sessions.size() > 0) {
			output.append("Track ");
			output.append(track.getNumber());
			output.append(":");
			output.append("\n");
			for(String sessionTime : sessions.keySet()) {
				output.append(sessionTime);
				output.append(" ");
				output.append(sessions.get(sessionTime));
				output.append("\n");
			}
		}
		return output.toString().trim();
	}

	/**
	 * Processes the sessions in the track and puts them on a calendar format
	 * 
	 * @param track
	 * @return
	 */
	public static Map<String, Session> getSchedule(Track track) {
		if(track == null) {
			return null;
		}
		Map<String, Session> sessions = new LinkedHashMap<String, Session>();
		try {
			SimpleDateFormat dateFormatter = new SimpleDateFormat(SchedulerConstants.DATE_FORMAT);
			Date date = dateFormatter.parse(SchedulerConstants.TRACK_START_TIME);
			Calendar currentTime = Calendar.getInstance();
			currentTime.setTime(date);
			for(Session session : track.getMorningSessions()) {
				String sessionTime = dateFormatter.format(currentTime.getTime());
				sessions.put(sessionTime, session);
				currentTime.add(Calendar.MINUTE, session.getDuration());
			}
			for(Session session : track.getAfternoonSessions()) {
				String sessionTime = dateFormatter.format(currentTime.getTime());
				sessions.put(sessionTime, session);;
				currentTime.add(Calendar.MINUTE, session.getDuration());
			}
		
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sessions;
	}
}
