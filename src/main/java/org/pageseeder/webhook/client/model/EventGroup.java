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

/**
 * @author Carlos Cabral
 * @since 03 Dec. 2018
 */
public class EventGroup {
  /** The id. */
  private final String _id;
  
  /** The path. */
  private final String _name;
  
  /**
   * 
   *
   * @param id the id
   * @param name the name
   */
  public EventGroup(String id, String name) {
    super();
    this._id = id;
    this._name = name;
  }
  
  /**
   * Gets the id.
   *
   * @return the id
   */
  public String getId() {
    return this._id;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return this._name;
  }

  /**
   * The Class Builder.
   */
  public static final class Builder {
    
    /** The id. */
    private String id;
    /** the name. */
    private String name;
    /**
     * Instantiates a new builder.
     */
    public Builder() {
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
     * Builds the.
     *
     * @return the EventGroup
     */
    public EventGroup build() {
      return new EventGroup(this.id, this.name);
    }
  }  
}
