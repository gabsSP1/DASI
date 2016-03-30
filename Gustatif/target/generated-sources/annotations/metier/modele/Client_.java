package metier.modele;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Client.class)
public abstract class Client_ {

	public static volatile SingularAttribute<Client, String> prenom;
	public static volatile SingularAttribute<Client, Long> id;
	public static volatile SingularAttribute<Client, String> mail;
	public static volatile SingularAttribute<Client, String> adresse;
	public static volatile SingularAttribute<Client, Double> longitude;
	public static volatile SingularAttribute<Client, Double> latitude;
	public static volatile SingularAttribute<Client, String> nom;

}

