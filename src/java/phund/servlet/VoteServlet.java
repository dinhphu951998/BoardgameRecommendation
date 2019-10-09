/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.servlet;

import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;
import phund.constant.Constant;
import phund.entity.User;
import phund.entity.Votes;
import phund.service.VoteService;
import phund.service.VoteServiceImp;
import phund.utils.JAXBUtils;

/**
 *
 * @author PhuNDSE63159
 */
public class VoteServlet extends HttpServlet {

    private VoteService voteService;

    public VoteServlet() {
        voteService = new VoteServiceImp();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String xml = request.getParameter("xml");
        boolean isError = true;
        try {
            if (xml != null && !xml.trim().isEmpty()) {
                Votes votes = (Votes) JAXBUtils.unmarshal(new StringReader(xml), Votes.class);
                HttpSession session = request.getSession(false);
                if (session != null) {
                    User user = (User) session.getAttribute(Constant.USER);
                    if (user != null) {
                        voteService.voteManyGame(votes.getVote(), user.getId());
                        isError = false;
                    }// end if user != null
                }//endif session != null
            }//endif validNumber == true            
        }//end if xml != null
        catch (JAXBException ex) {
            Logger.getLogger(VoteServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (isError) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
            }
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
