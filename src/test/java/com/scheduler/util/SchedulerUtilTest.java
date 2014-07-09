package com.scheduler.util;

import static org.fest.assertions.Assertions.assertThat;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.scheduler.bean.Session;

/**
 * Test cases for {@link SchedulerUtil}
 * 
 * @author anandgurumurthi
 *
 */
public class SchedulerUtilTest {
	
	// TEST CASES FOR getSessionsList
	
	@Test
	public void testGetSessionsList() {
		String input = "Test Session A 60min\nTest Session B 45min\nTest Session C lightning";
		List<Session> sessionsList = SchedulerUtil.getSessionsList(input);
		assertThat(sessionsList.size()).as("Number of sessions should match the input").isEqualTo(3);
	}
	
	@Test
	public void testGetSessionsListSingleSession() {
		String input = "Test Session A 60min";
		List<Session> sessionsList = SchedulerUtil.getSessionsList(input);
		assertThat(sessionsList.size()).as("Number of sessions should match the input").isEqualTo(1);
	}
	
	@Test
	public void testGetSessionsListInvalidInput() {
		String input = "Test Session A 60min\n\nTest Session C lightning";
		List<Session> sessionsList = SchedulerUtil.getSessionsList(input);
		assertThat(sessionsList.size()).as("Number of sessions should match the input").isEqualTo(2);
	}
	
	@Test
	public void testGetSessionsListNullInput() {
		String input = null;
		List<Session> sessionsList = SchedulerUtil.getSessionsList(input);
		assertThat(sessionsList.size()).as("If user input is null, we need to load test input").isGreaterThan(0);
	}
	
	// TEST CASES FOR prepareSessionMapFromList
	
	@Test
	public void testPrepareSessionMapFromList() {
		Session testSession1 = new Session(10, "Test Session A", 30, "Test Session A 30min");
		Session testSession2 = new Session(20, "Test Session B", 30, "Test Session A 30min");
		Session testSession3 = new Session(30, "Test Session C", 60, "Test Session A 60min");
		Session testSession4 = new Session(40, "Test Session D", 45, "Test Session A 45min");
		Session testSession5 = new Session(50, "Test Session E", 60, "Test Session A 60min");
		
		List<Session> sessions = new LinkedList<Session>();
		sessions.add(testSession1);
		sessions.add(testSession2);
		sessions.add(testSession3);
		sessions.add(testSession4);
		sessions.add(testSession5);
		
		Map<Integer, List<Session>> sessionMap = SchedulerUtil.prepareSessionMapFromList(sessions);
		assertThat(sessionMap.size()).as("Session map size should be greater than 0").isGreaterThan(0);
		assertThat(sessionMap.get(30).size()).as("There should be 2 sessions that are 30 minutes long").isEqualTo(2);
		assertThat(sessionMap.get(60).size()).as("There should be 2 sessions that are 60 minutes long").isEqualTo(2);
		assertThat(sessionMap.get(45).size()).as("There should be 1 session that is 45 minutes long").isEqualTo(1);
	}
	
	@Test
	public void testPrepareSessionMapFromListEmptyList() {
		List<Session> sessions = new LinkedList<Session>();
		Map<Integer, List<Session>> sessionMap = SchedulerUtil.prepareSessionMapFromList(sessions);
		assertThat(sessionMap.size()).as("Session map size should be equal to 0 for empty list").isEqualTo(0);
	}
	
	@Test
	public void testPrepareSessionMapFromListNullList() {
		List<Session> sessions = null;
		Map<Integer, List<Session>> sessionMap = SchedulerUtil.prepareSessionMapFromList(sessions);
		assertThat(sessionMap).as("Session map should be null for null list").isNull();
	}

	// TEST CASES FOR getSession
	
	@Test
	public void testGetSessionWithValidDurationInMins() {
		String input = "Test Session A 60min";
		Session session = SchedulerUtil.getSession(input);
		assertThat(session.getId()).as("Session should have a non-zero id").isGreaterThan(0);
		assertThat(session.getName()).as("Session name should be Test Session A").isEqualTo("Test Session A");
		assertThat(session.getDuration()).as("Session duration should be 60").isEqualTo(60);
		assertThat(session.getRawName()).as("Session raw name should be equal to input").isEqualTo(input);
		assertThat(session.toString()).as("Session.toString() should be equal to input").isEqualTo(input);
	}
	
