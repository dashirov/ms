/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.maj.sm;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maj.sm.model.Campaign;

import com.google.appengine.api.datastore.QueryResultIterable;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class HelloAppEngine extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
      
    response.setContentType("text/plain");
    response.getWriter().println("Hello App Engine!");
    
    List<Campaign> campaigns = ofy().load().type(Campaign.class).list();
    
    for (Campaign c : campaigns){
    	response.getWriter().println(c.toString());
    }
   
    
    Campaign campaign = new Campaign(UUID.randomUUID().toString(),"Test Desc");
    campaign.addPrice(new Date(), 0.25D);
    ofy().save().entity(campaign).now();

  }
}
