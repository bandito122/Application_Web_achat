<%-- 
    Document   : Panier
    Created on : 14 janv. 2017, 19:06:07
    Author     : Bob Mastrolilli
--%>

<%@page import="java.util.Vector"%>
<%@page import="beans.BeanBDAccessMySQL"%>
<%@page import="Servlet.FichierConfig"%>
<%@page import="beans.ConnectionOptions"%>
<%@page import="beans.IDataBaseAccess"%>
<%@page import="beans.DataBaseAccessFactory"%>
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
        <title>Votre panier</title>
    </head>
    <body>

        <p>PANIER : </p>

        <%
           DataBaseAccessFactory dbaf;
            IDataBaseAccess db;
            ConnectionOptions options;
            dbaf = DataBaseAccessFactory.getInstance();
            db = dbaf.getDataBaseAcces("MySQL");
            

            options = new ConnectionOptions();
            options.addOption("host", FichierConfig.getProperty("host"));
            options.addOption("port", FichierConfig.getProperty("portMySQL"));
            System.out.println("PORT MYSQL = " + FichierConfig.getProperty("portMySQL") );
            options.addOption("database", FichierConfig.getProperty("DB_name_MySQL"));
            options.addOption("user", FichierConfig.getProperty("userMySQL"));
            options.addOption("passwd", FichierConfig.getProperty("pwdMySQL"));
            db.Connect(options);
            Vector v  = new Vector();
            BeanBDAccessMySQL Bean = (BeanBDAccessMySQL) db;
           
            v = Bean.getPanier(Integer.valueOf(request.getParameter("idClient")));
            if( v==null)
            {
                
            
        %>
            Votre pannier est vide...
            <%}
                else
                {
                        
                   %>     
        <br/> <br /> <br />
    <center>  
        <table border style="background:#80BFFF"> 
            <tr>
            <%
                int i;
                for(i=0 ; i<v.size() ;i++)
                {
                    
                    System.out.println(v.get(0));
            %>
            </tr>
                <td>ID appareil :<%=v.get(i)%> </td>
        
                <%
                }%>
                </table>
                <form action="ServletPrincipale" name="payer" method="GET">
                        <input type="hidden" name="idClient" value="<%=request.getParameter("idClient")%>" />
                        <input type="submit" name="action" value="payer" />
                    </form>
                
            
            
            <p>
 
                <%}%>
                <a href="HomePage.jsp?idClient=<%=request.getParameter("idClient") %>" >Page d'accueil</a></p>  



            </body>
            </html>
         