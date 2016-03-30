/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import dao.ClientDao;
import dao.CommandeDao;
import dao.JpaUtil;
import dao.LivreurDao;
import dao.ProduitDao;
import dao.ProduitQuantiteDao;
import dao.RestaurantDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import metier.modele.Client;
import metier.modele.Commande;
import metier.modele.Drone;
import metier.modele.Livreur;
import metier.modele.Produit;
import metier.modele.Restaurant;
import metier.modele.Velo;
import static metier.service.ServiceTechnique.mailLivraison;
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
      public static boolean inscription(Client c) throws Throwable
        {
            JpaUtil.creerEntityManager();
            ClientDao clientDao = new ClientDao();
            if(clientDao.findByEmail(c.getMail()).isEmpty())
            {
              JpaUtil.ouvrirTransaction();
              clientDao.create(c);
              JpaUtil.validerTransaction();
              ServiceTechnique.mailInscription(c, true);
              JpaUtil.creerEntityManager();
              return true;
            }
            ServiceTechnique.mailInscription(c, false);
            JpaUtil.fermerEntityManager();
            return false;
        }
    
  /**
   * 
   * @param mail le mail entré
   * @param password le mot de passe entré
   * @return 0 en cas d'echec, 1 pour connexion de client, 2 pour connexion de livreur a velo et 3 pour connexion du gestionnaire
   * @throws Throwable 
   */
      
      
  public static Client connectionClient(String mail, String password) throws Throwable
  {
      JpaUtil.creerEntityManager();
      ClientDao clientDao = new ClientDao();
      List<Client> clients=clientDao.findByEmail(mail);
      if(clients.size() == 1)
      {
          Client c=clients.get(0);
        if(password.equals(c.getPassword() ) )
        {
            JpaUtil.fermerEntityManager();
            return c;
        }

        else
        {
            JpaUtil.fermerEntityManager();
            return null;
        }
      }
      else
      {
          JpaUtil.fermerEntityManager();
          return null;
      }
      
  }
  
  public static Livreur connectionLivreur(String mail, String password) throws Throwable
  {
      JpaUtil.creerEntityManager();
      LivreurDao livreurDao = new LivreurDao();
      List<Livreur> livreurs=livreurDao.findByEmail(mail);
      if(!livreurs.isEmpty())
      {
        Livreur l=livreurs.get(0);
        if(password.equals(l.getPassword() ) )
        {   JpaUtil.fermerEntityManager();
            return l;
        }

        else
        {
            JpaUtil.fermerEntityManager();
            return null;
        }
      }
      else
        {
            JpaUtil.fermerEntityManager();
            return null;
        }
  }
  
  public static boolean connectionManager(String mail, String password) throws Throwable
  {
      JpaUtil.creerEntityManager();
      LivreurDao livreurDao = new LivreurDao();
      List<Livreur> livreurs=livreurDao.findByEmail(mail);
      if(!livreurs.isEmpty())
      {
        Livreur l=livreurs.get(0);
        if(password.equals(l.getPassword()) && l instanceof Drone)
        {     
            JpaUtil.fermerEntityManager();
            return true;
        }

        else
        {
            JpaUtil.fermerEntityManager();
            return false;
        }
      }
      else
        {
            JpaUtil.fermerEntityManager();
            return false;
        }
      
  }
  public static List<Commande> getCommandesLivreur(Livreur livreur) throws Throwable
  {
      JpaUtil.creerEntityManager();
      List<Commande> l= (new CommandeDao()).findByLivreur(livreur);
      JpaUtil.fermerEntityManager();
      return l;
  }
  
  public static List<Commande> getCommandesLivreurEnCours(Livreur livreur) throws Throwable
  {
      JpaUtil.creerEntityManager();
      List<Commande> l= (new CommandeDao()).findByLivreurEncours(livreur);
      JpaUtil.fermerEntityManager();
      return l;
  }
  
  public static List<Commande> getCommandes() throws Throwable
  {
      JpaUtil.creerEntityManager();
      List<Commande> l= (new CommandeDao()).findAll();
      JpaUtil.fermerEntityManager();
      return l;
  }
  
  public static List<Livreur> getLivreurs() throws Throwable
  {
      JpaUtil.creerEntityManager();
      List<Livreur> l= (new LivreurDao()).findAll();
      JpaUtil.fermerEntityManager();
      return l;
  }
  
  public static List<Livreur> getLivreursDispo() throws Throwable
  {
      JpaUtil.creerEntityManager();
      List<Livreur> l= (new LivreurDao()).findDispo();
      JpaUtil.fermerEntityManager();
      return l;
  }
  
  public static List<Livreur> getLivreursDispoPoids(double poids) throws Throwable
  {
      JpaUtil.creerEntityManager();
      List<Livreur> l= (new LivreurDao()).findDispoPoids(poids);
      JpaUtil.fermerEntityManager();
      return l;
  }
  
  public static List<Drone> getDrones() throws Throwable
  {
      JpaUtil.creerEntityManager();
      List<Livreur> l= (new LivreurDao()).findAll();
      List<Drone> d= new ArrayList<>();
      for(int i=0; i<l.size(); i++)
      {
          if(l.get(i) instanceof Drone)
          {
              d.add((Drone)l.get(i));
          }
      }
      JpaUtil.fermerEntityManager();
      return d; 
  }
  
  public static List<Velo> getVelos() throws Throwable
  {
      JpaUtil.creerEntityManager();
      List<Livreur> l= (new LivreurDao()).findAll();
      List<Velo> d= new ArrayList<>();
      for(int i=0; i<l.size(); i++)
      {
          if(l.get(i) instanceof Velo)
          {
              d.add((Velo)l.get(i));
          }
      }
      JpaUtil.fermerEntityManager();
      return d;  
  }
  
  public static Client findClientById(Long id) throws Throwable
  {
      JpaUtil.creerEntityManager();
      ClientDao clientDao = new ClientDao();
      Client c=clientDao.findById(id);
      JpaUtil.fermerEntityManager();
      return c;
  }
  
  public static Produit findProduitById(Long id) throws Throwable
  {
      JpaUtil.creerEntityManager();
      ProduitDao produitDao = new ProduitDao();
      Produit p=produitDao.findById(id);
      JpaUtil.fermerEntityManager();
      return p;
  }
  
  public static Livreur findLivreurById(Long id) throws Throwable
  {
      JpaUtil.creerEntityManager();
      LivreurDao livreurDao = new LivreurDao();
      Livreur livreur=livreurDao.findById(id);
      JpaUtil.fermerEntityManager();
      return livreur;
  }
  
  public static Restaurant findRestaurantById(Long id) throws Throwable
  {
      JpaUtil.creerEntityManager();
      RestaurantDao restaurantDao = new RestaurantDao();
      Restaurant r=restaurantDao.findById(id);
      JpaUtil.fermerEntityManager();
      return r;
  }
  
  public static List<Restaurant> listRestaurant() throws Throwable
  {
      JpaUtil.creerEntityManager();
      List<Restaurant> l= (new RestaurantDao()).findAll();
      JpaUtil.fermerEntityManager();
      return l;
  }
  
  public static List<Produit> listRestaurantProduit(Long idResto) throws Throwable
  {
      JpaUtil.creerEntityManager();
      List<Produit> l= (new RestaurantDao()).findById(idResto).getProduits();
      JpaUtil.fermerEntityManager();
      return l;
  }
  
  
  public static boolean validerCommande(Commande commande) throws Throwable
  {
      JpaUtil.creerEntityManager();
      if (ServiceTechnique.affecterLivreur(commande))
      {
          JpaUtil.creerEntityManager();
        CommandeDao commandeDao=new CommandeDao();
        ProduitQuantiteDao pQ= new ProduitQuantiteDao();
        //commande.getLivreur().setDisponible(false);
        commande.setDateCommande(new Date());
        JpaUtil.ouvrirTransaction();
        for(int i=0; i<commande.getListeProduits().size(); i++)
        {
            pQ.create(commande.getListeProduits().get(i));
        }
        commandeDao.create(commande);
        JpaUtil.validerTransaction();
         JpaUtil.fermerEntityManager();
         mailLivraison(commande);
        return true;
      }
       return false; 
  }
  
  public static boolean cloturerCommande(Commande commande) throws Throwable
  {
      if(commande != null)
        {
            JpaUtil.creerEntityManager();
            CommandeDao commandeDao= new CommandeDao();
            JpaUtil.ouvrirTransaction();
            commande.setDateLivraison(new Date());
            commande.getLivreur().setDisponible(true);
            commandeDao.update(commande);
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
            return true;
        }
      return false;
  }
  
  public static boolean cloturerCommandeId(Long idCommande) throws Throwable
  {
        JpaUtil.creerEntityManager();
        CommandeDao commandeDao=new CommandeDao();
        Commande commande=commandeDao.findById(idCommande);
        if(commande != null)
        {
            commande.setDateLivraison(new Date());
            commande.getLivreur().setDisponible(true);
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            commandeDao.update(commande);
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
            return true;
        }
        return false;
  }
  
  
  
  
  //service pour le manager
  
  
  
}
