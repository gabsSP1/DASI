/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author sbelletier
 */
@Entity
public class Commande implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Date dateCommande;
    Date dateLivraison;
    @OneToMany(fetch= FetchType.EAGER)
    protected List<ProduitQuantite> listeProduits; 
    @ManyToOne
    Client commanditaire;
    @ManyToOne
    Livreur livreur;
    @ManyToOne
    Restaurant restaurant;

    public Commande() {
        //listeProduits = new ArrayList<>();
    }
    
    public Commande(Client commanditaire, Restaurant restaurant) {
        this.commanditaire = commanditaire;
        this.restaurant = restaurant;
        listeProduits=new ArrayList();
             
    }

    public Commande(List<ProduitQuantite> listeProduits, Client commanditaire, Restaurant restaurant) {
        this.listeProduits = listeProduits;
        this.commanditaire = commanditaire;
        this.restaurant = restaurant;
    }

    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public void setListeProduits(List<ProduitQuantite> listeProduits) {
        this.listeProduits = listeProduits;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
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

    public List<ProduitQuantite> getListeProduits() {
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
    
    public double getPoidsTotal()
    {
        double poids=0;
        for(int i=0; i<listeProduits.size(); i++)
        {
           poids+=listeProduits.get(i).getProduit().getPoids()*listeProduits.get(i).getQuantite();
        }
        return poids;
    }
    
    public double getPrixTotal()
    {
        double prix=0;
        for(int i=0; i<listeProduits.size(); i++)
        {
            prix+=listeProduits.get(i).getProduit().getPrix()*listeProduits.get(i).getQuantite();
        }
        return prix;
    }
   
}
