package com.satisfai.util;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
public class GrouponDealsEmail {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long userId;
    private String emailAddress;
    private UUID dealUuid;
    private Timestamp purchaseDate;
    private double purchaseAmount;
    private short lob;
    private short refundFlag;
    private short recencySegment;
    private short frequencySegment;

}
