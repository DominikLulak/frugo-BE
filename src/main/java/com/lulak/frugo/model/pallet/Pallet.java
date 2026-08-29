package com.lulak.frugo.model.pallet;

import com.lulak.frugo.model.warehouse.Location;
import jakarta.persistence.*;

@Entity
@Table(name = "pallet")
public class Pallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "eti_number", nullable = false, unique = true)
    private String palletNumber;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "is_closed", nullable = false)
    private boolean closed;

    public Integer getId(){ return id; }

    public String getPalletNumber(){ return palletNumber; }
    public void setPalletNumber(String palletNumber){ this.palletNumber = palletNumber; }

    public Location getLocation(){ return location; }
    public void setLocation(Location location){ this.location = location; }

    public boolean isClosed(){ return closed; }
    public void setClosed(boolean closed){ this.closed = closed; }
}
