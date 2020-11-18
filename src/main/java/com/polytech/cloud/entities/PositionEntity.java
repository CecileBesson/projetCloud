package com.polytech.cloud.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "position", schema = "cloud_equipe_e", catalog = "")
public class PositionEntity {
    private int idPosition;
    private BigDecimal lat;
    private BigDecimal lon;

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_position", nullable = false)
    public int getIdPosition() {
        return idPosition;
    }

    @Basic
    @Column(name = "lat", nullable = false, precision = 0)
    public BigDecimal getLat() {
        return lat;
    }


    @Basic
    @Column(name = "lon", nullable = false, precision = 0)
    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public void setIdPosition(int idPosition) {
        this.idPosition = idPosition;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionEntity that = (PositionEntity) o;
        return idPosition == that.idPosition &&
                Objects.equals(lat, that.lat) &&
                Objects.equals(lon, that.lon);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(idPosition, lat, lon);
    }

    @Override
    public String toString()
    {
        return "PositionEntity{" +
                "idPosition=" + idPosition +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
