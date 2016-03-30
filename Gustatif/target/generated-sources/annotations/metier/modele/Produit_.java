package metier.modele;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Produit.class)
public abstract class Produit_ {

	public static volatile SingularAttribute<Produit, Long> id;
	public static volatile SingularAttribute<Produit, Float> prix;
	public static volatile SingularAttribute<Produit, String> description;
	public static volatile SingularAttribute<Produit, Float> poids;
	public static volatile SingularAttribute<Produit, String> denomination;

}

