/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.servlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;
import phund.constant.Constant;
import phund.entity.User;
import phund.entity.VotedGame;
import phund.entity.WrapperVotedGame;
import phund.service.GameService;
import phund.service.GameServiceImp;
import phund.service.VoteService;
import phund.service.VoteServiceImp;
import phund.utils.JAXBUtils;

/**
 *
 * @author PhuNDSE63159
 */
@WebServlet(name = "GetVoteServlet", urlPatterns = {"/GetVoteServlet"})
public class GetVoteServlet extends HttpServlet {

    private final String VOTE_PAGE = "votePage.jsp";
    private VoteService voteService;
    private GameService gameService;

    public GetVoteServlet() {
        voteService = new VoteServiceImp();
        gameService = new GameServiceImp();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = VOTE_PAGE;
        HttpSession session = request.getSession(false);
        String offsetString = request.getParameter("offset");
        String fetchString = request.getParameter("fetch");
        try {
            if (session != null) {
                User user = (User) session.getAttribute(Constant.USER);
                Integer offset = null, fetch = null;
                try {
                    offset = Integer.parseInt(offsetString);
                    fetch = Integer.parseInt(fetchString);
                } catch (NumberFormatException e) {
                }
                List<VotedGame> games = gameService.getVotedGames(user.getId(), offset, fetch);
                WrapperVotedGame wapper = new WrapperVotedGame(games);
                String result = JAXBUtils.marshal(wapper, WrapperVotedGame.class);
                session.setAttribute(Constant.VOTED_GAMES, result);
            }
        } catch (JAXBException ex) {
            Logger.getLogger(GetVoteServlet.class.getName()).log(Level.SEVERE, null, ex);
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
