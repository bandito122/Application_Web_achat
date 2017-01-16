<%-- 
    Document   : index
    Created on : 11 janv. 2017, 14:49:04
    Author     : Bob Mastrolilli
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Connexion d'un client</title>
    </head>
    
    <body>
    <center><h1> Bienvenue sur Web_achat_appareil !</h1></center>
   <br/><br/><br/><br/> 
    <center>   
    <div>
            <form action="ServletPrincipale"  method="GET">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Login :<input type= "text" name="login" value="guest" /></br>
                Password :<input type="password" name="password" value="guest" /></br>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="hidden" name="action" value="Connexion" />
                <input type="submit" value="Connecter" />
                <input type="checkbox" name="cb_id" value="cb_id_valeur" checked ="checked" />
            </form>
        </div>
        </center> 

    </body>
</html>
