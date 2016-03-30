/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import com.google.maps.model.LatLng;
import javax.persistence.Entity;
import util.GeoTest;

/**
 *
 * @author sbelletier
 */
@Entity
public class Drone extends Livreur {
 
    double vitesse;

    public Drone() {
        super();
    }
    public Drone(String adresse, String mail, String password, double vitesse, double capacite)
    {
        super(adresse, mail, password, capacite);
        this.vitesse=vitesse;
    }

    @Override
    public double dureeTrajet(LatLng adresseL, LatLng adresseR) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        double distance=GeoTest.getFlightDistanceInKm(new LatLng(this.latitude,longitude),  adresseR);
        distance+=GeoTest.getFlightDistanceInKm(adresseR, adresseL);
        
        double dureeTrajet=distance/vitesse;
        return dureeTrajet*60;
    }

    public double getVitesse() {
        return vitesse;
    }
    
}
