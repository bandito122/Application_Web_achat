<%-- 
    Document   : NoDevicesStock
    Created on : 14 janv. 2017, 19:03:03
    Author     : Bob Mastrolilli
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Liste des appareils disponible ! </title>
    </head>
    <body>
        <p>Désolé mais il n'y a rien plus rien en stock !</p>
        
         <p>
            <a href="HomePage.jsp?idClient=<%=request.getParameter("idClient") %>" >Retournez à la page d'accueil</a></p>  
            <a href="HomePage.jsp?idClient=<%=request.getParameter("idClient") %>" >Afficher votre panier</a></p>   
    </body>
</html>