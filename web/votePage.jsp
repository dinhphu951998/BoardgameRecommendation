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
                    <link rel="stylesheet" href="resources/css/vote.css" />
                    <link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" />

                    <script src="resources/js/Utils.js"></script>
                    <script src="resources/js/HomePage.js"></script>
                    <script src="resources/js/Vote.js"></script>
                </head>

                <body onload="onLoad()" onbeforeunload="onBeforeUnload()">
                    <header>
                        <div class="row menu">
                            <div class="col span-1-of-3">
                                <h2><a href="">Board game</a></h2>
                            </div>

                            <div class="col span-2-of-3">
                                <ul>
                                    <li><a href="trend">Home</a></li>
                                    <li><a href="#" class="active">Your vote</a></li>
                                    <li><a href="suggest">Your match</a></li>
                                    <li><a href="login.html">Login</a></li>
                                </ul>

                            </div>
                        </div>
                        <div class="heading-main-box">
                            <h2>Voted games</h2>
                        </div>
                    </header>

                    <section class="game-section">

                        <x:transform doc="${VotedGames}" xslt="${GamesRender}" />

                    </section>

                </body>

                </html>