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
import java.util.List;
import metier.modele.Client;
import metier.modele.Commande;
import metier.modele.Livreur;
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
  private static void affecterLivreur(Commande commande)
  {
      Livreur l = null;
      LivreurDao livreurDao = new LivreurDao();
      List<Livreur> livreurs=livreurDao.findDispo(commande.getPoidsTotal());
      if(livreurs.size()!=0)
      {
          double temps=livreurs.get(0).dureeTrajet(new LatLng(commande.getRestaurant().getLatitude(), commande.getRestaurant().getLongitude()),
                  new LatLng(commande.getCommanditaire().getLatitude(),commande.getCommanditaire().getLongitude()));
          l=livreurs.get(0);
          for(int i=1; i<livreurs.size(); i++)
          {
              double ntemps=livreurs.get(i).dureeTrajet(new LatLng(commande.getRestaurant().getLatitude(), commande.getRestaurant().getLongitude()),
                  new LatLng(commande.getCommanditaire().getLatitude(),commande.getCommanditaire().getLongitude()));
              if(ntemps<temps)
              {
                  temps=ntemps;
                  l=livreurs.get(i);
              }
          }
          
      }
      
      commande.setLivreur(l);
  }
/**
 * 
 * @param commande
 * @return
 * @throws Throwable 
 */
  public static boolean validerCommande(Commande commande) throws Throwable
  {
      affecterLivreur(commande);
      if (commande.getLivreur() != null)
      {
          mailLivraison(commande);
          commande.getLivreur().setDisponible(false);
          commande.setDateCommande(new Date());
          commande.storeCommande();
          return true;
      }
      return false;
  }
  
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
  

  
 
}
