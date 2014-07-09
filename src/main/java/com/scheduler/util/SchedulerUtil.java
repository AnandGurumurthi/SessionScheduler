package com.scheduler.util;

import static com.scheduler.util.SchedulerConstants.TEST_INPUT_FILE_LOCATION;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.scheduler.bean.Session;

/**
 * Utility class to store the scheduler functions.
 * 
 * @author anandgurumurthi
 *
 */
public class SchedulerUtil {
	
	private static Random idGenerator = new Random();
	private static Pattern numberPattern = Pattern.compile(SchedulerConstants.NUMBER_REGEX_PATTERN);

	/**
	 * Prepares the list of sessions based on user input. If user input is empty, 
	 * it loads sessions from a test file. 
	 * 
	 * @param userInput
	 * @return
	 */
	public static List<Session> getSessionsList(String userInput) {
		List<String> rawInput = new LinkedList<String>();
		List<Session> sessionList = new LinkedList<Session>();
		
		// Checks if the user input is empty. If empty loads from test file
		if(StringUtils.isNotEmpty(userInput)) {
			rawInput = Arrays.asList(userInput.split("\n"));
		} else {
			rawInput = loadSessionsFromFile(TEST_INPUT_FILE_LOCATION);
		}
		
		for(String rawSession : rawInput) {
			Session session = getSession(rawSession.trim());
			if(session != null) {
				sessionList.add(session);
			}
		}
		
		return sessionList;
	}
	
	/**
	 * Converts the raw session value from user into a session object. 
	 * If time is not specified, we assume it is a 60 min session. 
	 * 
	 * @param rawSession
	 * @return
	 */
	public static Session getSession(String rawSession) {
		if(StringUtils.isEmpty(rawSession)) {
			return null;
		}
		if(rawSession.endsWith("min")) {
			Matcher matcher = numberPattern.matcher(rawSession);
			if(matcher.find()) {
				String sessionName = rawSession.substring(0, rawSession.indexOf(matcher.group()));
				int duration = Integer.parseInt(matcher.group());
				return new Session(idGenerator.nextInt(100), sessionName.trim(), duration, rawSession);
			}
		} else if(rawSession.endsWith("lightning")) {
			String sessionName = rawSession.substring(0, rawSession.indexOf("lightning"));
			return new Session(idGenerator.nextInt(100), sessionName.trim(), 5, rawSession);
		}
		
		// Defaulting it to a 60 min session
		String sessionName = rawSession;
		return new Session(idGenerator.nextInt(100), sessionName.trim(), 60, rawSession);
	}
	
	/**
	 * Utility method that prepares a map of sessions that are of the same duration. 
	 * 
	 * @param sessions
	 * @return
	 */
	public static Map<Integer, List<Session>> prepareSessionMapFromList(List<Session> sessions) {
		if(sessions == null) {
			return null;
		}
		Map<Integer, List<Session>> sessionMap = new HashMap<Integer, List<Session>>();
		for(Session session : sessions) {
			int duration = session.getDuration();
			List<Session> listOfSessions = new LinkedList<Session>();
			if(sessionMap.containsKey(duration)) {
				listOfSessions = sessionMap.get(duration);
			}
			listOfSessions.add(session);
			sessionMap.put(duration, listOfSessions);
		}
		
		return sessionMap;
	}
	
	/**
	 * Loads the sessions from the given file
	 * 
	 * @param fileName
	 * @return
	 */
	public static List<String> loadSessionsFromFile(String fileName) {
		BufferedReader br = null;
		List<String> sessions = new LinkedList<String>();
		try {
			br = new BufferedReader(new FileReader(fileName));
			String line;
			while((line = br.readLine()) != null) {
				sessions.add(line);
			}
		} catch (Exception e) {
			System.out.println("Error occurred in reading from file.");
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// NOOP
				}
			}
		}
		return sessions;
	}
	
	/**
	 * Checks if the given value is odd
	 * 
	 * @param value
	 * @return true if odd
	 */
	public static boolean isOdd(int value) {
		return value % 2 == 1;
	}
	
	/**
	 * Adds the durations of the sessions in the given list
	 * 
	 * @param sessions
	 * @return
	 */
	public static int getTotalDurationOfSessions(List<Session> sessions) {
		if(sessions == null) {
			return 0;
		}
		int totalDuration = 0;
		for(Session session : sessions) {
			totalDuration += session.getDuration();
		}
		return totalDuration;
	}
}
