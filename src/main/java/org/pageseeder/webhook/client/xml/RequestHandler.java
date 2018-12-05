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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.pageseeder.webhook.client.model.Event;
import org.pageseeder.webhook.client.model.EventGroup;
import org.pageseeder.webhook.client.model.EventObject;
import org.pageseeder.webhook.client.model.EventType;
import org.pageseeder.webhook.client.model.Webhook;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * <p>Handler to consume the {@link Request} result stream as {@link List} of {@link Webhook}.
 * 
 *
 * @author Carlos Cabral
 * @since 04 December 2018
 */
public class RequestHandler extends DefaultHandler {
 
  /** The webhooks. */
  private List<Webhook> webhooks = new ArrayList<>();
  
  /** The is in webevent. */
  private boolean isInWebevent;
  
  /** The is in event. */
  private boolean isInEvent;
  
  /** The webhook builder. */
  private Webhook.Builder webhookBuilder;
  
  /** The event builder. */
  private Event.Builder eventBuilder;
   
  /* (non-Javadoc)
   * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
   */
  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) {
    final String element = (localName == null || localName.isEmpty()) ? qName : localName;

    switch (element) {
    case "webhook":

      if(this.isInWebevent) {
        this.processObject(element, attributes);
      } else {
        this.webhookBuilder = new Webhook.Builder(); 
        String id = attributes.getValue("id");
        String name = attributes.getValue("name");
        this.webhookBuilder.id(id);
        this.webhookBuilder.name(name);
      }
      break;
    case "webevent":
      this.isInWebevent = true;
      this.eventBuilder = new Event.Builder();
      String eventId = attributes.getValue("id");
      String eventType = attributes.getValue("type");
      String eventDateTime = attributes.getValue("datetime");
      this.eventBuilder.id(eventId);      
      this.eventBuilder.type(EventType.getEnum(eventType));
      this.eventBuilder.datetime(eventDateTime);
      break;
    case "event":
      this.isInEvent = true;
      break;
    case "group":
      if (this.isInEvent) {
        String eventGroupId = attributes.getValue("id");
        String eventGroupName = attributes.getValue("name");
        EventGroup.Builder eventGroupBuilder = new EventGroup.Builder();
        eventGroupBuilder.id(eventGroupId);
        eventGroupBuilder.name(eventGroupName);
        this.eventBuilder.group(eventGroupBuilder.build());
      }      
      break;
    default:
      if(this.isInWebevent) {
        this.processObject(element, attributes);
      }
    }
  }

  /* (non-Javadoc)
   * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  
  public void endElement (String uri, String localName, String qName) throws SAXException {
    final String element = (localName == null || localName.isEmpty()) ? qName : localName;
    switch (element) {
    case "webevents":
      webhooks.add(this.webhookBuilder.build());
      this.webhookBuilder = null;
      break;
    case "webevent":
      if (this.webhookBuilder != null) this.webhookBuilder.event(this.eventBuilder.build());
      this.isInWebevent = false;
      this.eventBuilder = null;
      break;
    case "event":
      this.isInEvent = false;
      break;
    }
  }
  
  /**
   * Process object.
   *
   * @param element the element
   * @param atts the atts
   */
  private void processObject(String element, Attributes atts) {
    EventObject.Builder eventObjectBuilder = new EventObject.Builder();            
    String eventObjectId = atts.getValue("id");
    eventObjectBuilder.id(eventObjectId);
    eventObjectBuilder.element(element);
    String attributeName = null;
    String attributeValue = null;
    for (int i = 0; i < atts.getLength(); i++) {
      attributeName = atts.getQName(i);
      if (attributeName == null || attributeName.isEmpty()) attributeName = atts.getLocalName(i);
      if (!"id".equals(attributeName.toLowerCase())) {
        attributeValue = atts.getValue(i);
        eventObjectBuilder.attribute(attributeName, attributeValue);
      }
    }
    this.eventBuilder.object(eventObjectBuilder.build());
  }
  
  /**
   * List.
   *
   * @return the list
   */
  public @NonNull List<Webhook> list() {
    return this.webhooks;
  }
  
  /**
   * Gets the.
   *
   * @return the webhook
   */
  public @Nullable Webhook get(){
    return this.webhooks.size() > 0 ? this.webhooks.get(0) : null;   
  }
}
