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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class EventObject.
 *
 * @author Carlos Cabral
 * @since 03 Dec. 2018
 */
public class EventObject {
  
  /**  Element name. */
  private final String _element;
  
  /** The id. */
  private final String _id;
  
  /** The attributes of this element except the id. */
  private final Map<String, String> _attributes;
  
  /**
   * Instantiates a new event object.
   *
   * @param element the element
   * @param id the id
   * @param attributes the attributes
   */
  public EventObject(String element, String id, Map<String, String> attributes) {
    super();
    this._element = element;
    this._id = id;
    this._attributes = Collections.unmodifiableMap(attributes);
  }
  
  /**
   * Gets the element.
   *
   * @return the element
   */
  public String getElement() {
    return this._element;
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
   * Gets the attributes.
   *
   * @return the attributes
   */
  public Map<String, String> getAttributes() {
    return this._attributes;
  }



  /**
   * The Class Builder.
   */
  public static final class Builder {
    /** the element. */
    private String element;
    
    /** The id. */
    private String id;
        
    /** The attributes. */
    private Map<String, String> attributes;
    /**
     * Instantiates a new builder.
     */
    public Builder() {
      attributes = new HashMap<>();
    }

    /**
     * element.
     *
     * @param element the element
     * @return the builder
     */
    public Builder element(String element) {
      this.element = element;
      return this;
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
     * Attribute.
     *
     * @param name the name
     * @param value the value
     * @return the builder
     */
    public Builder attribute(String name, String value) {
      this.attributes.put(name, value);
      return this;
    }
    
    /**
     * Builds the.
     *
     * @return the EventGroup
     */
    public EventObject build() {
      return new EventObject(this.element, this.id, this.attributes);
    }
  }  
}
