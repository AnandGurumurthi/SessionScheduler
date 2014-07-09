package com.scheduler.util;

import static org.fest.assertions.Assertions.assertThat;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.scheduler.bean.Session;
import com.scheduler.bean.Track;

/**
 * Test cases for {@link TrackUtil}
 * 
 * @author anandgurumurthi
 *
 */
public class TrackUtilTest {
	
	// TEST CASES FOR getPrintableSchedule
	
	@Test
	public void testGetPrintableSchedule() {
		List<Session> morningSessions = new LinkedList<Session>();
		List<Session> afternoonSessions = new LinkedList<Session>();
		morningSessions.add(new Session(10, "Test Session A", 30, "Test Session A 30min"));
		morningSessions.add(new Session(20, "Test Session B", 60, "Test Session B 60min"));
		afternoonSessions.add(new Session(30, "Test Session C", 45, "Test Session C 45min"));
		
		Track track = new Track(1);
		track.setMorningSessions(morningSessions);
		track.setAfternoonSessions(afternoonSessions);
		
		String schedule = TrackUtil.getPrintableSchedule(track);
		assertThat(schedule).as("Schedule should not be empty for valid track").isNotEmpty();
	}
	
	@Test
	public void testGetPrintableScheduleEmptyTrack() {
		Track track = new Track(1);
		String schedule = TrackUtil.getPrintableSchedule(track);
		assertThat(schedule).as("Schedule should be empty for empty track").isEmpty();
	}
	
	@Test
	public void testGetPrintableScheduleNullTrack() {
		String schedule = TrackUtil.getPrintableSchedule(null);
		assertThat(schedule).as("Schedule should be empty for null track").isEmpty();
	}

	// TEST CASES FOR getSchedule
	
	@Test
	public void testGetSchedule() {
		List<Session> morningSessions = new LinkedList<Session>();
		List<Session> afternoonSessions = new LinkedList<Session>();
		morningSessions.add(new Session(10, "Test Session A", 30, "Test Session A 30min"));
		morningSessions.add(new Session(20, "Test Session B", 60, "Test Session B 60min"));
		afternoonSessions.add(new Session(30, "Test Session C", 45, "Test Session C 45min"));
		
		Track track = new Track(1);
		track.setMorningSessions(morningSessions);
		track.setAfternoonSessions(afternoonSessions);
		
		Map<String, Session> schedule = TrackUtil.getSchedule(track);
		assertThat(schedule.size()).as("Schedule should contain the same number of sessions").isEqualTo(3);
	}
	
	@Test
	public void testGetScheduleEmptyTrack() {
		Track track = new Track(1);
		Map<String, Session> schedule = TrackUtil.getSchedule(track);
		assertThat(schedule.size()).as("Schedule should contain 0 items for null track").isEqualTo(0);
	}
	
	@Test
	public void testGetScheduleNullTrack() {
		Map<String, Session> schedule = TrackUtil.getSchedule(null);
		assertThat(schedule).as("Schedule should be null for null track").isNull();
	}
}
