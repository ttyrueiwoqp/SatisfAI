package com.satisfai.util;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author Lu Fangjian
 */
@Data
@Entity
public class GrouponTickets {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long ticketsId;
    private int metricSetsAgentWaitTimeInMinutesBusiness;
    private int metricSetsAgentWaitTimeInMinutesCalendar;
    private Timestamp metricSetsAssignedAt;
    private int metricSetsAssigneeStations;
    private Timestamp metricSetsAssigneeUpdatedAt;
    private Timestamp metricSetsCreatedAt;
    private int metricSetsFirstResolutionTimeInMinutesBusiness;
    private int metricSetsFirstResolutionTimeInMinutesCalendar;
    private int metricSetsFullResolutionTimeInMinutesBusiness;
    private int metricSetsFullResolutionTimeInMinutesCalendar;
    private int metricSetsGroupStations;
    private Timestamp metricSetsInitiallyAssignedAt;
    private Timestamp metricSetsLatestCommentAddedAt;
    private int metricSetsOnHoldTimeInMinutesBusiness;
    private int metricSetsOnHoldTimeInMinutesCalendar;
    private int metricSetsReopens;
    private int metricSetsReplies;
    private int metricSetsReplyTimeInMinutesBusiness;
    private int metricSetsReplyTimeInMinutesCalendar;
    private Timestamp metricSetsRequesterUpdatedAt;
    private int metricSetsRequesterWaitTimeInMinutesBusiness;
    private int metricSetsRequesterWaitTimeInMinutesCalendar;
    private Timestamp metricSetsSolvedAt;
    private Timestamp metricSetsStatusUpdatedAt;
    private Timestamp metricSetsUpdatedAt;
    private long ticketsAssigneeId;
    private Timestamp ticketsCreatedAt;
    private String ticketsCustomField_20180577;
    private String ticketsCustomField_21024293;
    private String ticketsCustomField_22526998;
    private String ticketsCustomField_24112003;
    private String ticketsCustomField_27311858;
    private String ticketsCustomField_505895;
    private String ticketsDescription;
    private long ticketsGroupId;
    private long ticketsRequesterId;
    private String ticketsSatisfactionRatingScore;
    private String ticketsSubject;
    private String ticketsTags;
    private Timestamp ticketsUpdatedAt;
}
