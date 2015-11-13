package com.marius.dagenstegneserie;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@SuppressWarnings("serial")
public class DagensTegneserieServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException {
        resp.setContentType("text/plain");

        String pathInfo = req.getPathInfo();

        if (pathInfo.startsWith("/")) {
            pathInfo = pathInfo.substring(1);
        }


        for (Cartoon cartoon : Cartoon.values()) {
            if (pathInfo.equals(cartoon.getAppUrl())) {
                sendCartoonToResponse(new CartoonDataStoreService().getUrlFor(cartoon), resp);
                return;
            }
        }

        if (pathInfo.contains("cron")) {
            resp.getWriter().print(200);
        }  else if (pathInfo.contains("store-cartoons")) {
            new CartoonDataStoreService().storeAllCartoons();
            resp.getWriter().print(200);
        } else {
            resp.sendRedirect("index.html");
        }
    }

    private void sendCartoonToResponse(String url, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(url);
    }
}