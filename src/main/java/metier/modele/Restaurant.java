package metier.modele;

import com.google.maps.model.LatLng;
import dao.JpaUtil;
import dao.LivreurDao;
import dao.RestaurantDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import metier.service.ServiceTechnique;
import util.GeoTest;

@Entity
public class Restaurant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String denomination;
    private String description;
    private String adresse;
    private Double longitude;
    private Double latitude;

    @OneToMany
    private List<Produit> produits;
    
    public Restaurant() {
        produits = new ArrayList<>();
    }

    public Restaurant(String denomination, String description, String adresse) {
        this.denomination = denomination;
        this.description = description;
        this.adresse = adresse;
        produits = new ArrayList<>();
        LatLng latLng = GeoTest.getLatLng(adresse);
        this.longitude = latLng.lng;
        this.latitude = latLng.lat;
    }

    public Long getId() {
        return id;
    }

    public String getDenomination() {
        return denomination;
    }

    public String getDescription() {
        return description;
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

    public List<Produit> getProduits() {
        return produits;
    }

    public void addProduit(Produit produit) throws Throwable {
        this.produits.add(produit);
        RestaurantDao restoDao=new RestaurantDao();
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        restoDao.update(this);
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();  
    }

    @Override
    public String toString() {
        return "Restaurant{" + "id=" + id + ", denomination=" + denomination + ", description=" + description + ", adresse=" + adresse + ", longitudeAdresse=" + longitude + ", latitudeAdresse=" + latitude + ", produits=" + produits + '}';
    }
    
    
}
