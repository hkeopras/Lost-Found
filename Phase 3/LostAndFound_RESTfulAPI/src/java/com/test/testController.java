/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Henri
 */
@Path("testcontroller")
public class testController {
    
    @GET
    @Path("/getData")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<testModel> getDataJSON() throws SQLException, ClassNotFoundException {
        ArrayList<testModel> tmm = new ArrayList<>();
        Connection connection = null;
        
        String username = "LFadmin";
        String password = "LFadmin";
        String query = "SELECT * FROM USERS";
        
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        connection = DriverManager.getConnection("jdbc:derby://localhost:1527/LostAndFound", username, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        
        while(resultSet.next()) {
            testModel tm = new testModel();
            tm.setId(resultSet.getInt("id"));
            tm.setEmail(resultSet.getString("email"));
            tm.setPassword(resultSet.getString("password"));
            tmm.add(tm);     
        }
        
        return tmm;
    }
    
}
