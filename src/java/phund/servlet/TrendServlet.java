/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.servlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import phund.constant.Constant;
import phund.entity.TrendGame;
import phund.entity.WrapperTrendGame;
import phund.service.GameService;
import phund.service.GameServiceImp;
import phund.utils.DateUtils;
import phund.utils.JAXBUtils;

/**
 *
 * @author PhuNDSE63159
 */
@WebServlet(name = "TrendServlet", urlPatterns = {"/TrendServlet"})
public class TrendServlet extends HttpServlet {

    private final String HOMEPAGE = "homepage.jsp";

    private GameService gameService;

    public TrendServlet() {
        gameService = new GameServiceImp();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = HOMEPAGE;
        List<TrendGame> games = null;
        WrapperTrendGame wrapperTrendGame = null;
        String xmlTrendGame = "";
        try {
            String offsetString = request.getParameter("offset");
            String fetchString = request.getParameter("fetch");

            Integer offset = null, fetch = null;
            try {
                offset = Integer.parseInt(offsetString);
                fetch = Integer.parseInt(fetchString);
            } catch (NumberFormatException ex) {
            }
            
            if (offset == null && fetch == null) {
                ServletContext sc = request.getServletContext();
                checkTrendGames(sc);
            } else {
                games = gameService.getTrendGames(offset, fetch);
                wrapperTrendGame = new WrapperTrendGame(games);
                xmlTrendGame = JAXBUtils.marshal(wrapperTrendGame, WrapperTrendGame.class);
                request.setAttribute(Constant.TREND_GAMES, xmlTrendGame);
            }
        } catch (JAXBException ex) {
            log("JAXBException_TrendServlet: " + ex.getMessage() + " " + Calendar.getInstance().getTime());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    private void checkTrendGames(ServletContext sc) throws JAXBException, FileNotFoundException {
        long outdatedTime = (long) sc.getAttribute(Constant.OUTDATED_TIME);
        if (DateUtils.isBeforeNow(outdatedTime)) {
            String xmlTrendGame = reload();
            long nextOutdatedTime = DateUtils.getMillisFromNow(Constant.MAX_TIME_TREND_GAME, Constant.TIME_UNIT);
            sc.setAttribute(Constant.TREND_GAMES, xmlTrendGame);
            sc.setAttribute(Constant.OUTDATED_TIME, nextOutdatedTime);
        } 
    }

    private String reload() throws JAXBException, FileNotFoundException {
        List<TrendGame> games = gameService.getTrendGames(null, null);
        WrapperTrendGame wrapperTrendGame = new WrapperTrendGame(games);
        String xmlTrendGame = JAXBUtils.marshal(wrapperTrendGame, WrapperTrendGame.class);
        return xmlTrendGame;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
