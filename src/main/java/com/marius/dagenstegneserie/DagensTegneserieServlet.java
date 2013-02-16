package com.marius.dagenstegneserie;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;


@SuppressWarnings("serial")
public class DagensTegneserieServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException {
        resp.setContentType("text/plain");

        String pathInfo = req.getPathInfo();

        boolean cartoonUrl = false;

        for (Cartoon cartoon : Cartoon.values()) {
            if (pathInfo.contains(cartoon.getAppUrl())) {
                cartoonUrl = true;
                sendCartoonToResponse(new CartoonDataStoreService().getUrlFor(cartoon), resp);
            }
        }

        if (!cartoonUrl) {
            if (pathInfo.contains("cron")) {
                resp.getWriter().print(200);
            }  else if (pathInfo.contains("store-cartoons")) {
                new CartoonDataStoreService().storeAllCartoons();
                resp.getWriter().print(200);
            } else {
                resp.sendRedirect("index.html");
            }
        }
    }

    private void sendCartoonToResponse(String url, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(url);
    }
}