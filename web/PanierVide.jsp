<%-- 
    Document   : PanierVide
    Created on : 16 janv. 2017, 02:15:05
    Author     : Bob Mastrolilli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Infos </title>
    </head>
    <body>
        <p>Votre panier est vide ! (pour le moment). Allez réserver plein de choses ! </p>
        
         <p>
            <a href="HomePage.jsp?idClient=<%=request.getParameter("idClient") %>" >Retournez à la page d'accueil</a></p>  
            <a href="HomePage.jsp?idClient=<%=request.getParameter("idClient") %>" >Afficher votre panier</a></p>   
    </body>
</html>
<%}%>