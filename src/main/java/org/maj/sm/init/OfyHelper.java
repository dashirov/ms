package org.maj.sm.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.maj.sm.model.Campaign;

import com.googlecode.objectify.ObjectifyService;

public class OfyHelper implements ServletContextListener {
	  public static void register() {
		System.out.println("Registering");
	    ObjectifyService.register(Campaign.class);
	  }

	  @Override
	  public void contextInitialized(ServletContextEvent event) {
	    // This will be invoked as part of a warmup request, or the first user
	    // request if no warmup request was invoked.
	    register();
	  }

	  @Override
	  public void contextDestroyed(ServletContextEvent event) {
	    // App Engine does not currently invoke this method.
	  }
	}
