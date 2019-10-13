/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import phund.constant.Constant;
import phund.entity.User;
import phund.service.UserService;
import phund.service.UserServiceImp;

/**
 *
 * @author PhuNDSE63159
 */
public class FilterDispatcher implements Filter {

    private static final boolean debug = true;
    private final String TREND_SERVLET = "TrendServlet";
    private final String PERMISSION_DENY = "PermissionDeny.html";

    private List<String> adminPages = null;
    private List<String> userPages = null;
    private List<String> commonPages = null;

    private UserService userService;

    private FilterConfig filterConfig = null;

    public FilterDispatcher() {

    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("FilterDispatcher:DoBeforeProcessing");
        }
        ServletContext sc = filterConfig.getServletContext();
        adminPages = (List<String>) sc.getAttribute("ADMIN_PAGE");
        userPages = (List<String>) sc.getAttribute("USER_PAGE");
        commonPages = (List<String>) sc.getAttribute("COMMON_PAGE");
        if (adminPages == null || userPages == null || commonPages == null) {
            adminPages = new ArrayList<>();
            userPages = new ArrayList<>();
            commonPages = new ArrayList<>();

            commonPages.add("PermissionDeny.html");
            commonPages.add("homepage.jsp");
            commonPages.add("HomePage.js");
            commonPages.add("Utils.js");
            commonPages.add("Vote.js");

            userPages.add("SearchServlet");
            userPages.add("SuggestServlet");
            userPages.add("LoginServlet");
            userPages.add("TrendServlet");
            userPages.add("VoteServlet");
            userPages.add("GetVoteServlet");
            userPages.add("login.html");
            userPages.add("suggestpage.jsp");
            userPages.add("votePage.jsp");
            userPages.addAll(commonPages);

            adminPages.add("CrawlServlet");
            adminPages.add("LogoutServlet");
            adminPages.add("ComputeServlet");
            adminPages.add("ComputeTrendServlet");
            adminPages.add("dashboard.jsp");
            adminPages.addAll(userPages);

            sc.setAttribute("ADMIN_PAGE", adminPages);
            sc.setAttribute("USER_PAGE", userPages);
        }

    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("FilterDispatcher:DoAfterProcessing");
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        if (debug) {
            log("FilterDispatcher:doFilter()");
        }
        doBeforeProcessing(request, response);
        Throwable problem = null;

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        System.out.println(uri);
        int lastIndex = uri.lastIndexOf("/");
        String resource = uri.substring(lastIndex + 1).trim();
        System.out.println(resource + " " + resource.length());

        String url = TREND_SERVLET;
        try {
            if (!uri.contains("webresources")) {
                url = getCustomUrl(resource, url);
//                if (url != null) {
                if (checkAccessible(req, res, url)) {
                    RequestDispatcher rd = req.getRequestDispatcher(url);
                    rd.forward(request, response);
                } else {
                    res.sendRedirect(PERMISSION_DENY);
                }
//                }
            } else {
                chain.doFilter(request, response);
            }
        } catch (Throwable t) {
            problem = t;
            t.printStackTrace();
        }

        doAfterProcessing(request, response);

        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    private String getCustomUrl(String resource, String defaultURL) {
        String url = defaultURL;
        if (resource.length() > 0) {
            url = resource.substring(0, 1).toUpperCase()
                    + resource.substring(1)
                    + "Servlet";
            System.out.println(resource);
            if (resource.lastIndexOf(".jsp") > 0
                    || resource.lastIndexOf(".html") > 0
                    || resource.lastIndexOf(".css") > 0
                    || resource.lastIndexOf(".jpg") > 0
                    || resource.lastIndexOf(".js") > 0) {
                url = resource;
            }
        }
        return url;
    }

    private Cookie getCookieByName(Cookie[] cookies, String name) {
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;

    }

    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("FilterDispatcher:Initializing filter");
            }
        }
    }

    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("FilterDispatcher()");
        }
        StringBuffer sb = new StringBuffer("FilterDispatcher(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

    private void addAnonymousUser(HttpServletRequest req, HttpServletResponse res) {
        Integer id = null;
        User user = null;

        Cookie[] cookies = req.getCookies();
        Cookie cookieId = getCookieByName(cookies, Constant.COOKIE_ID);
        Cookie cookieUserToken = getCookieByName(cookies, Constant.COOKIE_USER_TOKEN);
        if (userService == null) {
            userService = new UserServiceImp();
        }
        if (cookieId != null && cookieUserToken != null) {
            try {
                String idTmp = cookieId.getValue();
                id = Integer.parseInt(idTmp);

                user = userService.getUserByIdAndUserToken(id, cookieUserToken.getValue());
            } catch (NumberFormatException e) {
            }
        }//end if cookie not null
        if (user == null) {
            try {
                user = userService.createAnonymousUser();
                id = user.getId();

                cookieId = new Cookie(Constant.COOKIE_ID, id.toString());
                cookieUserToken = new Cookie(Constant.COOKIE_USER_TOKEN, user.getUserToken());
                cookieId.setMaxAge(Constant.MAX_AGE);
                cookieUserToken.setMaxAge(Constant.MAX_AGE);

                res.addCookie(cookieId);
                res.addCookie(cookieUserToken);

            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(FilterDispatcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//end if user == null
        HttpSession session = req.getSession();
        session.setAttribute(Constant.USER, user);
        session.setAttribute(Constant.ROLE, "USER");
    }

    private boolean checkAccessible(HttpServletRequest req, HttpServletResponse res, String url) throws IOException {
        boolean isValid = false;
        HttpSession session = req.getSession(false);
        if (session == null) {
            addAnonymousUser(req, res);
            session = req.getSession(false);
        }
        String role = (String) session.getAttribute(Constant.ROLE);
        if (role != null) {

            if (role.equals("ADMIN") && adminPages.contains(url)) {
                isValid = true;
            } else if (role.equals("USER") && userPages.contains(url)) {
                isValid = true;
            }

            if (url.lastIndexOf(".css") > 0
                    || url.lastIndexOf(".jpg") > 0) {
                isValid = true;
            }
        }

//        return isValid;
        return true;
    }

}
