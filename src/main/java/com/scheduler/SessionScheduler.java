package com.scheduler;

import static com.scheduler.util.SchedulerConstants.TOTAL_AFTERNOON_SESSION_TIME;
import static com.scheduler.util.SchedulerConstants.TOTAL_MORNING_SESSION_TIME;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.scheduler.bean.Session;
import com.scheduler.bean.Track;
import com.scheduler.util.SchedulerUtil;

/**
 * Session scheduler class. Processes the given user input and prepares tracks with the sessions scheduled
 * 
 * @author anandgurumurthi
 *
 */
public class SessionScheduler {
	
	/**
	 * Schedules the given sessions and returns list of tracks. 
	 * 
	 * @param userInput
	 * @return
	 */
	public List<Track> schedule(String userInput) {
		
		// Load the sessions
		List<Session> sessions = SchedulerUtil.getSessionsList(userInput);
		Map<Integer, List<Session>> sessionMap = SchedulerUtil.prepareSessionMapFromList(sessions);
		
		if(sessions == null) {
			return null;
		}
		
		Map<Integer, Track> schedule = new TreeMap<Integer, Track>();
		int count = 1;
		while(sessions.size() > 0) {
			Session session = sessions.get(0);
			int sessionDuration = session.getDuration();
			
			Track track = null;
			
			int remainingTimeInMorning = 0;
			int remainingTimeInAfternoon = 0;
			
			// Find a track that can hold this session
			for(Integer number : schedule.keySet()) {
				Track existingTrack = schedule.get(number);
				remainingTimeInMorning = TOTAL_MORNING_SESSION_TIME - SchedulerUtil.getTotalDurationOfSessions(existingTrack.getMorningSessions());
				remainingTimeInAfternoon = TOTAL_AFTERNOON_SESSION_TIME - SchedulerUtil.getTotalDurationOfSessions(existingTrack.getAfternoonSessions());
				
				// Do we have room for this session in this track? 
				if(sessionDuration <= remainingTimeInMorning || sessionDuration <= remainingTimeInAfternoon) {
					track = existingTrack;
					break;
				}
			}
			
			// If no track exist that can hold this session, create a new track
			if(track == null) {
				track = new Track(count);
				remainingTimeInMorning = TOTAL_MORNING_SESSION_TIME - SchedulerUtil.getTotalDurationOfSessions(track.getMorningSessions());
				remainingTimeInAfternoon = TOTAL_AFTERNOON_SESSION_TIME - SchedulerUtil.getTotalDurationOfSessions(track.getAfternoonSessions());
			}
			
			int remTimeInAMAfterThisSession = remainingTimeInMorning - sessionDuration;
			
			// Remove from available sessions list
			sessions.remove(session);
			List<Session> sessionsInTimeSlot = sessionMap.get(sessionDuration);
			if(sessionsInTimeSlot.size() == 1) {
				sessionMap.remove(sessionDuration);
			} else {
				sessionsInTimeSlot.remove(session);
				sessionMap.put(sessionDuration, sessionsInTimeSlot);
			}
			
			// Add session to AM or PM depending on availability
			boolean addSessionInAM = false;
			List<Session> listOfSessions = new LinkedList<Session>();
			if(remTimeInAMAfterThisSession == 0) {
				addSessionInAM = true;
			} else if(remTimeInAMAfterThisSession < 0 || sessionDuration == 5) {
				addSessionInAM = false;
			} else if(SchedulerUtil.isOdd(remTimeInAMAfterThisSession)) {
				for(Integer availableDuration : sessionMap.keySet()) {
					if(availableDuration != 5 && SchedulerUtil.isOdd(availableDuration)) {
						if(remTimeInAMAfterThisSession >= availableDuration) {
							addSessionInAM = true;
							break;
						}
					}
				}
			} else {
				addSessionInAM = true;
			}
			
			if(addSessionInAM) {
				listOfSessions = track.getMorningSessions();
				listOfSessions.add(session);
				track.setMorningSessions(listOfSessions);
			} else {
				listOfSessions = track.getAfternoonSessions();
				listOfSessions.add(session);
				track.setAfternoonSessions(listOfSessions);
			}
			
			schedule.put(count, track);
			
			// If we are done adding all sessions or if track is full, complete it by adding the lunch and networking event
			if(sessions.size() == 0 || (SchedulerUtil.getTotalDurationOfSessions(track.getMorningSessions()) == TOTAL_MORNING_SESSION_TIME && 
					TOTAL_AFTERNOON_SESSION_TIME - SchedulerUtil.getTotalDurationOfSessions(track.getAfternoonSessions()) < 60 && 
					! sessionMap.containsKey(TOTAL_AFTERNOON_SESSION_TIME - SchedulerUtil.getTotalDurationOfSessions(track.getAfternoonSessions())))) {
				track.getMorningSessions().add(SchedulerUtil.getSession("Lunch"));
				track.getAfternoonSessions().add(SchedulerUtil.getSession("Networking Event"));
				count++;
			}
		}
		return new LinkedList<Track>(schedule.values());
	}
}
