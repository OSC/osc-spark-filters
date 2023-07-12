package edu.osc.spark;

import javax.servlet.*;
import java.io.IOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

/**
 * TODO: write docs
 *
 */
public class AuthFilter implements Filter {

  private static final Logger logger = LogManager.getRootLogger();
  private static final String AUTH_KEY = System.getenv("SPARK_UI_AUTH_TOKEN");
  private static final String AUTH_KEY_COOKIE_NAME = "spark_ui_auth_token";
  

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // nothing to do
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    Cookie[] cookies = ((HttpServletRequest) request).getCookies();
    boolean authenticated = false;

    if(cookies == null) {
      sendError(response);
    }

    for (Cookie cookie : cookies) {
      String name = cookie.getName();
      String value = cookie.getValue();

      if(name.equals(AUTH_KEY_COOKIE_NAME) &&  value.equals(AUTH_KEY)) {
        authenticated = true; 
      }
    }

    if(authenticated) {
      chain.doFilter(request, response);
    } else {
      sendError(response);
    }
  }

  private void sendError(ServletResponse response) throws IOException {
    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authenticated to see this page.");
  }
}
