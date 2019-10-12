<%-- 
    Document   : search
    Created on : Sep 26, 2019, 2:38:03 PM
    Author     : PhuNDSE63159
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search page</title>
        <link rel="stylesheet" href="resources/css/homepage.css">
        <link rel="stylesheet" href="vendors/css/grid.css">
        <link rel="stylesheet" href="resources/css/dashboard.css">
    </head>

    <body>
        <header>
            <div class="row menu">
                <div class="col span-1-of-3">
                    <h2><a href="">Board game</a></h2>
                </div>

                <div class="col span-2-of-3">
                    <ul>
                        <li><a href="#" class="active">Statistic</a></li>
                        <li><a href="#">Trending</a></li>
                        <li><a href="logout">Logout</a></li>
                    </ul>

                </div>
            </div>
            <form action="crawl">
                <button class="btn" name="btnSubmit" value="Crawl">Crawl</button>
            </form>
            <form action="compute">
                <button class="btn" name="btnSubmit" value="Compute">Compute similarity</button>
            </form>
            <form action="computeTrend">
                <button class="btn" name="btnSubmit" value="Compute">Compute Trend</button>
            </form>
        </header>
    </body>

</html>