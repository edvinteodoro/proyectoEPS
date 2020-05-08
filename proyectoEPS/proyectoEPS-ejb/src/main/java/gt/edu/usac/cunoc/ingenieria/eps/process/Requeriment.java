package gt.edu.usac.cunoc.ingenieria.eps.process;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author teodoro
 */
@Entity
@Table(name = "REQUERIMENT")
@NamedQueries({
    @NamedQuery(name = "Requeriment.findAll", query = "SELECT r FROM Requeriment r")})
public class Requeriment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "writtenRequest")
    private byte[] writtenRequest;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "inscriptionConstancy")
    private byte[] inscriptionConstancy;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "pensumClosure")
    private byte[] pensumClosure;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "propedeuticConstancy")
    private byte[] propedeuticConstancy;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "EPSpreproject")
    private byte[] ePSpreproject;
    @Lob
    @Column(name = "AEIOsettlement")
    private byte[] aEIOsettlement;
    @OneToOne
    @JoinColumn(name="PROCESS_id",referencedColumnName = "id")
    private Process pROCESSid;

    public Requeriment() {
    }

    public Requeriment(Integer id) {
        this.id = id;
    }

    public Requeriment(Integer id, byte[] writtenRequest, byte[] inscriptionConstancy, byte[] pensumClosure, byte[] propedeuticConstancy, byte[] ePSpreproject) {
        this.id = id;
        this.writtenRequest = writtenRequest;
        this.inscriptionConstancy = inscriptionConstancy;
        this.pensumClosure = pensumClosure;
        this.propedeuticConstancy = propedeuticConstancy;
        this.ePSpreproject = ePSpreproject;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getWrittenRequest() {
        return writtenRequest;
    }

    public void setWrittenRequest(byte[] writtenRequest) {
        this.writtenRequest = writtenRequest;
    }

    public byte[] getInscriptionConstancy() {
        return inscriptionConstancy;
    }

    public void setInscriptionConstancy(byte[] inscriptionConstancy) {
        this.inscriptionConstancy = inscriptionConstancy;
    }

    public byte[] getPensumClosure() {
        return pensumClosure;
    }

    public void setPensumClosure(byte[] pensumClosure) {
        this.pensumClosure = pensumClosure;
    }

    public byte[] getPropedeuticConstancy() {
        return propedeuticConstancy;
    }

    public void setPropedeuticConstancy(byte[] propedeuticConstancy) {
        this.propedeuticConstancy = propedeuticConstancy;
    }

    public byte[] getEPSpreproject() {
        return ePSpreproject;
    }

    public void setEPSpreproject(byte[] ePSpreproject) {
        this.ePSpreproject = ePSpreproject;
    }

    public byte[] getAEIOsettlement() {
        return aEIOsettlement;
    }

    public void setAEIOsettlement(byte[] aEIOsettlement) {
        this.aEIOsettlement = aEIOsettlement;
    }

    public byte[] getePSpreproject() {
        return ePSpreproject;
    }

    public void setePSpreproject(byte[] ePSpreproject) {
        this.ePSpreproject = ePSpreproject;
    }

    public byte[] getaEIOsettlement() {
        return aEIOsettlement;
    }

    public void setaEIOsettlement(byte[] aEIOsettlement) {
        this.aEIOsettlement = aEIOsettlement;
    }

    public Process getpROCESSid() {
        return pROCESSid;
    }

    public void setpROCESSid(Process pROCESSid) {
        this.pROCESSid = pROCESSid;
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
        if (!(object instanceof Requeriment)) {
            return false;
        }
        Requeriment other = (Requeriment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.usac.cunoc.ingenieria.eps.process.Requeriment[ id=" + id + " ]";
    }
    
}
