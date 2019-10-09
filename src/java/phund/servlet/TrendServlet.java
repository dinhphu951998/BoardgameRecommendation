/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import phund.constant.FileConstant;
import phund.entity.BoardGame;
import phund.entity.Game;
import phund.service.GameService;
import phund.service.GameServiceImp;
import phund.utils.JAXBUtils;

/**
 *
 * @author PhuNDSE63159
 */
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

        String id = request.getParameter("userId");
        String offsetString = request.getParameter("offset");
        String fetchString = request.getParameter("fetch");
        List<Game> games = null;
        BoardGame boardGame = null;
        String xmlBoardGame = "";
        try {
            Integer offset = null;
            Integer fetch = null;
            try {
                offset = Integer.parseInt(offsetString);
                fetch = Integer.parseInt(fetchString);
            } catch (NumberFormatException ex) {
            }
            if (offset == null && fetch == null) {
                ServletContext sc = request.getServletContext();
                xmlBoardGame = (String) sc.getAttribute("TRENDGAMES");
            } else {
                games = gameService.getTrendGames(offset, fetch);
                boardGame = new BoardGame();
                boardGame.setGames(games);

                xmlBoardGame = JAXBUtils.marshal(boardGame, BoardGame.class);
            }

            request.setAttribute("TRENDGAMES", xmlBoardGame);
        } catch (JAXBException ex) {
            Logger.getLogger(TrendServlet.class.getName()).log(Level.SEVERE, null, ex);
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
