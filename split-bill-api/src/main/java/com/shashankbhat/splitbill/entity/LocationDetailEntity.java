package com.shashankbhat.splitbill.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "location_detail")
@Data
@AllArgsConstructor
public class LocationDetailEntity {
    @Id
    private Integer uniqueId;
    private Double distanceRange;
    private Double latitude;
    private Double longitude;
    private Long dateCreated;
}
