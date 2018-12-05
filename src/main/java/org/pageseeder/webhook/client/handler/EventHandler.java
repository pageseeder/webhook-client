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

import org.eclipse.jdt.annotation.NonNull;
import org.pageseeder.webhook.client.model.Event;

/**
 * The service provider interface (SPI) for event handler.
 *
 * <p>This SPI provides a pluggable interface to add handlers that can handle an specific event.
 * 
 * @author Carlos Cabral
 * @since 04 Dec. 2018
 */
public interface EventHandler {
  
  /**
   * Handle the event situation.
   * 
   * @param event
   */
  public abstract void handle(@NonNull Event event);
  
  /**
   * Check if this handler handles this event.
   * 
   * @param event
   * @return
   */
  public abstract boolean support(@NonNull Event event);
}
