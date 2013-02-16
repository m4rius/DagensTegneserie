package com.marius.dagenstegneserie;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;


@SuppressWarnings("serial")
public class DagensTegneserieServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(DagensTegneserieServlet.class.getName());

    public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException {
        resp.setContentType("text/plain");

        String pathinfo = req.getPathInfo();

        if (pathinfo.contains("cron")) {
            resp.getWriter().print(200);
        } else if (pathinfo.contains("lunsh")) {
            sendTegneserieToResponse(getTegneserieFromWeb(Tegneserier.lunsh), resp);
        } else if (pathinfo.contains("pondus")) {
            sendTegneserieToResponse(getTegneserieFromWeb(Tegneserier.pondus), resp);
        } else if (pathinfo.contains("nemi")) {
            sendTegneserieToResponse(getTegneserieFromWeb(Tegneserier.nemi), resp);
        } else {
            resp.sendRedirect("index.html");
        }

    }

    private void sendTegneserieToResponse(String tegneserie, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(tegneserie);
    }

    private String getTegneserieFromWeb(Tegneserier tegneserieNavn) throws IOException {
        log.info("Henter tegneserie fra web");

        String pathToTegneserieImg = "";
        switch (tegneserieNavn) {
            case lunsh:
                pathToTegneserieImg = findTegneserieFromDbHtml(readURLContent("http://www.dagbladet.no/tegneserie/lunch/"));
                break;
            case pondus:
                pathToTegneserieImg = findTegneserieFromDbHtml(readURLContent("http://www.dagbladet.no/tegneserie/pondus/"));
                break;
            case nemi:
                pathToTegneserieImg = findTegneserieFromDbHtml(readURLContent("http://www.dagbladet.no/tegneserie/nemi/"));
            default:
                break;
        }

        return pathToTegneserieImg;
    }

    private String readURLContent(String urls) throws IOException {
        URL url = new URL(urls);
        URLConnection conn = url.openConnection();
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        return sb.toString();
    }

    private String findTegneserieFromDbHtml(String webpagehtml) {
        String gifUrl = webpagehtml.substring(webpagehtml.indexOf("<img class=\"tegneserie\""));
        gifUrl = gifUrl.substring(gifUrl.indexOf("src=\"")+5);
        gifUrl = gifUrl.substring(0, gifUrl.indexOf("\""));
        log.info(String.format("Found url %s", gifUrl));
        return (gifUrl);
    }

}