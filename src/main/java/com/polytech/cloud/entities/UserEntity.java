package com.polytech.cloud.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javax.persistence.*;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "user", catalog = "")
@JsonPropertyOrder({"id", "firstName", "lastName", "positionByFkPosition", "birthDay"})
public class UserEntity {

    private String id;
    private String firstName;
    private String lastName;
    private Date birthDay;
    private PositionEntity positionByFkPosition;

    public UserEntity()
    {
        this.id = UUID.randomUUID().toString();
    }

    @Id
    @Column(name = "id", nullable = false, length = 255)
    //@JsonFormat(shape = JsonFormat.Shape.STRING)
    public String getId() {
        return id;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 100)
    public String getFirstName() {
        return firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = false, length = 100)
    public String getLastName() {
        return lastName;
    }


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy", timezone = "UTC")
    @Basic
    @Column(name = "birth_day", nullable = false)
    public Date getBirthDay() {
        return birthDay;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonProperty("position")
    @JoinColumns(@JoinColumn(name = "fk_position", referencedColumnName = "id_position", nullable = false))
    public PositionEntity getPositionByFkPosition() {
        return positionByFkPosition;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(birthDay, that.birthDay) &&
                Objects.equals(positionByFkPosition, that.positionByFkPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDay, positionByFkPosition);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public void setPositionByFkPosition(PositionEntity positionByFkPosition) {
        this.positionByFkPosition = positionByFkPosition;
    }

    @Override
    public String toString()
    {
        return "UserEntity{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDay=" + birthDay +
                ", positionByFkPosition=" + positionByFkPosition +
                '}';
    }
}
