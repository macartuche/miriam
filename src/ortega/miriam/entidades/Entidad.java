/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ortega.miriam.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "entidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entidad.findAll", query = "SELECT e FROM Entidad e"),
    @NamedQuery(name = "Entidad.findById", query = "SELECT e FROM Entidad e WHERE e.id = :id"),
    @NamedQuery(name = "Entidad.findByNombres", query = "SELECT e FROM Entidad e WHERE e.nombres = :nombres"),
    @NamedQuery(name = "Entidad.findByIdentificacion", query = "SELECT e FROM Entidad e WHERE e.identificacion = :identificacion"),
    @NamedQuery(name = "Entidad.findByCorreo", query = "SELECT e FROM Entidad e WHERE e.correo = :correo"),
    @NamedQuery(name = "Entidad.findByTelefono", query = "SELECT e FROM Entidad e WHERE e.telefono = :telefono"),
    @NamedQuery(name = "Entidad.findByDireccion", query = "SELECT e FROM Entidad e WHERE e.direccion = :direccion")})
public class Entidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "nombres")
    private String nombres;
    @Column(name = "identificacion")
    private String identificacion;
    @Column(name = "correo")
    private String correo;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "direccion")
    private String direccion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidadid")
    private Collection<Clientes> clientesCollection;
    @OneToMany(mappedBy = "entidadid")
    private Collection<Proveedores> proveedoresCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidadid")
    private Collection<Usuarios> usuariosCollection;

    public Entidad() {
    }

    public Entidad(Long id) {
        this.id = id;
    }

    public Entidad(Long id, String nombres) {
        this.id = id;
        this.nombres = nombres;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @XmlTransient
    public Collection<Clientes> getClientesCollection() {
        return clientesCollection;
    }

    public void setClientesCollection(Collection<Clientes> clientesCollection) {
        this.clientesCollection = clientesCollection;
    }

    @XmlTransient
    public Collection<Proveedores> getProveedoresCollection() {
        return proveedoresCollection;
    }

    public void setProveedoresCollection(Collection<Proveedores> proveedoresCollection) {
        this.proveedoresCollection = proveedoresCollection;
    }

    @XmlTransient
    public Collection<Usuarios> getUsuariosCollection() {
        return usuariosCollection;
    }

    public void setUsuariosCollection(Collection<Usuarios> usuariosCollection) {
        this.usuariosCollection = usuariosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entidad)) {
            return false;
        }
        Entidad other = (Entidad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ortega.miriam.entidades.Entidad[ id=" + id + " ]";
    }
    
}
