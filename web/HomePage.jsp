<%-- 
    Document   : HomePage
    Created on : 11 janv. 2017, 14:50:54
    Author     : Bob Mastrolilli
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Page d'accueil</title>
    </head>
    <body>
    <center>
        <h1>Menu Principal</h1>

        <p>Bonjour et bienvenue sur WebApplic Reservation Appareil ! : </p>

        RESERVATION<br/>
        ------------------------------------------------
        <form action="/WebApp_Achat/ServletPrincipale" method="POST">
            <input type="submit" name="action" value="Liste_appareils" />
            <input type="hidden" name="idClient" value="<%=request.getParameter("idClient") %>" /> <%-- j'envoie toujours idClient car le client n'a pas de "session"--%>
        </form>
        <br/>PANIER<br/>
        ------------------------------------------------
        <form action="/WebApp_Achat/Panier.jsp?" method="POST">
            <input type="submit" name="action" value="panier" />
             <input type="hidden" name="idClient" value="<%=request.getParameter("idClient")%>" />
        </form>
        
        
    </center>
    </body>
</html>

