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
package org.pageseeder.webhook.client.example.generator;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map.Entry;

import org.pageseeder.berlioz.BerliozException;
import org.pageseeder.berlioz.content.ContentGenerator;
import org.pageseeder.berlioz.content.ContentRequest;
import org.pageseeder.webhook.client.example.service.EventService;
import org.pageseeder.webhook.client.model.Event;
import org.pageseeder.webhook.client.model.EventGroup;
import org.pageseeder.webhook.client.model.EventObject;
import org.pageseeder.xmlwriter.XMLWriter;

/**
 * @author Carlos Cabral
 * @since 07 Dec. 2018
 */
public class ListEventsReceived implements ContentGenerator {
  

  @Override
  public void process(ContentRequest req, XMLWriter xml) throws BerliozException, IOException {
    EventService eventService = new EventService();
    List<Event> events = eventService.list();
    xml.openElement("events");
    for (Event event:events) {
      xml.openElement("event");
      xml.attribute("id", event.getId());
      xml.attribute("type", event.getType().getType());
      xml.attribute("date-time", event.getDateTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
      xml.openElement("groups");
      for (EventGroup group:event.getGroups()) {
        xml.openElement("group");
        xml.attribute("id", group.getId());
        xml.attribute("name", group.getName());
        xml.closeElement();//group
      }
      xml.closeElement();//groups
      
      EventObject object = event.getObject();
      xml.openElement("object");
      xml.attribute("id", object.getId());
      xml.attribute("element", object.getElement());
      for (Entry<String, String> attribute:object.getAttributes().entrySet()) {
        xml.attribute(attribute.getKey(), attribute.getValue());  
        xml.closeElement();//object
      }      
      xml.closeElement();//event
    }
    xml.closeElement();//events
  }

}
