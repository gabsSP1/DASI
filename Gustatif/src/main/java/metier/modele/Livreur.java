/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import com.google.maps.model.LatLng;
import dao.JpaUtil;
import dao.LivreurDao;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;
import javax.persistence.Version;
import util.GeoTest;

/**
 *
 * @author sbelletier
 */
@Entity
@Inheritance (strategy= InheritanceType.JOINED)
public abstract class Livreur implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    boolean disponible;
    protected String adresse;
    protected Double longitude;
    protected Double latitude;
    private String mail;
    private String password;
    protected Double capacite; 
    @Version
    @Transient
    long version;
    public Livreur()
    {
        
    }
    
    public Livreur(String adresse, String mail, String password, double capacite)
    {
        disponible=true;
        this.adresse=adresse;
        this.mail=mail;
        this.password=password;
        latitude=GeoTest.getLatLng(adresse).lat;
        longitude=GeoTest.getLatLng(adresse).lng;
        this.capacite=capacite;
    }
    
    public abstract double dureeTrajet(LatLng adresseR, LatLng adresseL);

    public Long getId() {
        return id;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public String getAdresse() {
        return adresse;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getEmail() {
        return mail;
    }

    public Double getCapacite() {
        return capacite;
    }
    

    public String getPassword() {
        return password;
    }

    public void setDisponible(boolean disponible) throws Throwable {
        this.disponible = disponible;
        JpaUtil.creerEntityManager();
        LivreurDao livreurDao=new LivreurDao();
        JpaUtil.ouvrirTransaction();
        livreurDao.update(this);
        JpaUtil.validerTransaction(); 
        JpaUtil.fermerEntityManager();
    }

    @Override
    public String toString() {
        return "Livreur{"+"Id="+id + " disponible=" + disponible + ", adresse=" + adresse + ", longitude=" + longitude + ", latitude=" + latitude + ", email=" + mail + ", password=" + password + "}\r\n";
    }
    
    
    
}
