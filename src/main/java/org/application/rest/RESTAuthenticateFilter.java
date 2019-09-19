/**
 *
 */
package org.application.rest;

import org.application.filters.UrlsExcludeBaseFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author piotrek
 */
public class RESTAuthenticateFilter extends UrlsExcludeBaseFilter {
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (!isUrlExcluded(httpRequest)) {
            System.out.println("Filtering on: " + httpRequest.getRequestURI());

            final Optional<String> token = Optional.ofNullable(httpRequest.getHeader("X-Auth-Token"));
            chain.doFilter(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}