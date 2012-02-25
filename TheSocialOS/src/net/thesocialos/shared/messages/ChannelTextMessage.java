/** 
 * Copyright 2010 Daniel Guermeur and Amy Unruh
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *   See http://connectrapp.appspot.com/ for a demo, and links to more information 
 *   about this app and the book that it accompanies.
 */
package net.thesocialos.shared.messages;

import net.thesocialos.shared.messages.Message;

@SuppressWarnings("serial") public class ChannelTextMessage extends Message {

  private String msg;

  private ChannelTextMessage() {
    super(Type.TEXT_MESSAGE);
  }

  public ChannelTextMessage(String msg) {
    this();
    this.msg = msg;
  }

  public String get() {
    return msg;
  }
}
