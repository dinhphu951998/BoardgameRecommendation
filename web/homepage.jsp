<%-- 
    Document   : homepage.jsp
    Created on : Oct 7, 2019, 8:21:31 PM
    Author     : PhuNDSE63159
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<html lang="en">

    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="X-UA-Compatible" content="ie=edge" />
        <title>Board game recommendation</title>
        
        <link rel="stylesheet" href="vendors/css/grid.css" />
        <link rel="stylesheet" href="resources/css/homepage.css" />
        <link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" />
        
        <script src="resources/js/Utils.js"></script>
        <script src="resources/js/homepage.js"></script>
    </head>

    <body onload="onLoad()" onbeforeunload="onBeforeUnload()">
        <header>
            <div class="row menu">
                <div class="col span-1-of-3">
                    <h2><a href="">Board game</a></h2>
                </div>

                <div class="col span-2-of-3">
                    <ul>
                        <li><a href="#" class="active">Home</a></li>
                        <li><a href="#">Your match</a></li>
                        <li><a href="login.html">Login</a></li>
                    </ul>

                </div>
            </div>
            <div class="heading-main-box">
                <div class="search-box">
                    <h2>Which game will you play next?</h2>
                    <form action="search">
                        <input type="text" class="input-box" name="searchValue" placeholder="Find the game" />
                    </form>
                </div>
            </div>
        </header>

        <section class="game-section">
            <div class="title">
                <h2>What is your favorite game?</h2>
                <p>Vote the game you played to get suggestion</p>
            </div>
            
            <c:import url="WEB-INF/xsl/GameRender.xsl" var="xslDoc" />
            <x:transform doc="${TRENDGAMES}" xslt="${xslDoc}" />

        </section>

    </body>

</html>