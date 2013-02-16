package com.marius.dagenstegneserie;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;


@SuppressWarnings("serial")
public class DagensTegneserieServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(DagensTegneserieServlet.class.getName());

    public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException {
        resp.setContentType("text/plain");

        String pathinfo = req.getPathInfo();

        if (pathinfo.contains("cron")) {
            resp.getWriter().print(200);
        } else if (pathinfo.contains("lunsh")) {
            sendTegneserieToResponse(new CartoonDataStoreService().getUrlFor(Cartoon.lunsh), resp);
        } else if (pathinfo.contains("pondus")) {
            sendTegneserieToResponse(new CartoonDataStoreService().getUrlFor(Cartoon.pondus), resp);
        } else if (pathinfo.contains("nemi")) {
            sendTegneserieToResponse(new CartoonDataStoreService().getUrlFor(Cartoon.nemi), resp);
        } else if (pathinfo.contains("store-cartoons")) {
            new CartoonDataStoreService().storeAllCartoons();
            resp.getWriter().print(200);

        } else {
            resp.sendRedirect("index.html");
        }

    }

    private void sendTegneserieToResponse(String url, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(url);
    }
}