/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.*;
import com.google.maps.model.*;
import dao.ClientDao;
import dao.JpaUtil;
import dao.LivreurDao;
import java.math.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import metier.modele.Client;
import metier.modele.Commande;
import metier.modele.Livreur;
import metier.modele.Produit;
import util.GeoTest;

/**
 *
 *
 * @author gspecq
 */
public class ServiceTechnique {
/**
 * 
 * @param commande 
 */
  public static boolean affecterLivreur(Commande commande) throws Throwable
  {
      Livreur l = null;
      LivreurDao livreurDao = new LivreurDao();
      List<Livreur> livreurs=ServiceMetier.getLivreursDispoPoids(commande.getPoidsTotal());
      if(livreurs.size()!=0)
      {
          double temps=livreurs.get(0).dureeTrajet(new LatLng(commande.getRestaurant().getLatitude(), commande.getRestaurant().getLongitude()),
                  new LatLng(commande.getCommanditaire().getLatitude(),commande.getCommanditaire().getLongitude()));
          l=livreurs.get(0);
          for(int i=1; i<livreurs.size(); i++)
          {
              double ntemps=livreurs.get(i).dureeTrajet(new LatLng(commande.getRestaurant().getLatitude(), commande.getRestaurant().getLongitude()),
                  new LatLng(commande.getCommanditaire().getLatitude(),commande.getCommanditaire().getLongitude()));
              System.out.println("Livreur "+livreurs.get(i).getId()+" temps en min "+ntemps+" "+livreurs.get(i).getAdresse()+" "+commande.getRestaurant().getAdresse());
              if(ntemps<temps)
              {
                  temps=ntemps;
                  l=livreurs.get(i);
              }
          }
          l.setDisponible(false);
          commande.setLivreur(l);
          return true;
      }
      return false;
      
  }
/**
 * 
 * @param commande
 * @return
 * @throws Throwable 
 */

  
  private static void envoyerMail(String mailDest, String objet, String texte)
  {
      System.out.println("TO : "+mailDest );
      System.out.println("OBJECT : "+objet);
      System.out.println("CONTENT : "+texte);
  }
  
  public static void mailLivraison(Commande commande)
  {
      String adresse=commande.getLivreur().getEmail();
      StringBuilder objet = new StringBuilder();
      objet.append("Livraison n°");
      objet.append(commande.getId());
      StringBuilder texte = new StringBuilder();
      texte.append("Bonjour, \n voici la commande à livrer");
      envoyerMail(adresse, objet.toString(), texte.toString());
  }
  
  public static void mailInscription(Client client, boolean success)
  {
      String adresse = client.getMail();
      String objet = "Bienvenue chez Gustat'if !!";
      String texte = "";
      if(success)
      {
            texte = "Bonjour "+client.getNom()+", \nvotre inscription sur gustat'if est effective !";
      }
      else
      {
          texte = "Désolé "+client.getNom()+", \nvotre inscription sur gustat'if a échouée.";
      }
      envoyerMail(adresse, objet, texte);
  }
  
    public static double calculerPrix(Map<Produit, Integer> mapProduits){
        int prix=0;
        Set<Produit> cles = mapProduits.keySet();
        Iterator<Produit> it = cles.iterator();
        while (it.hasNext()){
           Produit cle = it.next(); 
           int quantite = mapProduits.get(cle); 
           prix+=cle.getPrix()*quantite;
        }
        return prix;
    }
    
    public static double calculerPoids(Map<Produit, Integer> mapProduits){
        int prix=0;
        Set<Produit> cles = mapProduits.keySet();
        Iterator<Produit> it = cles.iterator();
        while (it.hasNext()){
           Produit cle = it.next(); 
           int quantite = mapProduits.get(cle); 
           prix+=cle.getPoids()*quantite;
        }
        return prix;
    }
  
 
}
