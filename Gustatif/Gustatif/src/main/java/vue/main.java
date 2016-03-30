/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;
import metier.modele.*;
import dao.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.service.ServiceTechnique;
import metier.modele.Livreur;
//import metier.service.*;
/**
 *
 * @author gspecq
 */
public class main {
    
    public static void main(String[] args)
    {
        
        //créer clinet
       try {
            ClientDao clientDao=new ClientDao();
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            Client c=new Client ("Dupont", "jean", "20 Avenue Albert Einstein Villeurbanne", "jdupont@insa-lyon.fr", "123" );
             Client c2=new Client ("Dupont", "jean", "3 rue du chat", "jdupont@insa-lyon.fr","123" );
            clientDao.create(c);
            clientDao.create(c2);
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
        } catch (Throwable ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        //consulter client
        try {
            ClientDao clientDao=new ClientDao();
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            List<Client> l=clientDao.findAll();
            Client c = clientDao.findByEmail("jdupont@insa-lyon.fr").get(0);
            System.out.println(l.toString());
            System.out.println(c.toString());
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
        } catch (Throwable ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
          try {
            RestaurantDao restaurantDao=new RestaurantDao();
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            Produit p=new Produit("spaghetti bolo", "blabla",(float)150,(float)150.0);
            Restaurant r=new Restaurant("La fourchette de cuivre", "bon resto", "50 avenue normandie niemen");
            Restaurant r2=new Restaurant("La fourchette de bronze", "bon resto", "51 avenue normandie niemen");
            ProduitDao produitsDao=new ProduitDao();
            produitsDao.create(p);
            r.addProduit(p);
            Produit p2=new Produit("spaghetti carbo", "blabla",(float)150,(float)150.0);
            produitsDao.create(p2);
            r.addProduit(p2);
            restaurantDao.create(r);
            Produit p3 = new Produit("spaghetti basilic","blibli",(float)200,(float)20.0);
            produitsDao.create(p3);
            r2.addProduit(p3);
            restaurantDao.create(r2);
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
        } catch (Throwable ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
          
          
          try {
            RestaurantDao restaurantDao=new RestaurantDao();
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            List<Restaurant> l=restaurantDao.findAll();
            System.out.println(l.toString());
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
        } catch (Throwable ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
          try {
            LivreurDao livreurDao=new LivreurDao();
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            Livreur c=new Drone ("3 avenue Albert Einstein", "salut.specq@insa-lyon.fr", "1234", 150.0 ,15.0);
            livreurDao.create(c);
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
        } catch (Throwable ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        //consulter client
        try {
            LivreurDao livreurDao=new LivreurDao();
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            List<Livreur> l=livreurDao.findAll();
            System.out.println(l.toString());
            System.out.println((l.get(0)) instanceof Drone );
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
        } catch (Throwable ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
     System.exit(0);    
    }
    
}
