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
package org.pageseeder.webhook.client.example.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.pageseeder.webhook.client.model.Event;

/**
 * The Class EventService.
 *
 * @author Carlos Cabral
 * @since 07 Dec. 2018
 */
public class EventService {
  /** The event cache. */
  private static final Map<String, Event> EVENT_CACHE = new LinkedHashMap<>();
  
  /**
   * Instantiates a new event service.
   */
  public EventService() {
    super();
  }

  /**
   * List.
   *
   * @return the list
   */
  public List<Event> list() {
    List<Event> events = new ArrayList<>();
    EVENT_CACHE.forEach((k, event) -> {
      events.add(event);
    });
    return events;
  }
  
  /**
   * Adds the.
   *
   * @param event the event
   */
  public void add(Event event) {
    String key = createKey(event);
    EVENT_CACHE.put(key, event);
  }
  
  /**
   * Creates the key.
   *
   * @param event the event
   * @return the string
   */
  private String createKey(Event event) {
    return event.getType() + event.getDateTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
  }
}
