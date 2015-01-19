package ortega.miriam.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ortega.miriam.entidades.Entidad;
import ortega.miriam.entidades.Rol;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-17T11:47:02")
@StaticMetamodel(Usuarios.class)
public class Usuarios_ { 

    public static volatile SingularAttribute<Usuarios, Long> id;
    public static volatile SingularAttribute<Usuarios, String> username;
    public static volatile SingularAttribute<Usuarios, Entidad> entidadid;
    public static volatile SingularAttribute<Usuarios, Rol> rolid;
    public static volatile SingularAttribute<Usuarios, String> password;
    public static volatile SingularAttribute<Usuarios, Boolean> activo;

}