/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import dao.ClientDao;
import dao.JpaUtil;
import dao.RestaurantDao;
import java.util.ArrayList;
import java.util.List;
import metier.modele.Client;
import metier.modele.Commande;
import metier.modele.Livreur;
import metier.modele.Produit;
import metier.modele.ProduitQuantite;
import metier.modele.Restaurant;
import metier.service.ServiceMetier;
import util.Saisie;

/**
 *
 * @author Gabriel
 */
public class CasUtilisation {
    
    public static void main (String[] args) throws Throwable
    {
        
        String commande="";
        while (!commande.equals("EXIT"))
        {
            System.out.println("Commande: EXIT, CLIENT, CONNECT_C, CONNECT_L, CONNECT_M, PRODUITS, COMMANDE, END_COMMANDE, RESTAURANTS, LIST_LIVREUR, LIST_COMMANDE, DISP_LIVREUR");
            commande=Saisie.lireChaine("Entrer une commande:");
            switch (commande) {
                case "CLIENT":
                    {
                        String nom=Saisie.lireChaine("Nom");
                        String prenom=Saisie.lireChaine("Prenom");
                        String adresse=Saisie.lireChaine("Adresse");
                        String mail=Saisie.lireChaine("Email?");
                        String pass=Saisie.lireChaine("Mdp");
                        Client c = new Client(nom, prenom, adresse, mail, pass);
                        if(ServiceMetier.inscription(c))
                        {
                            System.out.println("Client créé");
                        }
                        else
                        {
                            System.out.println("Impossible de créer le client, mail déjà utilisé");
                        }       break;
                    }
                case "CONNECT_C":
                    {
                        String mail=Saisie.lireChaine("Email?");  
                        String pass=Saisie.lireChaine("Mdp");
                        Client c = ServiceMetier.connectionClient(mail, pass);
                        if(c != null)
                        {
                            System.out.println("Clinet "+c.getNom()+" s'est connecté");
                        }
                        else
                        {
                            System.out.println("Erreur password ou login");
                        }       break;
                    }
                case "CONNECT_L":
                    {
                        String mail=Saisie.lireChaine("Email?");
                        String pass=Saisie.lireChaine("Mdp");
                        Livreur c = ServiceMetier.connectionLivreur(mail, pass);
                        if(c != null)
                        {                       
                            System.out.println("Livreur "+c.getEmail()+" s'est connecté");
                        }
                        else
                        {
                            System.out.println("Erreur password ou login");
                        }       break;
                    }
                case "CONNECT_M":
                    {
                        String mail=Saisie.lireChaine("Email?");
                        String pass=Saisie.lireChaine("Mdp");
                        if(ServiceMetier.connectionManager(mail, pass))
                        {
                            System.out.println("Manager s'est connecté");
                        }
                        else
                        {
                            System.out.println("Erreur password ou login");
                        }       break;
                    }
                case "RESTAURANTS":
                    {
                        List<Restaurant> l=ServiceMetier.listRestaurant();
                        for(int i=0; i<l.size(); i++)
                            System.out.println(l.get(i).getDenomination());
                        break;
                    }
                case "PRODUITS":
                    {
                        Integer idR=Saisie.lireInteger("Entrer id restaurant");
                        List<Produit> l=ServiceMetier.listRestaurantProduit((long)idR);
                        if(l == null)
                        {
                            System.out.println("ma");
                        }
                        else
                        {
                            System.out.println(l);
                        }   break;
                    }
                case "COMMANDE":
                    {
                        JpaUtil.creerEntityManager();
                        long idC=Saisie.lireInteger("Entrer idClient");
                        long idR=Saisie.lireInteger("Entrer idResto");
                        ClientDao clientDao =new ClientDao();
                        RestaurantDao restoDao = new RestaurantDao();
                        Restaurant r=restoDao.findById((long)idR);
                        Client c= clientDao.findById((long) idC );
                        int k=-1;
                        List<ProduitQuantite> liste= new ArrayList();
                        while(k!=-3)
                        {
                            k=Saisie.lireInteger("Entrer: idProduit -3 pour finaliser la commande");
                            if(k!=-3){
                                int q=Saisie.lireInteger("Entrer: quantité");
                                liste.add(new ProduitQuantite(ServiceMetier.findProduitById((long)k), q));
                            }
                        }   Commande com= new Commande(liste, c, r);
                        if(ServiceMetier.validerCommande(com))
                        {
                            System.out.println("Commande envoyée");
                        }
                        else
                        {
                            System.out.println("Aucun livreur n'est disponible pour votre commande veuillez réessayer plus tard");
                        }   break;
                    }
                case "END_COMMANDE":
                    int idCommande=Saisie.lireInteger("id commande");
                    ServiceMetier.cloturerCommandeId((long)idCommande);
                    break;
                case "LIST_COMMANDE":
                    String c2=Saisie.lireChaine("ALL ou LIVREUR");
                    if(c2.equals("ALL"))
                    {
                        List<Commande> l=ServiceMetier.getCommandes();
                        for(int i=0; i<l.size(); i++)
                            System.out.println(ServiceMetier.getCommandes().get(i).getDateCommande());
                    }
                    else if (c2.equals("LIVREUR"))
                    {
                        int id=Saisie.lireInteger("Id du livreur");
                        List<Commande> l=ServiceMetier.getCommandesLivreurEnCours(ServiceMetier.findLivreurById((long)id));
                        for(int i=0; i<l.size(); i++)
                            System.out.println(ServiceMetier.getCommandes().get(i).getPoidsTotal()+"g "+ServiceMetier.getCommandes().get(i).getPrixTotal()+"$");
                    }
                    break;
                case "DISP_LIVREUR":
                    int id=Saisie.lireInteger("Id du livreur");
                    ServiceMetier.findLivreurById((long)id).setDisponible(true);
                    break;
                case "LIST_LIVREUR":
                    System.out.println("Drone");
                    System.out.println(ServiceMetier.getDrones());
                    System.out.println("Velo");
                    System.out.println(ServiceMetier.getVelos());
                    System.out.println("dispo");
                    System.out.println(ServiceMetier.getLivreursDispo());
                    break;
                default:
                    break;
            }
        }
        
        System.exit(0);
    }
    
}
