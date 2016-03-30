/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Commande;
import metier.modele.Livreur;

/**
 *
 * @author sbelletier
 */
public class CommandeDao {
    
      
    public void create(Commande commande) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(commande);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public Commande update(Commande commande) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            commande = em.merge(commande);
        }
        catch(Exception e){
            throw e;
        }
        return commande;
    }
    
    public Commande findById(Long id) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Commande commande = null;
        try {
            commande = em.find(Commande.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return commande;
    }
    
    public List<Commande> findAll() throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Commande> commande = null;
        try {
            Query q = em.createQuery("SELECT c FROM Commande c ORDER BY c.dateCommande");
            commande = (List<Commande>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }     
        return commande;
    }
    
     public List<Commande> findByLivreur(Livreur l) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Commande> commandes = null;
        try {
            Query q = em.createQuery("SELECT l FROM Commande l where Livreur_Id = :id");
            q.setParameter("id", l.getId());
            commandes = (List<Commande>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }     
        return commandes;
    }
     
     public List<Commande> findByLivreurEncours(Livreur l) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Commande> commandes = null;
        try {
            Query q = em.createQuery("SELECT l FROM Commande l where Livreur_Id = :id and dateLivraison = null");
            q.setParameter("id", l.getId());
            commandes = (List<Commande>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }     
        return commandes;
    }
     
     
    
}
