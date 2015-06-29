/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ortega.miriam.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "abonos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Abonos.findAll", query = "SELECT a FROM Abonos a"),
    @NamedQuery(name = "Abonos.findById", query = "SELECT a FROM Abonos a WHERE a.id = :id"),
    @NamedQuery(name = "Abonos.findByFecha", query = "SELECT a FROM Abonos a WHERE a.fecha = :fecha"),
    @NamedQuery(name = "Abonos.findByValor", query = "SELECT a FROM Abonos a WHERE a.valor = :valor"),
    @NamedQuery(name = "Abonos.findBySaldo", query = "SELECT a FROM Abonos a WHERE a.saldo = :saldo"),
    @NamedQuery(name = "Abonos.findByCuenta", query = "SELECT a FROM Abonos a WHERE a.cuenta = :cuenta")})
public class Abonos implements Serializable {
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
    @Column(name = "valor")
    private BigDecimal valor;
    @Basic(optional = false)
    @Column(name = "saldo")
    private BigDecimal saldo;

    
    @ManyToOne(fetch=FetchType.LAZY)
    private CuentasCxP cuenta;

    public Abonos() {
    }

    public Abonos(Integer id) {
        this.id = id;
    }

    public Abonos(Integer id, Date fecha, BigDecimal valor, 
            BigDecimal saldo, CuentasCxP cuenta) {
        this.id = id;
        this.fecha = fecha;
        this.valor = valor;
        this.saldo = saldo;
        this.cuenta = cuenta;
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

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public CuentasCxP getCuenta() {
        return cuenta;
    }

    public void setCuenta(CuentasCxP cuenta) {
        this.cuenta = cuenta;
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
        if (!(object instanceof Abonos)) {
            return false;
        }
        Abonos other = (Abonos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ortega.miriam.entidades.Abonos[ id=" + id + " ]";
    }
    
}
