<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reservation valididation</title>
    </head>
    <body>
        <p>Paiement réussi ! merci ?</p>
 
         <p>
            <a href="HomePage.jsp?idClient=<%=request.getParameter("idClient") %>" >Page d'accueil</a></p>   
    </body>
</html>