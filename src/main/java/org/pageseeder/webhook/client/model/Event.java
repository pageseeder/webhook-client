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

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

/**
 * The Class Event.
 *
 * @author Carlos Cabral
 * @since 03 December 2018
 */
public class Event implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = -2167458294655095246L;

  /** The id. */
  private final String _id;
  
  /** The type. */
  private final EventType _type;
  
  /** The uriid. */
  private final OffsetDateTime _dateTime;
  
  /** The group. */
  private final List<EventGroup> _groups;
   
  /** The event which triggered the webhook. */
  private final EventObject _object;
  
  /**
   * Instantiates a new Event.
   *
   * @param id the id
   * @param type the type
   * @param dateTime the date time
   * @param groups the groups
   * @param object the object
   */
  public Event(@NonNull String id, @NonNull EventType type, @NonNull OffsetDateTime dateTime, 
      @NonNull List<EventGroup> groups, @NonNull EventObject object) {
    super();
    this._id = id;
    this._type = type;
    this._dateTime = dateTime;
    this._groups = Collections.unmodifiableList(groups);
    this._object = object;
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
   * Gets the type.
   *
   * @return the type
   */
  public @NonNull EventType getType() {
    return this._type;
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  public @NonNull OffsetDateTime getDateTime() {
    return this._dateTime;
  }
  
  
  /**
   * Gets the groups.
   *
   * @return the groups
   */
  public @NonNull List<EventGroup> getGroups() {
    return this._groups;
  }  
  
  /**
   * Gets the object.
   *
   * @return the object
   */
  public @NonNull EventObject getObject () {
    return this._object;
  }
  
  /**
   * The Class Builder.
   */
  public static final class Builder {
    
    /** The id. */
    private String id;
    /** the type. */
    private EventType type;
    
    /** The date time. */
    private OffsetDateTime dateTime;
    
    /** The groups. */
    private List<EventGroup> groups;
    
    /** The object. */
    private EventObject object;
  
    /**
     * Instantiates a new builder.
     */
    public Builder() {
      this.groups = new ArrayList<>();
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
     * Type.
     *
     * @param type the type
     * @return the builder
     */
    public Builder type(EventType type) {
      this.type = type;
      return this;
    }
    
    public Builder datetime(String datetime) {
      this.dateTime = OffsetDateTime.parse(datetime);
      return this;
    }
    
    /**
     * Group.
     *
     * @param group the group
     * @return the builder
     */
    public Builder group(EventGroup group) {
      this.groups.add(group);
      return this;
    }
    
    /**
     * Object.
     *
     * @param object the object
     * @return the builder
     */
    public Builder object (EventObject object) {
      this.object = object;
      return this;
    }
    
    /**
     * Builds the.
     *
     * @return the event
     */
    public Event build() {
      return new Event(this.id, this.type, this.dateTime, this.groups, this.object);
    }
  }  
}
