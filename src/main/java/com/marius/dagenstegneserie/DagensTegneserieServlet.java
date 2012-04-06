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

    private static final String KIND = "Tegneserier";

    public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException {
        resp.setContentType("text/plain");

        String pathinfo = req.getPathInfo();

        if (pathinfo.contains("cron")) {
            getTegneserie(Tegneserier.lunsh, resp);
            resp.getWriter().print(200);
        } else if (pathinfo.contains("lunsh")) {
            sendTegneserieToResponse(getTegneserie(Tegneserier.lunsh, resp), resp);
        } else if (pathinfo.contains("pondus")) {
            sendTegneserieToResponse(getTegneserie(Tegneserier.pondus, resp), resp);
        } else if (pathinfo.contains("nemi")) {
            sendTegneserieToResponse(getTegneserie(Tegneserier.nemi, resp), resp);
        } else {
            resp.sendRedirect("index.html");
        }

    }


    private Entity getTegneserie(Tegneserier tegneserieNavn, HttpServletResponse resp) throws IOException {
        Entity tegneserie = null; //getTegneserieFromDatastore(tegneserieNavn);
        if (tegneserie == null) {
            tegneserie = getTegneserieFromWeb(tegneserieNavn);
        }

        return tegneserie;


    }

    private void sendTegneserieToResponse(Entity tegneserie, HttpServletResponse resp) throws IOException {

//	    Text text = (Text) tegneserie.getProperty("gif");
//	    resp.getWriter().write(text.getValue());
//		resp.getWriter().close();
//		resp.setContentType("image");
        resp.sendRedirect((String)tegneserie.getProperty("content"));
    }

    private Entity getTegneserieFromWeb(Tegneserier tegneserieNavn) throws IOException {
        log.info("Henter tegneserie fra web");
        Key tegneserieKey = createKey(tegneserieNavn);
        Entity entity = new Entity(KIND, tegneserieKey);


        String pathToTegneserieImg = "";
        switch (tegneserieNavn) {
            case lunsh:
                pathToTegneserieImg = findLunchPath(readURLContent("http://www.dagbladet.no/tegneserie"));
                break;
            case pondus:
                pathToTegneserieImg = findPondusPath(readURLContent("http://www.dagbladet.no/tegneserie"));
                break;
            case nemi:
                pathToTegneserieImg = findNemiPath(readURLContent("http://www.dagbladet.no/tegneserie"));
            default:
                break;
        }

//		String realpath = runHashedPathToGetRealPath(pathToTegneserieImg);

        entity.setProperty("content", pathToTegneserieImg);
//		Text text = new Text(realpath);
//		entity.setProperty("gif", text);

        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        datastoreService.put(entity);

        return entity;
    }

    private String runHashedPathToGetRealPath(String pathToTegneserieImg) throws IOException{
        String realPath = readURLContent(pathToTegneserieImg);
        System.out.println("GIF: " + realPath);
        return realPath;
    }


    private Entity getTegneserieFromDatastore(Tegneserier tegneserie) {
        log.info("Henter tegneserie fra datastore");
        Query query = new Query(KIND, createKey(tegneserie));
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        List<Entity> tegneserieer = datastoreService.prepare(query).asList(
                FetchOptions.Builder.withLimit(1));
        if (!tegneserieer.isEmpty()) {
            return tegneserieer.get(0);
        }

        return null;
    }

    private Key createKey(Tegneserier tegneserie) {
        return KeyFactory.createKey(KIND, tegneserie + DateFormat.getDateInstance().format(new Date()));
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

    private String findLunchPath(String dagbladetTegneserieContent) {
        String lunch = dagbladetTegneserieContent.substring(dagbladetTegneserieContent.indexOf("Lund"));
        lunch = lunch.substring(lunch.indexOf("pondusarkiv"));
        lunch = lunch.substring(0, lunch.indexOf("'"));
        String ferdigUrl = "http://www.dagbladet.no/tegneserie/" + lunch;
        return ferdigUrl;
    }

    private String findPondusPath(String webpagehtml) {
        System.out.println(webpagehtml);
        String pondus = webpagehtml.substring(webpagehtml.indexOf("pondusarkiv"));
        pondus = pondus.substring(0, pondus.indexOf("'"));
        String ferdigUrl = "http://www.dagbladet.no/tegneserie/" + pondus;
        return ferdigUrl;
    }
    
    private String findNemiPath(String webpagehtml) {
        String nemi = webpagehtml.substring(webpagehtml.indexOf("Lise Myhre"));
        nemi = nemi.substring(nemi.indexOf("pondusarkiv"));
        nemi = nemi.substring(0, nemi.indexOf("'"));
        String ferdigUrl = "http://www.dagbladet.no/tegneserie/" + nemi;
        return ferdigUrl;

    }
}