/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import dao.ClientDao;
import dao.JpaUtil;
import dao.LivreurDao;
import dao.RestaurantDao;
import java.util.List;
import metier.modele.Client;
import metier.modele.Drone;
import metier.modele.Livreur;
import metier.modele.Produit;
import metier.modele.Restaurant;
import metier.service.ServiceTechnique;
/**
 *
 * @author sbelletier
 */
public class ServiceMetier {
    
    /**
     * 
     * @param nom
     * @param prenom
     * @param adresse
     * @param mail
     * @param password
     * @return
     * @throws Throwable 
     */
      public static boolean inscription(String nom, String prenom, String adresse, String mail, String password) throws Throwable
        {
            ClientDao clientDao = new ClientDao();
            JpaUtil.creerEntityManager();
            if(clientDao.findByEmail(mail).size()==0)
            {
              Client c=new Client(nom, prenom, adresse, mail, password);
              JpaUtil.ouvrirTransaction();
              clientDao.create(c);
              JpaUtil.validerTransaction();
              ServiceTechnique.mailInscription(c, true);
              return true;
            }
            JpaUtil.fermerEntityManager();
            //ServiceTechnique.mailInscription(c, false);
            return false;
        }
    
  /**
   * 
   * @param mail le mail entré
   * @param password le mot de passe entré
   * @return 0 en cas d'echec, 1 pour connexion de client, 2 pour connexion de livreur a velo et 3 pour connexion du gestionnaire
   * @throws Throwable 
   */
  public static int connection(String mail, String password) throws Throwable
  {
      LivreurDao livreurDao = new LivreurDao();
      JpaUtil.creerEntityManager();
      List<Livreur> livreurs=livreurDao.findByEmail(mail);
      JpaUtil.fermerEntityManager();
      if(livreurs.size() > 0)
      {
        Livreur l=livreurs.get(0);
        if(password.equals(l.getPassword() ) )
        {
            if(l instanceof Drone)
            {
                return 3;
            }
            
            else
            {
                return 2;
            }
        }

        else
        {
            return 0;
        }
      }
      
      ClientDao clientDao = new ClientDao();
      JpaUtil.creerEntityManager();
      List<Client> clients=clientDao.findByEmail(mail);
      JpaUtil.fermerEntityManager();
      if(clients.size() == 1)
      {
          Client c=clients.get(0);
        if(password.equals(c.getPassword() ) )
        {
            return 1;
        }

        else
        {
            return 0;
        }
      }
      else
      {
          return 0;
      }
  }
  
  public static List<Restaurant> listRestaurant() throws Throwable
  {
      return (new RestaurantDao()).findAll();
  }
  
  public static List<Produit> listRestaurantProduit(int idResto) throws Throwable
  {
      return (new RestaurantDao()).findById((long)idResto).getProduits();
  }
  
}
