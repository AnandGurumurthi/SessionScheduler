package com.scheduler;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.testng.annotations.Test;

import com.scheduler.bean.Track;
import com.scheduler.util.SchedulerUtil;
import com.scheduler.util.TrackUtil;

/**
 * Test cases for {@link SessionScheduler}
 * 
 * @author anandgurumurthi
 *
 */
public class SessionSchedulerTest {
	
	@Test
	public void testScheduleOneTrack() {
		SessionScheduler scheduler = new SessionScheduler();
		String input = "Test Session A 60min\nTest Session B 45min\n"
				+ "Test Session C 30min\nTest Session D 45min\n"
				+ "Test Session E 60min\nTest Session F 30min\n"
				+ "Test Session G 45min\nTest Session H lightning\n"
				+ "Test Session I 30min\nTest Session J 30min\n";
		
		List<Track> schedule = scheduler.schedule(input);
		
		assertThat(schedule.size()).as("Schedule should be not be empty for valid user input").isGreaterThan(0);
		
		for(Track track : schedule) {
			assertThat(SchedulerUtil.getTotalDurationOfSessions(track.getMorningSessions())).
				as("Total morning session should be equal to 240 (180 for sessions and 60 for Lunch)").isEqualTo(240);
			
			String printableVersion = TrackUtil.getPrintableSchedule(track);
			assertThat(printableVersion).as("Schedule should have Lunch at noon for valid user input").contains("12:00PM Lunch");
			assertThat(printableVersion).as("Schedule should end with Networking Event for valid user input").endsWith("Networking Event");
		}
	}
	
	@Test
	public void testScheduleMultipleTracks() {
		SessionScheduler scheduler = new SessionScheduler();
		String input = "Test Session A 60min\nTest Session B 45min\n"
				+ "Test Session C 30min\nTest Session D 45min\n"
				+ "Test Session E 60min\nTest Session F 30min\n"
				+ "Test Session G 45min\nTest Session H lightning\n"
				+ "Test Session I 30min\nTest Session J 30min\n"
				+ "Test Session AA 60min\nTest Session BB 45min\n"
				+ "Test Session CC 30min\nTest Session DD 15min\n"
				+ "Test Session EE 60min\nTest Session FF 60min\n"
				+ "Test Session GG 45min\nTest Session HH 15min\n"
				+ "Test Session II 30min\nTest Session JJ 30min\n";
		
		List<Track> schedule = scheduler.schedule(input);
		
		assertThat(schedule.size()).as("Schedule should be not be empty for valid user input").isGreaterThan(0);
		
		for(Track track : schedule) {
			assertThat(SchedulerUtil.getTotalDurationOfSessions(track.getMorningSessions())).
				as("Total morning session should be equal to 240 (180 for sessions and 60 for Lunch)").isEqualTo(240);
			
			String printableVersion = TrackUtil.getPrintableSchedule(track);
			assertThat(printableVersion).as("Schedule should have Lunch at noon for valid user input").contains("12:00PM Lunch");
			assertThat(printableVersion).as("Schedule should end with Networking Event for valid user input").endsWith("Networking Event");
		}
	}
	
	@Test
	public void testScheduleEmptyInput() {
		SessionScheduler scheduler = new SessionScheduler();
		String input = "";
		
		List<Track> schedule = scheduler.schedule(input);
		
		assertThat(schedule.size()).as("Schedule should be not be empty for valid user input").isGreaterThan(0);
		
		for(Track track : schedule) {
			assertThat(SchedulerUtil.getTotalDurationOfSessions(track.getMorningSessions())).
				as("Total morning session should be equal to 240 (180 for sessions and 60 for Lunch)").isEqualTo(240);
			
			String printableVersion = TrackUtil.getPrintableSchedule(track);
			assertThat(printableVersion).as("Schedule should have Lunch at noon for valid user input").contains("12:00PM Lunch");
			assertThat(printableVersion).as("Schedule should end with Networking Event for valid user input").endsWith("Networking Event");
		}
	}
	
	@Test
	public void testScheduleNullInput() {
		SessionScheduler scheduler = new SessionScheduler();
		List<Track> schedule = scheduler.schedule(null);
		
		assertThat(schedule.size()).as("Schedule should be not be empty for valid user input").isGreaterThan(0);
		
		for(Track track : schedule) {
			assertThat(SchedulerUtil.getTotalDurationOfSessions(track.getMorningSessions())).
				as("Total morning session should be equal to 240 (180 for sessions and 60 for Lunch)").isEqualTo(240);
			
			String printableVersion = TrackUtil.getPrintableSchedule(track);
			assertThat(printableVersion).as("Schedule should have Lunch at noon for valid user input").contains("12:00PM Lunch");
			assertThat(printableVersion).as("Schedule should end with Networking Event for valid user input").endsWith("Networking Event");
		}
	}
}
