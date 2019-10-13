/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.constant;

import java.time.temporal.ChronoUnit;

/**
 *
 * @author PhuNDSE63159
 */
public class Constant {
    //servlet context
//    public static final String SUGGEST_GAME_RENDER = "SuggestedGamesRender";
    public static final String GAME_RENDER = "GamesRender";
    public static final String TREND_GAMES = "TrendGames";
    public static final String OUTDATED_TIME = "OutdatedTime";
    

    //cookie
    public static final String COOKIE_ID = "Id";
    public static final String COOKIE_USER_TOKEN = "UserToken";
    //session
    public static final String USER = "User";
    public static final String ROLE = "Role";
    public static final String SUGGESTED_GAMES = "SuggestedGames";
    public static final String VOTED_GAMES = "VotedGames";

    public static final int MAX_AGE = 60 * 60 * 24 * 365 * 10;
    
    public static final int MAX_TIME_TREND_GAME = 1;
    public static final ChronoUnit TIME_UNIT = ChronoUnit.HOURS;
}
