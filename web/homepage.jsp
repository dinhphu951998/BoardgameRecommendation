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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>

        <script src="resources/js/Utils.js"></script>
        <script src="resources/js/HomePage.js"></script>
        <script src="resources/js/Vote.js"></script>
        <script>
            var gamesString = "${TrendGames}";
            var xslString = '${GamesRender}';
            var loadMoreUrl = "webresources/games/trend";
        </script>
    </head>

    <body onload="onLoad()" onbeforeunload="onBeforeUnload()">
        <header>
            <div class="row menu">
                <div class="col span-1-of-3">
                    <h2><a href="trend">Board game</a></h2>
                </div>
                <div class="col span-2-of-3">
                    <ul>
                        <li><a href="#" class="active">Home</a></li>
                        <li><a href="getVote">Your vote</a></li>
                        <li><a href="suggest">Your match</a></li>
                        <li><a href="login.html">Login</a></li>
                    </ul>
                </div>
            </div>
            <div class="heading-main-box">
                <div class="search-box">
                    <h2>Which game will you play next?</h2>
                    <form action="search" id="search-form">

                        <input type="text" id="search-input" 
                               class="input-box" name="searchValue" placeholder="Find the game. Press enter to fast search" value="${param.searchValue}"/>
                        <!--                        <div class="submit-btn input-box" id="search-div">
                                        <span class="fa fa-search"></span>
                                        Tìm kiếm
                                    </div>-->
                        <button type="submit" class="submit-btn input-box">
                            <img src="https://img.icons8.com/dotty/50/000000/advanced-search.png" alt="">
                            Tìm kiếm
                        </button>
                    </form>
                </div>
            </div>
        </header>

        <section class="game-section">
            <div class="title">
                <h2>What is your favorite game?</h2>
                <p>Vote the game you played to get suggestion</p>
            </div>
            <%--<x:transform doc="${TrendGames}" xslt="${GamesRender}" />--%>
        </section>

        <div style="display: none" id="data">
            ${TrendGames}
        </div>

        <div style="display: none" id="render">
            ${GamesRender}
        </div>
    </body>

</html>