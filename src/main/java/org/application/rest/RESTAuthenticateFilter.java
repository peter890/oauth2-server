/**
 * 
 */
package org.application.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Optional;

/**
 * @author piotrek
 *
 */
public class RESTAuthenticateFilter implements Filter {
	private List<String> excludedUrls = new ArrayList<String>();

	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		if (!isUrlExcluded(httpRequest)) {
			System.out.println("Filtering on: " + httpRequest.getRequestURI());

			Optional<String> token = Optional.fromNullable(httpRequest.getHeader("X-Auth-Token"));
			chain.doFilter(request, response);
		} else {
			chain.doFilter(request, response);
			return;
		}
	}

	public void init(final FilterConfig filterConfig) {
		String excludedUrlsString = filterConfig.getInitParameter("EXCLUDE_URLS");
		if (null != excludedUrlsString) {
			String[] urlsArray = excludedUrlsString.split(";");
			excludedUrls = Arrays.asList(urlsArray);
		}
	}

	public void destroy() {
	}

	/**
	 * Sprawdza czy podany url jest wykluczony z filtrowania
	 * 
	 * @param HttpServletRequest
	 *            do sprawdzenia.
	 * @return <true> jeœli URL jest wy³¹czony z filtrowania, w przeciwnym razie
	 *         <false>
	 */
	private boolean isUrlExcluded(final HttpServletRequest httpRequest) {
		String path = httpRequest.getRequestURI();
		path = path.replaceFirst(httpRequest.getContextPath(), "");
		boolean excluded = false;

		if (excludedUrls.contains(path)) {
			excluded = true;
		} else {
			for (String url : excludedUrls) {
				if (url.endsWith("*") && path.startsWith(url.replace("*", ""))) {
					if ((excluded = path.startsWith(url.replace("*", ""))) == true) {
						break;
					}
				}
			}
		}
		return excluded;
	}
}
