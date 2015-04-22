package ortega.miriam.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ortega.miriam.entidades.Usuarios;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-21T20:38:36")
@StaticMetamodel(Rol.class)
public class Rol_ { 

    public static volatile CollectionAttribute<Rol, Usuarios> usuariosCollection;
    public static volatile SingularAttribute<Rol, Long> id;
    public static volatile SingularAttribute<Rol, String> nombre;

}