	@Test
	public void testGetSessionForLightningSession() {
		String input = "Test Session A lightning";
		Session session = SchedulerUtil.getSession(input);
		assertThat(session.getId()).as("Session should have a non-zero id").isGreaterThan(0);
		assertThat(session.getName()).as("Session name should be Test Session A").isEqualTo("Test Session A");
		assertThat(session.getDuration()).as("Session duration should be 5").isEqualTo(5);
		assertThat(session.getRawName()).as("Session raw name should be equal to input").isEqualTo(input);
		assertThat(session.toString()).as("Session.toString() should be equal to input").isEqualTo(input);
	}
	
	@Test
	public void testGetSessionWithNoDurationSpecified() {
		String input = "Test Session A";
		Session session = SchedulerUtil.getSession(input);
		assertThat(session.getId()).as("Session should have a non-zero id").isGreaterThan(0);
		assertThat(session.getName()).as("Session name should be Test Session A").isEqualTo("Test Session A");
		assertThat(session.getDuration()).as("Session duration should be 60 when no time is specified").isEqualTo(60);
		assertThat(session.getRawName()).as("Session raw name should be equal to input").isEqualTo(input);
		assertThat(session.toString()).as("Session.toString() should be equal to input").isEqualTo(input);
	}
	
	@Test
	public void testGetSessionWithEmptyString() {
		String input = "";
		Session session = SchedulerUtil.getSession(input);
		assertThat(session).as("Session should be null for an empty string").isNull();
	}
	
	@Test
	public void testGetSessionWithNull() {
		String input = null;
		Session session = SchedulerUtil.getSession(input);
		assertThat(session).as("Session should be null for a null input").isNull();
	}
	
	// TEST CASES FOR loadSessionsFromFile
	
	@Test
	public void testLoadSessionsFromFile() {
		List<String> sessions = SchedulerUtil.loadSessionsFromFile("src/test/resources/testSessions.txt");
		assertThat(sessions.size()).as("List size should be equal to 5").isEqualTo(5);
	}
	
	@Test
	public void testLoadSessionsFromFileInvalidFile() {
		List<String> sessions = SchedulerUtil.loadSessionsFromFile("src/test/resources/non-existing-file.txt");
		assertThat(sessions.size()).as("List size should be equal to 0").isEqualTo(0);
	}
	
	@Test
	public void testLoadSessionsFromFileNullFile() {
		List<String> sessions = SchedulerUtil.loadSessionsFromFile(null);
		assertThat(sessions.size()).as("List size should be equal to 0").isEqualTo(0);
	}
	
	// TEST CASES FOR isOdd
	
	@Test
	public void testIsOddWithOddValue() {
		boolean isOdd = SchedulerUtil.isOdd(1);
		assertThat(isOdd).as("isOdd should be true for 1").isEqualTo(true);
	}
	
	@Test
	public void testIsOddWithEvenValue() {
		boolean isOdd = SchedulerUtil.isOdd(2);
		assertThat(isOdd).as("isOdd should be true for 2").isEqualTo(false);
	}

	// TEST CASES FOR getTotalDurationOfSessions
	
	@Test
	public void testGetTotalDurationOfTests() {
		List<Session> sessions = new LinkedList<Session>();
		sessions.add(new Session(10, "Test Session A", 30, "Test Session A 30min"));
		sessions.add(new Session(20, "Test Session B", 60, "Test Session B 60min"));
		sessions.add(new Session(30, "Test Session C", 45, "Test Session C 45min"));
		
		int totalDuration = SchedulerUtil.getTotalDurationOfSessions(sessions);
		assertThat(totalDuration).as("Total duration should be equal to 135").isEqualTo(135);
	}
	
	@Test
	public void testGetTotalDurationOfTestsEmptyList() {
		List<Session> sessions = new LinkedList<Session>();
		int totalDuration = SchedulerUtil.getTotalDurationOfSessions(sessions);
		assertThat(totalDuration).as("Total duration should be equal to 0").isEqualTo(0);
	}
	
	@Test
	public void testGetTotalDurationOfTestsNullList() {
		int totalDuration = SchedulerUtil.getTotalDurationOfSessions(null);
		assertThat(totalDuration).as("Total duration should be equal to 0").isEqualTo(0);
	}
}
