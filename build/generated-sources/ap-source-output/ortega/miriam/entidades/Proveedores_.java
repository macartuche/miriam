package ortega.miriam.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ortega.miriam.entidades.Entidad;
import ortega.miriam.entidades.Factura;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-23T21:01:25")
@StaticMetamodel(Proveedores.class)
public class Proveedores_ { 

    public static volatile SingularAttribute<Proveedores, String> descripcion;
    public static volatile ListAttribute<Proveedores, Factura> facturaList;
    public static volatile SingularAttribute<Proveedores, Long> id;
    public static volatile SingularAttribute<Proveedores, Entidad> entidadid;
    public static volatile SingularAttribute<Proveedores, Boolean> activo;

}