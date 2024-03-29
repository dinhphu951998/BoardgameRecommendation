/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import phund.constant.Constant;
import phund.entity.Account;
import phund.service.AccountServiceImp;
import phund.service.AccountService;

/**
 *
 * @author PhuNDSE63159
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final String DASHBOARD = "dashboard.jsp";
    private final String LOGIN_FAIL = "error.html";

    private AccountService accountService;

    public LoginServlet() {
        accountService = new AccountServiceImp();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LOGIN_FAIL;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            if (username != null && !"".equals(username.trim())
                    && password != null && !"".equals(password.trim())) {

                Account account = accountService.login(username, password);
                if (account != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute(Constant.USER, account);
                    session.setAttribute(Constant.ROLE, "ADMIN");
                    url = DASHBOARD;
                    
                }// end if account not null

            }//end if username password not empty
        } finally {
            response.sendRedirect(url);
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
