/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.listener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.bind.JAXBException;
import phund.constant.Constant;
import phund.constant.FileConstant;
import phund.entity.BoardGame;
import phund.entity.Game;
import phund.entity.TrendGame;
import phund.entity.WrapperTrendGame;
import phund.service.GameService;
import phund.service.GameServiceImp;
import phund.utils.FileUtils;
import phund.utils.JAXBUtils;

/**
 *
 * @author PhuNDSE63159
 */
public class AppContextListener implements ServletContextListener {

    private String realPath = null;
    private GameService gameService;

    public AppContextListener() {
        gameService = new GameServiceImp();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        realPath = sce.getServletContext().getRealPath("/");
        try {
            ServletContext sc = sce.getServletContext();

            initRenderStyleSheet(sc);
            initTrendGames(sc);

        } catch (JAXBException ex) {
            Logger.getLogger(AppContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AppContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initRenderStyleSheet(ServletContext sc) {
        String trendRender = realPath + FileConstant.GAME_RENDER;
//        String suggestRender = realPath + FileConstant.SUGGEST_GAME_RENDER;

        String gameRenderXsl = FileUtils.read(trendRender);
//        String suggestXsl = FileUtils.read(suggestRender);

        sc.setAttribute(Constant.GAME_RENDER, gameRenderXsl.trim());
//        sc.setAttribute(Constant.SUGGEST_GAME_RENDER, suggestXsl);
    }

    private void initTrendGames(ServletContext sc) throws JAXBException, FileNotFoundException {
        List<TrendGame> trendGames = gameService.getTrendGames(null, null);
        WrapperTrendGame wrapper = new WrapperTrendGame(trendGames);
        String xmlTrend = "";
        xmlTrend = JAXBUtils.marshal(wrapper, WrapperTrendGame.class);
//        xmlBoardGame = xmlBoardGame.replace("'", "\"");
        sc.setAttribute(Constant.TREND_GAMES, xmlTrend);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
