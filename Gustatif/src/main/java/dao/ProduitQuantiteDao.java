/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.EntityManager;
import metier.modele.ProduitQuantite;

/**
 *
 * @author Gabriel
 */
public class ProduitQuantiteDao {
    public void create(ProduitQuantite produitQ) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(produitQ);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public ProduitQuantite update(ProduitQuantite produitQ) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            produitQ = em.merge(produitQ);
        }
        catch(Exception e){
            throw e;
        }
        return produitQ;
    }
}
