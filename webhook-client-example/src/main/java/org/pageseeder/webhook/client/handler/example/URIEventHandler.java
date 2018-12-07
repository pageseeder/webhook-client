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
package org.pageseeder.webhook.client.handler.example;

import java.time.format.DateTimeFormatter;

import org.eclipse.jdt.annotation.NonNull;
import org.pageseeder.webhook.client.handler.EventHandler;
import org.pageseeder.webhook.client.model.Event;
import org.pageseeder.webhook.client.model.EventObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Carlos Cabral
 * @since 03 Dec. 2018
 */
public class URIEventHandler implements EventHandler {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(EventHandler.class);
  
  @Override
  public void handle(@NonNull Event event) {
    EventObject object = event.getObject();
    LOGGER.debug("Received a URI Event {} for URI id '{}' at {}", event.getType().getType(), object.getId(), event.getDateTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
  }

  @Override
  public boolean support(@NonNull Event event) {
    boolean support = false;
    switch (event.getType()) {
    case URI_ARCHIVED:
    case URI_CREATED:
    case URI_DELETED:
    case URI_MODIFIED:
      support = true;
      break;
    default:
      support = false;
    }    
    return support;
  }
}
