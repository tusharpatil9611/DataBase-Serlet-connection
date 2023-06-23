package org.com;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
@WebServlet("/login")
public class Spring extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("index.jsp");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("in do post method");
        String username = req.getParameter("user");
        String password = req.getParameter("pass");

        if (validUser(username, password)) {
            System.out.println("valid user true");
            resp.sendRedirect("welcome.jsp");

        } else {
            System.out.println("else block valid user false");
            resp.sendRedirect("failure.jsp");
        }
    }


    private boolean validUser(String username, String password){
            try {
                Class.forName("org.mariadb.jdbc.Driver");
               Connection con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/test?angularbackend=root&password=root");

                String query = "SELECT userName,password FROM angularbackend WHERE userName=? AND password=?";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, username);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    System.out.println("rs object"+rs);
                    return true;
                }
                con.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            return false;
        }

    }
