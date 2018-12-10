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
package org.pageseeder.webhook.client.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * The Class Ps Webhook.
 *
 * @author Carlos Cabral
 * @since 03 Dev. 2018
 */
public class Webhook {
    
  /** The id. */
  private final String _id;
  
  /** The path. */
  private final String _name;
  
  /** The events. */
  private final List<Event> _events;
  
  /**
   * Instantiates a new webhook.
   *
   * @param id the id
   * @param name the name
   * @param events the events
   */
  public Webhook(@NonNull String id, @Nullable String name, @NonNull List<Event> events) {
    super();
    this._id = id;
    this._name = name;
    this._events = Collections.unmodifiableList(events);
  }
  
  /**
   * Gets the id.
   *
   * @return the id
   */
  public @NonNull String getId() {
    return this._id;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public @Nullable String getName() {
    return this._name;
  }
  
  /**
   * Gets the events.
   *
   * @return the events
   */
  public @NonNull List<Event> getEvents() {
    return this._events;
  }



  /**
   * The Class Builder.
   */
  public static final class Builder {
    
    /** The id. */
    private String id;
    /** the name. */
    private String name;
    
    /**  the evetns. */
    List<Event> events;
    /**
     * Instantiates a new builder.
     */
    public Builder() {
      this.events = new ArrayList<>();
    }
    
    /**
     * Id.
     *
     * @param id the id
     * @return the builder
     */
    public Builder id(String id) {
      this.id = id;
      return this;
    }

    /**
     * Name.
     *
     * @param name the name
     * @return the builder
     */
    public Builder name(String name) {
      this.name = name;
      return this;
    }
 

    /**
     * Event.
     *
     * @param event the event
     * @return the builder
     */
    public Builder event(Event event) {
      this.events.add(event);
      return this;
    }
    
    /**
     * Builds the.
     *
     * @return the webhook
     */
    public Webhook build() {
      return new Webhook(this.id, this.name, this.events);
    }
  }  
}
