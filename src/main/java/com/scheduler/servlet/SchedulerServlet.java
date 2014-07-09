package com.scheduler.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scheduler.SessionScheduler;
import com.scheduler.bean.Session;
import com.scheduler.bean.Track;
import com.scheduler.util.TrackUtil;

/**
 * A simple servlet that takes user input and schedules the sessions. 
 * 
 * @author anandgurumurthi
 *
 */
public class SchedulerServlet extends HttpServlet {

	private static final long serialVersionUID = 2946767025669102550L;

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		SessionScheduler scheduler = new SessionScheduler();
		String userInput = request.getParameter("sessions");
		List<Track> tracks = scheduler.schedule(userInput);
		Map<String, Map<String, Session>> sessions = new TreeMap<String, Map<String,Session>>();
		for(Track track : tracks) {
			sessions.put("Track" + track.getNumber(), TrackUtil.getSchedule(track));
		}
		request.setAttribute("tracks", sessions);
        request.getRequestDispatcher("/").forward(request, response);
    }
}
