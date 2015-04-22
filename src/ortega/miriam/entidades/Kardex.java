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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "kardex")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kardex.findAll", query = "SELECT k FROM Kardex k"),
    @NamedQuery(name = "Kardex.findById", query = "SELECT k FROM Kardex k WHERE k.id = :id"),
    @NamedQuery(name = "Kardex.findByFecha", query = "SELECT k FROM Kardex k WHERE k.fecha = :fecha"),
    @NamedQuery(name = "Kardex.findByCantidad", query = "SELECT k FROM Kardex k WHERE k.cantidad = :cantidad"),
    @NamedQuery(name = "Kardex.findByPreciounitario", query = "SELECT k FROM Kardex k WHERE k.preciounitario = :preciounitario"),
    @NamedQuery(name = "Kardex.findByTotal", query = "SELECT k FROM Kardex k WHERE k.total = :total"),
    @NamedQuery(name = "Kardex.findByCompraVenta", query = "SELECT k FROM Kardex k WHERE k.compraVenta = :compraVenta"),
    @NamedQuery(name = "Kardex.findByCantidadTotal", query = "SELECT k FROM Kardex k WHERE k.cantidadTotal = :cantidadTotal"),
    @NamedQuery(name = "Kardex.findByPrecioUTotal", query = "SELECT k FROM Kardex k WHERE k.precioUTotal = :precioUTotal"),
    @NamedQuery(name = "Kardex.findByPrecioTotal", query = "SELECT k FROM Kardex k WHERE k.precioTotal = :precioTotal")})
public class Kardex implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "preciounitario")
    private BigDecimal preciounitario;
    @Column(name = "total")
    private BigDecimal total;
    @Column(name = "compraVenta")
    private Character compraVenta;
    @Basic(optional = false)
    @Column(name = "cantidadTotal")
    private int cantidadTotal;
    @Column(name = "precioUTotal")
    private BigDecimal precioUTotal;
    @Column(name = "precioTotal")
    private BigDecimal precioTotal;
    @JoinColumn(name = "idproducto", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Producto idproducto;
    @JoinColumn(name = "idfactura", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Factura idfactura;

    public Kardex() {
    }

    public Kardex(Long id) {
        this.id = id;
    }

    public Kardex(Long id, Date fecha, int cantidad, int cantidadTotal) {
        this.id = id;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.cantidadTotal = cantidadTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPreciounitario() {
        return preciounitario;
    }

    public void setPreciounitario(BigDecimal preciounitario) {
        this.preciounitario = preciounitario;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Character getCompraVenta() {
        return compraVenta;
    }

    public void setCompraVenta(Character compraVenta) {
        this.compraVenta = compraVenta;
    }

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public BigDecimal getPrecioUTotal() {
        return precioUTotal;
    }

    public void setPrecioUTotal(BigDecimal precioUTotal) {
        this.precioUTotal = precioUTotal;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Producto getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Producto idproducto) {
        this.idproducto = idproducto;
    }

    public Factura getIdfactura() {
        return idfactura;
    }

    public void setIdfactura(Factura idfactura) {
        this.idfactura = idfactura;
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
        if (!(object instanceof Kardex)) {
            return false;
        }
        Kardex other = (Kardex) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ortega.miriam.entidades.Kardex[ id=" + id + " ]";
    }
    
}
