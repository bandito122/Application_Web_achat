/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import GestionSocket.GestionSocket;
import static RequestResponseDISMAP.IDISMAP.BUY_GOODS_REQUEST;
import static RequestResponseDISMAP.IDISMAP.BUY_GOODS_REQUEST_CO;
import static RequestResponseDISMAP.IDISMAP.SEARCH_GOODS_REQUEST;
import static RequestResponseDISMAP.IDISMAP.TAKE_GOODS_REQUEST;
import static RequestResponseDISMAP.IDISMAP.YES;
import RequestResponseDISMAP.RequestDISMAP;
import RequestResponseDISMAP.ResponseDISMAP;
import beans.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Bob Mastrolilli
 */
public class ServletPrincipale extends HttpServlet
{

    //BeanBDAccessMySQL bean;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    BeanBDAccessMySQL beanSql;
    GestionSocket GSocket;
    
    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
        ServletContext sc = getServletContext();
        sc.log("-- démarrage de la servlet ServletPrincipal");
        sc.log("YO=" + FichierConfig.getNomFichierConfig());
        sc.log("YO=" + FichierConfig.getProperty("host"));
        beanSql = ConnectToBd();
        GSocket = new GestionSocket();
        GSocket.ConnectServeur(UtilsDISMAP.FichierConfig.getProperty("serMv"), Integer.parseInt(UtilsDISMAP.FichierConfig.getProperty("portMv")));
        
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception 
    {
        int NumClient = 0;
        java.io.PrintWriter out = response.getWriter();
        ServletContext sc = getServletContext();
        sc.log("-- passage par la servlet ControleDataCenter");

        String action = request.getParameter("action");
        sc.log("-- Valeur du paramètre action : " + action);

        System.out.println("Connexion à la bd ");
        int numClient = 0;
        if (action.equals("Connexion")) 
        {

            boolean etatCheckBox = request.getParameter("cb_id") != null;
            sc.log(("etat = " + etatCheckBox));
            /* Verification que le login n'existe déjà pas */
            String login_app_client = (String) request.getParameter("login");
            int bool = beanSql.VerifyIfLoginExist(login_app_client);
            sc.log("bool = " + bool);
            if ((bool == 1) && etatCheckBox == true) // le client se dit "nouveau client" et que le login existe --> on renvoie à index.
            {
                sc.log("nouveau client mais login existe déjà");
                System.out.println("Login existe déjà...");
                response.sendRedirect(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/WebApp_Achat/index.jsp");
                sc.log("adresse send redirect = " + request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/WebApp_Achat/index.jsp");
                sc.log("");
                sc.log("je continue");
            } else if (bool == 0 && etatCheckBox == true) // INSERT DANS CLIENT_WEB ET CLIENT
            { // Si le nouveau client est bien inséré alors on continue
                sc.log("nouveau client --> insertion client");

                NumClient = beanSql.InsertLoginWeb(login_app_client, (String) request.getParameter("password"));
                if (NumClient == 0) {
                    response.sendRedirect(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/WebApp_Achat/index.jsp");
                } else 
                {
                   action="Liste_appareils";

                }

            } else if ((bool == 0) && etatCheckBox == false) // Le client déjà enregistré qui donne un mauvais login
            {
                sc.log("Login inexistant...");
                System.out.println("Login inexistant..");
                response.sendRedirect(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/WebApp_Achat/index.jsp");
                sc.log("Je continue...");
            } 
            else if ((bool == 1) && etatCheckBox == false) // déjà client et bon mot de pass -> on envoie la liste
            {
                sc.log("LOGIN = " + (String) request.getParameter("login"));
                String pwd = beanSql.findPasswpordByLoginWEB((String) request.getParameter("login"));
                NumClient= beanSql.GetIdClientByLogin((String) request.getParameter("login"));
                sc.log("pwd= " + pwd);
                sc.log("pass = " + request.getParameter("password")  );
                if (pwd != null) 
                {
                    if (pwd.equals(request.getParameter("password")))
                    {
                        sc.log("LIIIIIISTE");
                        action="Liste_appareils";  
                        
                    } 
                    else 
                    {
                        response.sendRedirect(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/WebApp_Achat/index.jsp");
                    }

                 }
             }  
        }
        if (action.equals("ajout_panier")) {
            numClient = Integer.valueOf(request.getParameter("idClient"));
            int numSerie = Integer.valueOf(request.getParameter("numSerie"));

            Vector TakeGoods = new Vector();

            TakeGoods.add(request.getParameter("numSerie"));

            RequestDISMAP req = new RequestDISMAP(TAKE_GOODS_REQUEST, TakeGoods);
            GSocket.Send(req);
            System.out.println("Apres la requete");
            //Attente de reponse du serveur
            ResponseDISMAP rep = (ResponseDISMAP) GSocket.Receive();
            System.out.println("Apres la réponse du serveur");

            if (rep.getCodeRetour() == YES) {
                //appel du bean
                int test = beanSql.InsertPanierWeb(numClient, numSerie);
                if (test == 0) //PROBLEME
                {
                    response.sendRedirect(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/WebApp_Achat/NoDevicesStock.jsp?idClient="  + numClient );
                } else {
                    response.sendRedirect(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/WebApp_Achat/Panier.jsp?idClient=" + +numClient );

                }
            } else {
                response.sendRedirect(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/WebApp_Achat/NoDevicesStock.jsp?idClient="  + numClient  );
            }

        }
        if (action.equals("Liste_appareils")) 
        {
            /*Si l'action est demandé par la servletprincipale alors getParameter retourne null: */
            /*Si l'action provient d'une requête http alors on récupérer l'id client avec getParameter ; */
            if(request.getParameter("idClient") != null) 
            {
                NumClient = Integer.valueOf(request.getParameter("idClient"));
            }
                
            sc.log(request.getParameter("idClient"));
            sc.log("Je genere page static ");
             
                    sc.log("Je genere page static ");
                    sc.log("Je genere page static ");
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Listing des Appareils disponible </title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<tr>");
                    out.println(" <br/> <br /> <br /> ");
                    out.println(" <center> ");
                    out.println("<table border style=" + "background:#80BFFF" + ">");

                   
                    Vector v = new Vector();
                    v.add("ALL".toString());
                    RequestDISMAP req = new RequestDISMAP(SEARCH_GOODS_REQUEST, v);

                    sc.log("serMv = " + UtilsDISMAP.FichierConfig.getProperty("serMv"));
                    sc.log("portMv = " + UtilsDISMAP.FichierConfig.getProperty("portMv"));

                    GSocket.Send(req);
                    sc.log("SEND");
                    ResponseDISMAP rep = (ResponseDISMAP) GSocket.Receive();
                    List<Object> res = (List<Object>) rep.getChargeUtile();
                    sc.log(res.toString());
                    //sc.log("nombre d'article HTML : " + res.size());
                    if (rep.getCodeRetour() == YES) {
                        out.println("<p>Cataloguqe du site InCheapDev.");
                        for (int i = 0; i < res.size(); i++) {

                            sc.log(("element" + new Integer(i).toString()));
                            out.println("<tr>");
                            out.println("<td>Numero de série : " + ((Vector) res.get(i)).get(0) + "</td>");
                            out.println("<td>Type Général :" + ((Vector) res.get(i)).get(1) + "</td>");
                            out.println("<td>Type Précis : " + ((Vector) res.get(i)).get(2) + "</td>");
                            out.println(" <td>Marque :" + ((Vector) res.get(i)).get(3) + "</td>");
                            out.println(" <td>Prix :  " + ((Vector) res.get(i)).get(4) + "</td>");
                            out.println(" <td>Etat situation : " + ((Vector) res.get(i)).get(5) + "</td>");
                            out.println(" <td> <form action=\"Reservation.jsp\" name=\"reservation\" method=\"GET\"> ");
                            out.println("<input type=\"submit\" value=\"Choisir cet article\" />");
                            out.println("<input type=\"hidden\" name=\"numSerie\" value=\"" + ((Vector) res.get(i)).get(0).toString() + "\" " + "/>");
                            Integer inte = new Integer(NumClient);
                            sc.log("numClient= " + inte.toString());
                            out.println("<input type=\"hidden\" name=\"idClient\" value=\"" + inte.toString() + "\" " + " />");
                            out.println("</form> " + "</td");
                            out.println("</tr>");
                        }

                    } else {
                        out.println(" Désolé plus rien disponible !!!! ");
                    }

                    out.println("</table>");
                    out.println("</body>");
                    out.println("</html>");
        }
        if (action.equals("payer")) {


            numClient = Integer.valueOf(request.getParameter("idClient"));
            sc.log("idClient = " + numClient);
            Vector BuyGoods = new Vector();
            BuyGoods.add(beanSql.getPanier(numClient));
            BuyGoods.add("CARTE"); //paiement par carte vu que c'est du WEB...

            RequestDISMAP req = new RequestDISMAP(BUY_GOODS_REQUEST, BuyGoods);
            GSocket.Send(req);
            System.out.println("Apres la requete");
            //Attente de reponse du serveur
            ResponseDISMAP rep = (ResponseDISMAP) GSocket.Receive();
            sc.log("Apres la réponse du serveur");

            if (rep.getCodeRetour() == YES) {
                //appel du bean
                sc.log("Paiement ok ");
                response.sendRedirect(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/WebApp_Achat/PaiementOk.jsp?idClient=" + numClient);
                beanSql.removeNumSeriesFromPanier(numClient);
            } else {
                response.sendRedirect(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/WebApp_Achat/PaiementFailed.jsp?idClient=" + numClient);
            }

        }

    }

    public BeanBDAccessMySQL ConnectToBd() {
        DataBaseAccessFactory dbaf;
        IDataBaseAccess db;
        ConnectionOptions options;
        dbaf = DataBaseAccessFactory.getInstance();
        db = dbaf.getDataBaseAcces("MySQL");
        options = new ConnectionOptions();
        options.addOption("host", FichierConfig.getProperty("host"));
        options.addOption("port", FichierConfig.getProperty("portMySQL"));
        System.out.println("PORT MYSQL = " + FichierConfig.getProperty("portMySQL"));
        options.addOption("database", FichierConfig.getProperty("DB_name_MySQL"));
        options.addOption("user", FichierConfig.getProperty("userMySQL"));
        options.addOption("passwd", FichierConfig.getProperty("pwdMySQL"));
        int test = db.Connect(options);

        return (BeanBDAccessMySQL) db;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ServletPrincipale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ServletPrincipale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
