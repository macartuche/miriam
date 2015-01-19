package ortega.miriam.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ortega.miriam.entidades.Clientes;
import ortega.miriam.entidades.Proveedores;
import ortega.miriam.entidades.Usuarios;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-17T11:47:02")
@StaticMetamodel(Entidad.class)
public class Entidad_ { 

    public static volatile SingularAttribute<Entidad, Long> id;
    public static volatile SingularAttribute<Entidad, String> identificacion;
    public static volatile SingularAttribute<Entidad, String> direccion;
    public static volatile SingularAttribute<Entidad, String> nombres;
    public static volatile SingularAttribute<Entidad, String> telefono;
    public static volatile CollectionAttribute<Entidad, Proveedores> proveedoresCollection;
    public static volatile CollectionAttribute<Entidad, Usuarios> usuariosCollection;
    public static volatile SingularAttribute<Entidad, String> correo;
    public static volatile CollectionAttribute<Entidad, Clientes> clientesCollection;

}