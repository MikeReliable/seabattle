package com.example.seaBattle.objects;

import javax.persistence.*;

@Entity
@Table
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fieldId;

    private byte coordinateX;
    private byte coordinateY;
    private byte status;

    public Field() {
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public byte getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(byte coordinateX) {
        this.coordinateX = coordinateX;
    }

    public byte getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(byte coordinateY) {
        this.coordinateY = coordinateY;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
