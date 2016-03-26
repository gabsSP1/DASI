/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import dao.ClientDao;
import dao.CommandeDao;
import dao.JpaUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author sbelletier
 */
public class Commande implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Date dateCommande;
    Date dateLivraison;
    @ManyToMany(mappedBy="id")
    protected Map<Produit, Integer> listeProduits; 
    @ManyToOne
    Client commanditaire;
    @ManyToOne
    Livreur livreur;
    @ManyToOne
    Restaurant restaurant;
    boolean fini;
    double prixTotal;
    double poidsTotal;
    
    public Commande(Client commanditaire, Restaurant restaurant) {
        this.commanditaire = commanditaire;
        this.restaurant = restaurant;
        fini=false;
        listeProduits=new HashMap<Produit, Integer>();
        
        
    }
    
    
    public void setProduit(Produit p, int quantite)
    {
        if(listeProduits.containsKey(p))
        {
            poidsTotal-=listeProduits.get(p)*p.getPoids();
            prixTotal-=listeProduits.get(p)*p.getPrix();
        }
        listeProduits.put(p, quantite);
        poidsTotal+=quantite*p.getPoids();
        prixTotal+=quantite*p.getPrix();
    }
    
    public void supprimerProduit(Produit p)
    {
        poidsTotal-=listeProduits.get(p)*p.getPoids();
        prixTotal-=listeProduits.get(p)*p.getPrix();
        listeProduits.remove(p);
    }
    
    public void terminerCommande(Date fin) throws Throwable
    {
        this.dateLivraison=fin;
        this.fini=true;
        CommandeDao commandeDao=new CommandeDao();
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        commandeDao.update(this);
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
    }
 
    
    public void storeCommande() throws Throwable
    {
        CommandeDao commandeDao=new CommandeDao();
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        commandeDao.create(this);
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();  
    }

    public Long getId() {
        return id;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public Date getDateLivraison() {
        return dateLivraison;
    }

    public Map<Produit, Integer> getListeProduits() {
        return listeProduits;
    }

    public Client getCommanditaire() {
        return commanditaire;
    }

    public Livreur getLivreur() {
        return livreur;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public boolean isFini() {
        return fini;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public double getPoidsTotal() {
        return poidsTotal;
    }

    
    
}
