package com.marius.dagenstegneserie;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DagbladetScraper {

    private static final String URL = "http://www.dagbladet.no";

    public String getImageUrl() throws IOException {

        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);
        webClient.setJavaScriptEnabled(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.waitForBackgroundJavaScript(1000);
        webClient.setRefreshHandler(new WaitingRefreshHandler());
        webClient.setCssEnabled(true);
        webClient.setJavaScriptErrorListener(new JavaScriptErrorListener() {
            @Override
            public void scriptException(HtmlPage htmlPage, ScriptException scriptException) {
                System.out.println("scriptexception");
            }

            @Override
            public void timeoutError(HtmlPage htmlPage, long allowedTime, long executionTime) {
                System.out.println("timeouterror");
            }

            @Override
            public void malformedScriptURL(HtmlPage htmlPage, String url, MalformedURLException malformedURLException) {
                System.out.println("malformedscripturl");
            }

            @Override
            public void loadScriptError(HtmlPage htmlPage, java.net.URL scriptUrl, Exception exception) {
                System.out.println("loadscripterrpr");
            }
        });
        webClient.setCssErrorHandler(new ErrorHandler() {
            @Override
            public void warning(CSSParseException exception) throws CSSException {

            }

            @Override
            public void error(CSSParseException exception) throws CSSException {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void fatalError(CSSParseException exception) throws CSSException {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        webClient.setPageCreator(new DefaultPageCreator() {
            @Override
            protected HtmlPage createHtmlPage(WebResponse webResponse, WebWindow webWindow) throws IOException {
                String contentAsString = webResponse.getContentAsString();


                int i = contentAsString.indexOf("http://track.adform.net");
                System.out.println(contentAsString);

                return super.createHtmlPage(webResponse, webWindow);    //To change body of overridden methods use File | Settings | File Templates.
            }
        });

        final HtmlPage page = webClient.getPage("http://www.dagbladet.no/tegneserie/pondus/");
        return null;
    }
}
