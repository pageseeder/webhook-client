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
public enum EventType {
  //Admin
  WEBHOOK_PING,
  
  //Group or project events  
  URI_CREATED,
  URI_MODIFIED,
  URI_ARCHIVED,
  URI_DELETED,
  COMMENT_CREATED,
  COMMENT_MODIFIED,
  COMMENT_ARCHIVED,
  COMMENT_UNARCHIVED,
  COMMENT_DELETED,
  TASK_UPDATED,
  WORKFLOW_UPDATED,
  GROUP_DELETED,
  GROUP_ARCHIVED,
  GROUP_MODIFIED,
  GROUPPROPERTIES_MODIFIED,
  GROUPFOLDER_CREATED,
  GROUPFOLDER_DELETED,
  GROUPFODLER_MODIFIED,
  MEMBERSHIP_CREATED,
  MEMBERSHIP_MODIFIED,
  MEMBERSHIP_DELETED,

  //Project events
  GROUP_CREATED,
  PROJECT_DELETED,
  PROJECT_ARCHIVED,
  PROJECT_MODIFIED,
    
  //Server events
  MEMBER_CREATED,
  MEMBER_MODIFIED,
  MEMBER_DELETED,
  PROJECT_CREATED,
  UNKNOWN;
  
  public static EventType getEnum(String type) {
    return type != null ? EventType.valueOf(type.toUpperCase().replace('.','_')) : EventType.UNKNOWN;
  }
  
  /**
   * Gets the type.
   *
   * @return the type
   */
  public String getType() {
    return transfornType(this.name());
  }
  
  /**
   * Transforn type.
   *
   * @param type the type
   * @return the string
   */
  private static String transfornType (String type) {
    return type.toLowerCase().replace('_', '.');
  }
}
