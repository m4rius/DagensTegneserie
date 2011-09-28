package com.marius.dagens.tegneserie;
import static java.util.Calendar.DAY_OF_MONTH;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class DagensTegneserieServlet extends HttpServlet {
	
	private static Logger logger = Logger.getLogger(DagensTegneserieServlet.class.getName());
	
	
	private static String lunshPath = null;
	private static int dayLunshPathParsed = 0; 
//	private static Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("Europe/Copenhagen"));
	
	private byte[] byteArray;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		String pathInfo = req.getPathInfo();
		
		if (pathInfo.contains("lunsh")) {
			sendLunsh(resp);
		} else if (pathInfo.contains("pbs")) {
			sendPBS(resp);
		} else {
			resp.getWriter().println("Wrong path");
		}

	}

	private void sendPBS(HttpServletResponse resp) throws IOException {
		String pbsContent = readURLContent("http://www.gocomics.com/pearlsbeforeswine/");
		String pbsUrl = findPbsPath(pbsContent);
		
	}

	private String findPbsPath(String pbsContent) {
		return null;
	}

	private void sendLunsh(HttpServletResponse resp) throws IOException {
		int idag = new GregorianCalendar(TimeZone.getTimeZone("Europe/Copenhagen")).get(DAY_OF_MONTH);
		if (lunshPath == null || dayLunshPathParsed != idag) {
			logger.info("Parsing path to lunsh cartoon on dagbladet");
			String dagbladetTegneserieContent = readURLContent("http://www.dagbladet.no/tegneserie/");
			lunshPath = findLunchPath(dagbladetTegneserieContent);
			dayLunshPathParsed = idag;
		} else {
			logger.info("Using cached path :" + lunshPath);
		}
		sendImgToResponse(lunshPath, resp);
	}

	private void sendImgToResponse(String lunshUrl, HttpServletResponse resp) throws IOException {
		resp.setContentType("image/gif");
		OutputStream out = resp.getOutputStream();
		URL url = new URL(lunshUrl);
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		
	    byte[] buf = new byte[1024];
	    int count = 0;

	    ByteArrayOutputStream imgStram = new ByteArrayOutputStream();
	    
	    while ((count = in.read(buf)) >= 0) {
	        imgStram.write(buf, 0, count);
	    }
	    in.close();
	    byteArray = imgStram.toByteArray();
	    
	    out.write(byteArray);
	    
	    out.close();
		
	}

	private String findLunchPath(String dagbladetTegneserieContent) {
		String lunch = dagbladetTegneserieContent.substring(dagbladetTegneserieContent.indexOf("luncharkiv"));
		lunch = lunch.substring(0, lunch.indexOf("width"));
		String ferdigUrl = "http://www.dagbladet.no/tegneserie/" + lunch;
		return ferdigUrl;
	}
	
	private String readURLContent(String urls) throws IOException {
		URL url = new URL(urls);
		URLConnection conn = url.openConnection();
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuffer sb = new StringBuffer();
		String line;
		while ((line = rd.readLine()) != null)	{
			sb.append(line);
		}
		rd.close();
		return sb.toString();
	}
}
