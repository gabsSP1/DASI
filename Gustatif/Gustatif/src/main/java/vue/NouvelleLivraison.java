/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.util.ArrayList;
import java.util.List;
import metier.modele.Client;
import metier.modele.Commande;
import metier.modele.ProduitQuantite;
import metier.modele.Restaurant;
import metier.service.ServiceMetier;

/**
 *
 * @author Gabriel
 */
public class NouvelleLivraison {
    public static void main(String[] args) throws Throwable
    {
        int a=53;
        int b=56;
        ProduitQuantite pQ1= new ProduitQuantite(ServiceMetier.findProduitById((long)a),5);
        ProduitQuantite pQ2= new ProduitQuantite(ServiceMetier.findProduitById((long)b),5);
        List<ProduitQuantite> l= new ArrayList();
        l.add(pQ1);
        l.add(pQ2);
        Restaurant r=ServiceMetier.findRestaurantById((long)((int)2));
        Client c=ServiceMetier.findClientById((long)((int)506));
        Commande commande = new Commande(l, c, r);
        if(ServiceMetier.validerCommande(commande))
        {
            System.out.println("Commande effectuée");
        }
        else
        {
            System.out.println("Erreru lors de la commande veuillez réessayer plus tard");
        }
        System.exit(0);
    }
    
}
