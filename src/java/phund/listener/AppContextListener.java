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
import phund.entity.BoardGame;
import phund.entity.Game;
import phund.service.GameService;
import phund.service.GameServiceImp;
import phund.utils.JAXBUtils;

/**
 *
 * @author PhuNDSE63159
 */
public class AppContextListener implements ServletContextListener {

    private GameService gameService;

    public AppContextListener() {
        gameService = new GameServiceImp();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ServletContext sc = sce.getServletContext();
            List<Game> trendGames = gameService.getTrendGames(null, null);
            BoardGame boardGame = new BoardGame(trendGames);
            String xmlBoardGame = "";
            xmlBoardGame = JAXBUtils.marshal(boardGame, BoardGame.class);
            sc.setAttribute("TRENDGAMES", xmlBoardGame);
        } catch (JAXBException ex) {
            Logger.getLogger(AppContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AppContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
