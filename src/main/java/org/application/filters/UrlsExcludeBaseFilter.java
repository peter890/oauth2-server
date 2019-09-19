package org.application.filters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class UrlsExcludeBaseFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(UrlsExcludeBaseFilter.class);
    private List<String> excludedUrls;

    protected UrlsExcludeBaseFilter() {
        this.excludedUrls = new ArrayList<>();
    }

    public void init(final FilterConfig filterConfig) throws ServletException {
        logger.debug("init");
        final String excludedUrlsString = filterConfig.getInitParameter("EXCLUDE_URLS");

        if (null != excludedUrlsString) {
            final String[] urlsArray = excludedUrlsString.split(";");
            this.excludedUrls = Arrays.asList(urlsArray);
        }
    }

    public void destroy() {

    }

    /**
     * Sprawdza czy podany url jest wykluczony z filtrowania
     *
     * @param httpRequest do sprawdzenia.
     * @return <true> jeśli URL jest wyłączony z filtrowania, w przeciwnym razie
     * <false>
     */
    protected boolean isUrlExcluded(final HttpServletRequest httpRequest) {
        final String path = httpRequest.getRequestURI().replaceFirst(httpRequest.getContextPath(), "");
        boolean excluded = false;

        if (this.excludedUrls.contains(path)) {
            excluded = true;
        } else {
            for (final String url : this.excludedUrls) {
                if (url.endsWith("*") && path.startsWith(url.replace("*", ""))) {
                    if ((excluded = path.startsWith(url.replace("*", "")))) {
                        break;
                    }
                }
            }
        }
        return excluded;
    }
}