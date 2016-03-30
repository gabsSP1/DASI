package metier.modele;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Restaurant.class)
public abstract class Restaurant_ {

	public static volatile SingularAttribute<Restaurant, Long> id;
	public static volatile SingularAttribute<Restaurant, String> adresse;
	public static volatile ListAttribute<Restaurant, Produit> produits;
	public static volatile SingularAttribute<Restaurant, String> description;
	public static volatile SingularAttribute<Restaurant, Double> longitude;
	public static volatile SingularAttribute<Restaurant, Double> latitude;
	public static volatile SingularAttribute<Restaurant, String> denomination;

}

