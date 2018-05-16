/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editServletPkg;

import entityBeanPkg.Users;
import entityBeanPkg.UsersFacadeLocal;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Henri
 */
@WebServlet(name = "EditServlet", urlPatterns = {"/EditServlet"})
public class EditServlet extends HttpServlet {
    
    @EJB
    private UsersFacadeLocal usersFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String strId;
        String strFirstName;
        String strLastName;
        String strEmail;
        String strPassword;    
        
        strId = request.getParameter("id");
        strFirstName = request.getParameter("firstName");
        strLastName = request.getParameter("lastName");
        strEmail = request.getParameter("email");
        strPassword = request.getParameter("password");
        
        int intId = Integer.parseInt(strId);

        Users users = new Users();
        
        users.setId(intId);
        users.setFirstname(strFirstName);
        users.setLastname(strLastName);
        users.setEmail(strEmail);
        users.setPassword(strPassword);

        usersFacade.edit(users);
             
        request.setAttribute("id", intId);
        request.setAttribute("firstName", strFirstName);
        request.setAttribute("lastName", strLastName);
        request.setAttribute("email", strEmail);
        request.setAttribute("password", strPassword);

        getServletContext()
            .getRequestDispatcher("/RegistrationConfirmation.jsp")
            .forward(request, response);
        
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
