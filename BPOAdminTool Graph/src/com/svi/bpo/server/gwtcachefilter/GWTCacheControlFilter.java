package com.svi.bpo.server.gwtcachefilter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Forces GWT resources to cache for a very long time.
 * <p>
 * GWT compiled JavaScript and ImageBundles can be cached indefinitely by a
 * browser and/or an edge proxy, as they never contain user-specific data and
 * are named by a unique checksum. If their content is ever modified then the
 * URL changes, so user agents would request a different resource. We force
 * these resources to have very long expiration times.
 * <p>
 * To use, add the following block to your <code>web.xml</code>:
 *
 * <pre>
 * &lt;filter&gt;
 *     &lt;filter-name&gt;CacheControl&lt;/filter-name&gt;
 *     &lt;filter-class&gt;com.google.gwtexpui.server.CacheControlFilter&lt;/filter-class&gt;
 *   &lt;/filter&gt;
 *   &lt;filter-mapping&gt;
 *     &lt;filter-name&gt;CacheControl&lt;/filter-name&gt;
 *     &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 *   &lt;/filter-mapping&gt;
 * </pre>
 */
public class GWTCacheControlFilter implements Filter {
  @Override
public void init(final FilterConfig config) {
  }

  @Override
public void destroy() {
  }

  @Override
public void doFilter(final ServletRequest sreq, final ServletResponse srsp,
      final FilterChain chain) throws IOException, ServletException {
    final HttpServletRequest req = (HttpServletRequest) sreq;
    final HttpServletResponse rsp = (HttpServletResponse) srsp;
    final String pathInfo = pathInfo(req);

    if (isCachable(pathInfo, req)) {
      final long now = System.currentTimeMillis();
      rsp.setHeader("Cache-Control", "max-age=31536000,public");
      rsp.setDateHeader("Expires", now + 604800000L);
      rsp.setDateHeader("Date", now);

    } else if (nocache(pathInfo)) {
      rsp.setHeader("Expires", "Fri, 01 Jan 1980 00:00:00 GMT");
      rsp.setHeader("Pragma", "no-cache");
      rsp.setHeader("Cache-Control", "no-cache, must-revalidate");
      rsp.setDateHeader("Date", System.currentTimeMillis());
    }

    chain.doFilter(req, rsp);
  }

  private static boolean isCachable(final String pathInfo,
      final HttpServletRequest req) {
	  
    if (pathInfo.endsWith(".html")) {
    	System.out.println("HTML!!!");
      return true;
    } else if (pathInfo.endsWith(".gif")) {
    	System.out.println("GIF!!!");
      return true;
    } else if (pathInfo.endsWith(".png")) {
    	System.out.println("PNG!!!");
      return true;
    } else if (pathInfo.endsWith(".jpg")) {
    	System.out.println("JPG!!!");
      return true;
    } else if (pathInfo.endsWith(".jpeg")) {
    	System.out.println("JPEG!!!");
      return true;
    } else if (pathInfo.endsWith(".css")) {
    	System.out.println("CSS!!!");
      return true;
    } else if (pathInfo.endsWith(".jar")) {
    	System.out.println("JAR!!!");
      return true;
    } else if (pathInfo.endsWith(".cache.swf")) {
    	System.out.println(".CACHE.SWF!!!");
      return true;
    } else if (pathInfo.endsWith(".nocache.js")) {
      final String v = req.getParameter("content");
      System.out.println("NOCACHE.JS");
      return v != null && v.length() > 20;
    }
    return false;
  }

  private static boolean nocache(final String pathInfo) {
    if (pathInfo.endsWith(".nocache.js")) {
      return true;
    }
    return false;
  }

  private static String pathInfo(final HttpServletRequest req) {
    final String uri = req.getRequestURI();
    final String ctx = req.getContextPath();
    return uri.startsWith(ctx) ? uri.substring(ctx.length()) : uri;
  }
}

