package com.scheduler;
import java.util.List;

import com.scheduler.bean.Track;
import com.scheduler.util.TrackUtil;

/**
 * A simple command line executor for the Session Scheduler. 
 * 
 * @author anandgurumurthi
 *
 */
public class CommandLineScheduler {

	public static void main(String args[]) {
		String userInput = args.length > 0 ? args[0] : "";
		SessionScheduler scheduler = new SessionScheduler();
		List<Track> tracks = scheduler.schedule(userInput);
		for(Track track : tracks) {
			System.out.println(TrackUtil.getPrintableSchedule(track));
		}
	}
}
