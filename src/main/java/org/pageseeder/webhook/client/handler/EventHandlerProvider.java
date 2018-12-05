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
package org.pageseeder.webhook.client.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * The Class EventHandlerProvider.
 *
 * @author Carlos Cabral
 * @since 05 Dec. 2018
 */
public class EventHandlerProvider {
  
  /** The provider. */
  private static EventHandlerProvider PROVIDER;
  
  /** The loader. */
  private ServiceLoader<EventHandler> loader;
  
  /**
   * Instantiates a new event handler provider.
   */
  private EventHandlerProvider(){
    loader = ServiceLoader.load(EventHandler.class);
  } 
  
  /**
   * Gets the single instance of EventHandlerProvider.
   *
   * @return single instance of EventHandlerProvider
   */
  public static synchronized EventHandlerProvider getInstance(){
    if (PROVIDER == null) {
      PROVIDER = new EventHandlerProvider();
    }
    return PROVIDER;
  }
  
  /**
   * Handlers.
   *
   * @return the list
   */
  public List<EventHandler> handlers() {
    List<EventHandler> handlers = new ArrayList<>();
    loader.iterator().forEachRemaining(handlers::add);
    return handlers;
  }
}
