<%-- 
    Document   : Reservation
    Created on : 15 janv. 2017, 02:24:41
    Author     : Bob Mastrolilli
--%>

<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.CallableStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page Reservation</title>
    </head>
    <body>
        <H3>Bienvenue sur la page de réservation</H3>
    <td> Vous avez demandé à réserver l'appareil  n°<%=request.getParameter("numSerie")%> </td> 
    <form action="/WebApp_Achat/ServletPrincipale" method="GET"> 

        <input type="submit" value="Commander!" /> 
        <input type="hidden" name="idClient"   value="<%=request.getParameter("idClient")%>"  />
        <input type="hidden" name="numSerie"   value= "<%=request.getParameter("numSerie")%>" />
        <input type="hidden" name="action"   value= "ajout_panier" />
    </form>
    <br /><br /><br />
    <a href="HomePage.jsp?idClient=<%=  request.getParameter("idClient")   %>"   > Page d'accueil</a></p>    

</body>
</html>
