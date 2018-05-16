<%-- 
    Document   : editForm
    Created on : 16 mai 2018, 11:23:49
    Author     : Henri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
    
    <% 
        String id, firstName, lastName, email, password;
  
        id = request.getParameter("id");
        firstName = request.getParameter("firstName");
        lastName = request.getParameter("lastName");
        email = request.getParameter("email");
        password = request.getParameter("password");
        
        if (firstName == null) firstName = "";
        if (lastName == null) lastName  = ""; 
        if (email == null) email = "";
        if (password == null) password = ""; 

   %>

    <form action="/LostFound-war/EditServlet" method="get">
        <table border="0" align="left">
            <tr>
                <td>Id </td>
                <td><input type="number" size="32" name="id" value="<%=id%>"/></td>
            </tr>
            <tr>
                <td>First Name </td>
                <td><input type="text" size="32" name="firstName" value="<%=firstName%>"/></td>
            </tr>
            <tr>
                <td>Last Name </td>
                <td><input type="text" size="32" name="lastName" value="<%=lastName%>"/></td>
            </tr>
            <tr>
                <td>Email </td>
                <td><input type="text" size="32" name="email" value="<%=email%>"/></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="text" size="32" name="password" value="<%=password%>"/></td>
            </tr>
            <tr>
                <td>Submit </td>
                <td>
                    <input type="submit" name="submit" value="submit">
                </td>
            </tr>
        </table>
    </form>       
</body>
</html>
