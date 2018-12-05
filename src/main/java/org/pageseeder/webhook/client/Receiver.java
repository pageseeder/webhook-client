/*
 * Copyright 2018 Allette Systems (Australia)
 * http://www.allette.com.au
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.pageseeder.webhook.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jdt.annotation.Nullable;
import org.pageseeder.webhook.client.handler.EventHandler;
import org.pageseeder.webhook.client.handler.EventHandlerProvider;
import org.pageseeder.webhook.client.model.Event;
import org.pageseeder.webhook.client.model.Webhook;
import org.pageseeder.webhook.client.xml.RequestHandler;
import org.pageseeder.webhook.client.xml.XMLHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * The Class Receiver.
 *
 * @author Carlos Cabral
 * @version 03 December 2018
 */
public class Receiver extends HttpServlet {
    
  /** The Constant LOGGER. */
  private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
  
  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -5482719005441407995L; 
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doPost(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    LOGGER.debug("Processing webhook from Pageseeder.");
    
    //Validate headers Origin
    Map<String, String> headers = getHeadersInfo(req);
    if (isRequestAllowed(headers)) {
      try {
        Charset charset = getCharset(headers);
        RequestHandler handler = new RequestHandler();
        parseBody(req, charset, handler);
        process(handler.list());
        resp.setStatus(200);
      } catch (SAXException | ParserConfigurationException ex) {
        resp.setStatus(500);
      }
    } else {
      LOGGER.error("Requester not allowed.");
      resp.setStatus(403);
    }
    //Webhook requester is waiting for this header
    resp.addHeader("X-PS-Secret", headers.get("X-PS-Secret"));
  }
  
  /**
   * Process.
   *
   * @param webhooks the webhooks
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void process(List<Webhook> webhooks) throws IOException {
    if (webhooks != null) {
      for (Webhook webhook : webhooks) {
        processEvents(webhook.getEvents());
      }
    }
  }
  
  /**
   * Process events.
   *
   * @param events the events
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void processEvents(List<Event> events) throws IOException {
    if (events != null) {
      for (Event event : events) {
        processEvent(event);
      }
    }
  }
  
  /**
   * Process event.
   *
   * @param event the event
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void processEvent(Event event) throws IOException {
    EventHandlerProvider provider = EventHandlerProvider.getInstance();
    List<EventHandler> handlers = provider.handlers();
    handlers.forEach(handler -> {
      if (handler.support(event)) {
        LOGGER.debug("Event type '{}' handled by {}.", event.getType(), handler.getClass().getName());
        handler.handle(event);
      }
    });
  }
  
  /**
   * Check if the origin of this request is valid.
   *
   * @param headers the headers
   * @return true, if is host allowed
   */
  protected boolean isRequestAllowed(Map<String, String> headers) {

    return true;
  }
  
  /**
   * Gets the headers info.
   *
   * @param request the request
   * @return the headers info
   */
  protected Map<String, String> getHeadersInfo(HttpServletRequest request) {
    
    Map<String, String> map = new HashMap<String, String>();

    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String key = (String) headerNames.nextElement();
      String value = request.getHeader(key);
      LOGGER.debug("Header: {} -> {}", key, value);
      map.put(key, value);
    }

    return map;
  }

  /**
   * Gets the body.
   *
   * @param request the request
   * @param charset the charset
   * @return the body
   * @throws IOException Signals that an I/O exception has occurred.
   */
  protected String getBody(HttpServletRequest request, Charset charset) throws IOException {
    BufferedReader bufferedReader = null;
    StringWriter writer = new StringWriter();
    
    try {
      InputStream inputStream = request.getInputStream();
      if (inputStream != null) {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        char[] charBuffer = new char[128];
        int bytesRead = -1;
        while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
          writer.write(charBuffer, 0, bytesRead);
        }
      } else {
        writer.write("<empty />");
      }
    } catch (IOException ex) {
      throw ex;
    } finally {
      if (bufferedReader != null) {
        try {
          bufferedReader.close();
        } catch (IOException ex) {
          throw ex;
        }
      }
    }
    return writer.toString();
  }
  
  /**
   * Parses the body content.
   *
   * @param request the request
   * @param charset the charset
   * @param handler the handler
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws BerliozException the berlioz exception
   * @throws ParserConfigurationException 
   * @throws SAXException 
   */
  protected void parseBody(HttpServletRequest request, Charset charset, DefaultHandler handler) throws IOException, SAXException, ParserConfigurationException {
    String xmlBody = getBody(request, charset);
    LOGGER.debug("XML: " + xmlBody); //TODO REmove after some tests in production
    if (xmlBody != null) {
      XMLHelper.parse(handler, xmlBody);
    } 
  }

  /**
   * Gets the charset.
   *
   * @param headers the headers
   * @return the charset
   * @throws IOException Signals that an I/O exception has occurred.
   */
  protected Charset getCharset(Map<String, String> headers) throws IOException {
    String contentType = headers.get("Content-Type");
    return toCharset(contentType);
  }
  
  /**
   * Returns the character set used based on the value of the "content-type" header.
   *
   * <p>This method will first try the charset parameeter.
   * <p>For example, it will return "iso-8859-1" for "text/html; charset=iso-8859-1".
   *
   * <p>If there is no charset parameter, but the subtype is xml, it will assume UTF-8.
   * <p>For example, it will return "utf-8" for "application/xml".
   *
   * @param contentType The value of the "Content-Type" header.
   *
   * @return the corresponding charset instance or <code>null</code>
   *
   * @throws IllegalCharsetNameException If the given charset name is illegal
   * @throws UnsupportedCharsetException If no support for the named charset is available
   *         in this instance of the Java virtual machine
   */
  private @Nullable Charset toCharset(@Nullable String contentType) {
    if (contentType == null) return null;
    Charset charset = null;
    String lc = contentType.toLowerCase();
    int charsetParameter = lc.indexOf("charset=");
    if (charsetParameter > 0) {
      String name = lc.substring(charsetParameter).replaceAll("^charset=([a-zA-Z0-9_-]+).*", "$1");
      charset = Charset.forName(name);
    } else if (lc.indexOf("xml") > 0) {
      charset = StandardCharsets.UTF_8;
    }
    return charset;
  }
}
