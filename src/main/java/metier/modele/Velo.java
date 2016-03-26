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
public class Velo extends Livreur{

    public Velo() {
        super();
    }

    
    
    public Velo(String adresse, String mail, String password, double capacite) {
        super(adresse, mail, password, capacite);
    }
    


    @Override
    public double dureeTrajet(LatLng adresseR, LatLng adresseL) {
        return GeoTest.getTripDurationByBicycleInMinute(new LatLng(this.latitude,longitude), adresseL, adresseR);
    }
    
}
