/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import dao.ClientDao;
import dao.CommandeDao;
import dao.ProduitDao;
import dao.RestaurantDao;
import java.util.Date;
import metier.modele.Commande;
import metier.service.ServiceMetier;
import metier.service.ServiceTechnique;
import util.Saisie;

/**
 *
 * @author Gabriel
 */
public class CasUtilisation {
    
    public static void main (String[] args) throws Throwable
    {
        System.out.println("Commande: EXIT, CLIENT, CONNECT, PRODUITS, COMMANDE, END_COMMANDE, RESTAURANTS");
        String commande="";
        while (!commande.equals("EXIT"))
        {
            commande=Saisie.lireChaine("Entrer une commande:");
            if(commande.equals("CLIENT"))
            {
                String nom=Saisie.lireChaine("Nom");
                String prenom=Saisie.lireChaine("Prenom");
                String adresse=Saisie.lireChaine("Adresse");
                String mail=Saisie.lireChaine("Email?");
                String pass=Saisie.lireChaine("Mdp");
                if(ServiceMetier.inscription(nom, prenom, adresse, mail, pass))
                {
                    System.out.println("Client créé");
                }
                else
                {
                    System.out.println("Impossible de créer le client, mail déjà utilisé");
                }
            }
            
            else if(commande.equals("CONNECT"))
            {
                String mail=Saisie.lireChaine("Email?");
                String pass=Saisie.lireChaine("Mdp");
                int t=ServiceMetier.connection(mail, pass);
                    switch (t)
                    {
                        case 0:
                            System.out.println("Erreur d'identification");
                            break;
                        case 1:
                            System.out.println("Connecté en tant que client");
                            break;

                        case 2:
                            System.out.println("Connecté en tant que livreur");
                            break;

                        case 3:
                            System.out.println("Connecté en tant que manager");
                            break;
                    }
                          
                        
                }
            
                else if(commande.equals("RESTAURANTS"))
                {
                    System.out.println(ServiceMetier.listRestaurant());                   
                }
                else if(commande.equals("PRODUITS"))
                {
                    int idR=Saisie.lireInteger("Entrer id restaurant");
                    System.out.println(ServiceMetier.listRestaurantProduit(idR));                   
                }
            
                if(commande.equals("COMMANDE"))
                {
                    long idC=Saisie.lireInteger("Entrer idClient");
                    long idR=Saisie.lireInteger("Entrer idResto");
                    Commande c=new Commande((new ClientDao()).findById(idC),(new RestaurantDao()).findById(idR));
                    int k=-1;
                    while(k!=-3)
                    {
                        k=Saisie.lireInteger("Entrer: idProduit -3 pour finaliser la commande");
                        
                        if(k!=-3){
                            int q=Saisie.lireInteger("Entrer: quantité");
                            c.setProduit((new ProduitDao()).findById((long)k),q);
                        }                       
                    }
                    
                    if(ServiceTechnique.validerCommande(c))
                    {
                        System.out.println("Commande envoyée");
                    }
                    else
                    {
                         System.out.println("Aucun livreur n'est disponible pour votre commande veuillez réessayer plus tard");
                    }
                        
                }
                
                else if(commande.equals("END_COMMANDE"))
                {
                    int idCommande=Saisie.lireInteger("id commande");
                    Commande c=(new CommandeDao().findById((long)idCommande));
                    c.getLivreur().setDisponible(true);
                    c.terminerCommande(new Date());
                }
        }
        
    }
    
}
