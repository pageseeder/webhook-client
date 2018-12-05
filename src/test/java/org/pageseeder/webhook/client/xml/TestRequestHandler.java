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
package org.pageseeder.webhook.client.xml;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.Test;
import org.pageseeder.webhook.client.model.Event;
import org.pageseeder.webhook.client.model.EventGroup;
import org.pageseeder.webhook.client.model.EventObject;
import org.pageseeder.webhook.client.model.Webhook;
import org.xml.sax.SAXException;

/**
 * @author Carlos Cabral
 * @since 03 Dec. 2018
 */
public class TestRequestHandler {

  @Test
  public void testPing(){
    File source = new File("src/test/resources/org/pageseeder/webhook/client/xml/webhook-ping.xml");
    try {
      RequestHandler handler = new RequestHandler();
      XMLHelper.parse(handler, read(source, "utf-8"));
      
      Webhook webhook = handler.get();
      Assert.assertEquals("2", webhook.getId());
      Assert.assertEquals("sus-webhook", webhook.getName());
      Assert.assertEquals(1, webhook.getEvents().size());
      
      Event event = webhook.getEvents().get(0);
      Assert.assertNotNull(event);
      Assert.assertEquals("2.2993710555900", event.getId());
      Assert.assertEquals("webhook.ping", event.getType().getType());
      Assert.assertEquals("2018-12-03T09:56:31+11:00", event.getDateTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
      Assert.assertEquals(0, event.getGroups().size());      
      
      EventObject object = event.getObject();
      Assert.assertEquals("webhook", object.getElement());
      Assert.assertEquals("2", object.getId());
      
      Map<String, String> attributes = object.getAttributes();
      Assert.assertEquals(1, attributes.size());
      Assert.assertEquals("sus-webhook", attributes.get("name"));           
      
    } catch (IOException | SAXException | ParserConfigurationException ex) {
      Assert.fail("Exception: " + ex.getMessage());
    }
  }
  
  @Test
  public void testUriCreated(){
    File source = new File("src/test/resources/org/pageseeder/webhook/client/xml/uri-created.xml");
    try {
      RequestHandler handler = new RequestHandler();
      XMLHelper.parse(handler, read(source, "utf-8"));
      
      Webhook webhook = handler.get();
      Assert.assertEquals("2", webhook.getId());
      Assert.assertEquals("sus-webhook", webhook.getName());
      Assert.assertEquals(1, webhook.getEvents().size());
      
      Event event = webhook.getEvents().get(0);
      Assert.assertNotNull(event);
      Assert.assertEquals("55399.3166452438700", event.getId());
      Assert.assertEquals("uri.created", event.getType().getType());
      Assert.assertEquals("2018-12-03T09:59:24+11:00", event.getDateTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
      Assert.assertEquals(1, event.getGroups().size());
      
      EventGroup group = event.getGroups().get(0);
      Assert.assertNotNull(group);
      Assert.assertEquals("4", group.getId());
      Assert.assertEquals("generic-test", group.getName());
      
      EventObject object = event.getObject();
      Assert.assertEquals("uri", object.getElement());
      Assert.assertEquals("55399", object.getId());
      
      Map<String, String> attributes = object.getAttributes();
      Assert.assertEquals(1, attributes.size());
      Assert.assertEquals("/ps/generic/test/documents/test_webhook.psml", attributes.get("path"));           
      
    } catch (IOException | SAXException | ParserConfigurationException ex) {
      Assert.fail("Exception: " + ex.getMessage());
    }
  }
  
  @Test
  public void testDoubleUriModified(){
    File source = new File("src/test/resources/org/pageseeder/webhook/client/xml/double-uri-modified.xml");
    try {
      RequestHandler handler = new RequestHandler();
      XMLHelper.parse(handler, read(source, "utf-8"));
      
      Webhook webhook = handler.get();
      Assert.assertEquals("2", webhook.getId());
      Assert.assertEquals("sus-webhook", webhook.getName());
      Assert.assertEquals(2, webhook.getEvents().size());
      
      webhook.getEvents().forEach(event -> {
        String eventId = null;
        String eventType = null;
        String eventDateTime = null;
        String eventGroupId = null;
        String eventGroupName = null;
        String eventObjectElement = null;
        String eventObjectId = null;
        String eventObjectPath = null;
        
        
        if ("86.4008840876300".equals(event.getId())) {
          eventId = "86.4008840876300";
          eventType = "uri.modified";
          eventDateTime = "2018-12-03T10:13:27+11:00";
          eventGroupId = "5";
          eventGroupName = "generic-demo";
          eventObjectElement = "uri";
          eventObjectId = "86";
          eventObjectPath = "/ps/generic/demo/documents/pointer.psml";
        } else {
          eventId = "89.4008846409000";
          eventType = "uri.modified";
          eventDateTime = "2018-12-03T10:13:27+11:00"; 
          eventGroupId = "6";
          eventGroupName = "generic-demo2";
          eventObjectElement = "uri2";
          eventObjectId = "89";
          eventObjectPath = "/ps/generic/demo/documents/receiverb.psml";         
        }
        
        Assert.assertNotNull(event);
        Assert.assertEquals(eventId, event.getId());
        Assert.assertEquals(eventType, event.getType().getType());
        Assert.assertEquals(eventDateTime, event.getDateTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        Assert.assertEquals(1, event.getGroups().size());
        
        EventGroup group = event.getGroups().get(0);
        Assert.assertNotNull(group);
        Assert.assertEquals(eventGroupId, group.getId());
        Assert.assertEquals(eventGroupName, group.getName());
        
        EventObject object = event.getObject();
        Assert.assertEquals(eventObjectElement, object.getElement());
        Assert.assertEquals(eventObjectId, object.getId());
        
        Map<String, String> attributes = object.getAttributes();
        Assert.assertEquals(1, attributes.size());
        Assert.assertEquals(eventObjectPath, attributes.get("path")); 
      });
      
    } catch (IOException | SAXException | ParserConfigurationException ex) {
      Assert.fail("Exception: " + ex.getMessage());
    }
  }
  
  /**
   * Returns the content of the source file. 
   *
   * @param source the source
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private static String read(File source, String charset) throws IOException {
    Charset encoding = Charset.forName(charset);
    byte[] encoded = Files.readAllBytes(source.toPath());
    return new String(encoded, encoding);
  }
}
