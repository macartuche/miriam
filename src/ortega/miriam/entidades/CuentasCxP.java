/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ortega.miriam.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "cuentasCxP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuentasCxP.findAll", query = "SELECT c FROM CuentasCxP c"),
    @NamedQuery(name = "CuentasCxP.findById", query = "SELECT c FROM CuentasCxP c WHERE c.id = :id"),
    @NamedQuery(name = "CuentasCxP.findByFecha", query = "SELECT c FROM CuentasCxP c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "CuentasCxP.findByTotal", query = "SELECT c FROM CuentasCxP c WHERE c.total = :total"),
    @NamedQuery(name = "CuentasCxP.findByEstado", query = "SELECT c FROM CuentasCxP c WHERE c.estado = :estado"),
    @NamedQuery(name = "CuentasCxP.findByTipo", query = "SELECT c FROM CuentasCxP c WHERE c.tipo = :tipo"),
    @NamedQuery(name = "CuentasCxP.findByFacturaId", query = "SELECT c FROM CuentasCxP c WHERE c.facturaId = :facturaId")})
public class CuentasCxP implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "total")
    private BigDecimal total;
    @Basic(optional = false)
    @Column(name = "saldo")
    private BigDecimal saldo;
        
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;

    @OneToOne(fetch = FetchType.LAZY)
    private Factura facturaId;

    @OneToMany(mappedBy = "cuenta", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Abonos> abonos;

    public CuentasCxP() {
    }

    public CuentasCxP(Integer id) {
        this.id = id;
    }

    public CuentasCxP(Integer id, Date fecha, BigDecimal total,
            String estado, String tipo, Factura facturaId) {
        this.id = id;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.tipo = tipo;
        this.facturaId = facturaId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Factura getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Factura facturaId) {
        this.facturaId = facturaId;
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
        if (!(object instanceof CuentasCxP)) {
            return false;
        }
        CuentasCxP other = (CuentasCxP) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ortega.miriam.entidades.CuentasCxP[ id=" + id + " ]";
    }

    public List<Abonos> getAbonos() {
        return abonos;
    }

    public void setAbonos(List<Abonos> abonos) {
        this.abonos = abonos;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    } 
}
