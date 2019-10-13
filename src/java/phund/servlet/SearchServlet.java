/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.servlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import phund.constant.Constant;
import phund.entity.BoardGame;
import phund.entity.TrendGame;
import phund.entity.VotedGame;
import phund.entity.WrapperTrendGame;
import phund.entity.WrapperVotedGame;
import phund.service.GameService;
import phund.service.GameServiceImp;
import phund.utils.JAXBUtils;

/**
 *
 * @author PhuNDSE63159
 */
public class SearchServlet extends HttpServlet {

    private final String TREND_PAGE = "homepage.jsp";

    private GameService gameService;

    public SearchServlet() {
        gameService = new GameServiceImp();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = TREND_PAGE;
        String offsetString = "";
        String fetchString = "";
        List<TrendGame> games = null;
        WrapperTrendGame wrapper = null;
        String xmlTrendGame = "";
        String searchValue = "";
        try {
            searchValue = request.getParameter("searchValue");
            offsetString = request.getParameter("offset");
            fetchString = request.getParameter("fetch");
            if (searchValue != null && searchValue.trim().length() > 0) {
                Integer offset = null, fetch = null;
                try {
                    offset = Integer.parseInt(offsetString);
                    fetch = Integer.parseInt(fetchString);
                } catch (NumberFormatException e) {
                }
                games = gameService.searchGames(searchValue, offset, fetch);
                wrapper = new WrapperTrendGame(games);
                xmlTrendGame = JAXBUtils.marshal(wrapper, WrapperTrendGame.class);
                request.setAttribute(Constant.TREND_GAMES, xmlTrendGame);
            }
        } catch (JAXBException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
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
