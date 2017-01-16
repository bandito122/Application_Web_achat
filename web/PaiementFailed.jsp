<%-- 
    Document   : PaiementFailed
    Created on : 16 janv. 2017, 02:08:46
    Author     : Bob Mastrolilli
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reservation valididation</title>
    </head>
    <body>
        <p>Votre paiement n'a pas abouti... ! Desolé ! Allez travailler pour gagner de l'argent puis revenez dépenser vos sous chez nous ! merci !   ?</p>

         <p>
            <a href="HomePage.jsp?idClient=<%=request.getParameter("idClient") %>" >Page d'accueil</a></p>  
    </body>
</html>