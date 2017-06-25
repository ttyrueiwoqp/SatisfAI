package com.satisfai.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Lu Fangjian
 */
public class Converter {

    private static final int BATCH_LIMIT = 500;
    private static final String FILE_PATH = "C:\\Users\\lvfan\\Downloads\\";

    // TODO To run a new file, comment off the previous file and add the new file
//    private static final String FILE_NAME = "20170524_001841_1495577921643_cs_na_desk.output";
    private static final String FILE_NAME = "20170524_003039_1495578639692_cs_na_desk.output";

    private static final String url = "jdbc:postgresql://35.188.61.177:5432/groupon_2016";
    private static final String user = "postgres";
    // TODO Enter password in your local
    private static final String password = "";

    private static final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
            .create();

    public static void main(String[] args) throws IOException {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(true);
            ps = con.prepareStatement(SQL);

            JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(FILE_PATH + FILE_NAME)));
            reader.beginArray();

            int i = 0;
            while (reader.hasNext()) {
                GrouponTickets tickets = gson.fromJson(reader, GrouponTickets.class);
                buildPrepareStatement(ps, tickets);
                ps.addBatch();
                i++;
                if (i % BATCH_LIMIT == 0) {
                    ps.executeBatch();
                    System.out.println("Executed Batch " + (i / BATCH_LIMIT));
                    ps.clearBatch();
                }
                ps.clearParameters();
            }
            reader.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.executeBatch();
                    System.out.println("Executed Final Batch");
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static final String SQL = "INSERT INTO groupon_tickets " +
            "(tickets_id," +
            " tickets_assignee_id," +
            " tickets_created_at," +
            " tickets_custom_field_20180577," +
            " tickets_custom_field_21024293," +
            " tickets_custom_field_22526998," +
            " tickets_custom_field_24112003," +
            " tickets_custom_field_27311858," +
            " tickets_custom_field_505895," +
            " tickets_description," +
            " tickets_group_id," +
            " tickets_requester_id," +
            " tickets_satisfaction_rating_score," +
            " tickets_subject," +
            " tickets_tags," +
            " tickets_updated_at," +
            " metric_sets_agent_wait_time_in_minutes_business," +
            " metric_sets_agent_wait_time_in_minutes_calendar," +
            " metric_sets_assigned_at," +
            " metric_sets_assignee_stations," +
            " metric_sets_assignee_updated_at," +
            " metric_sets_created_at," +
            " metric_sets_first_resolution_time_in_minutes_business," +
            " metric_sets_first_resolution_time_in_minutes_calendar," +
            " metric_sets_full_resolution_time_in_minutes_business," +
            " metric_sets_full_resolution_time_in_minutes_calendar," +
            " metric_sets_group_stations," +
            " metric_sets_initially_assigned_at," +
            " metric_sets_latest_comment_added_at," +
            " metric_sets_on_hold_time_in_minutes_business," +
            " metric_sets_on_hold_time_in_minutes_calendar," +
            " metric_sets_reopens," +
            " metric_sets_replies," +
            " metric_sets_reply_time_in_minutes_business," +
            " metric_sets_reply_time_in_minutes_calendar," +
            " metric_sets_requester_updated_at," +
            " metric_sets_requester_wait_time_in_minutes_business," +
            " metric_sets_requester_wait_time_in_minutes_calendar," +
            " metric_sets_solved_at," +
            " metric_sets_status_updated_at," +
            " metric_sets_updated_at)" +
            " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static void buildPrepareStatement(PreparedStatement ps, GrouponTickets tickets) throws SQLException {
        int idx = 0;
        ps.setLong(++idx, tickets.getTicketsId());
        ps.setLong(++idx, tickets.getTicketsAssigneeId());
        ps.setTimestamp(++idx, tickets.getTicketsCreatedAt());
        ps.setString(++idx, tickets.getTicketsCustomField_20180577());
        ps.setString(++idx, tickets.getTicketsCustomField_21024293());
        ps.setString(++idx, tickets.getTicketsCustomField_22526998());
        ps.setString(++idx, tickets.getTicketsCustomField_24112003());
        ps.setString(++idx, tickets.getTicketsCustomField_27311858());
        ps.setString(++idx, tickets.getTicketsCustomField_505895());
        ps.setString(++idx, tickets.getTicketsDescription());
        ps.setLong(++idx, tickets.getTicketsGroupId());
        ps.setLong(++idx, tickets.getTicketsRequesterId());
        ps.setString(++idx, tickets.getTicketsSatisfactionRatingScore());
        ps.setString(++idx, tickets.getTicketsSubject());
        ps.setString(++idx, tickets.getTicketsTags());
        ps.setTimestamp(++idx, tickets.getTicketsUpdatedAt());
        ps.setInt(++idx, tickets.getMetricSetsAgentWaitTimeInMinutesBusiness());
        ps.setInt(++idx, tickets.getMetricSetsAgentWaitTimeInMinutesCalendar());
        ps.setTimestamp(++idx, tickets.getMetricSetsAssignedAt());
        ps.setInt(++idx, tickets.getMetricSetsAssigneeStations());
        ps.setTimestamp(++idx, tickets.getMetricSetsAssigneeUpdatedAt());
        ps.setTimestamp(++idx, tickets.getTicketsCreatedAt());
        ps.setInt(++idx, tickets.getMetricSetsFirstResolutionTimeInMinutesBusiness());
        ps.setInt(++idx, tickets.getMetricSetsFirstResolutionTimeInMinutesCalendar());
        ps.setInt(++idx, tickets.getMetricSetsFullResolutionTimeInMinutesBusiness());
        ps.setInt(++idx, tickets.getMetricSetsFullResolutionTimeInMinutesCalendar());
        ps.setInt(++idx, tickets.getMetricSetsGroupStations());
        ps.setTimestamp(++idx, tickets.getMetricSetsInitiallyAssignedAt());
        ps.setTimestamp(++idx, tickets.getMetricSetsLatestCommentAddedAt());
        ps.setInt(++idx, tickets.getMetricSetsOnHoldTimeInMinutesBusiness());
        ps.setInt(++idx, tickets.getMetricSetsOnHoldTimeInMinutesCalendar());
        ps.setInt(++idx, tickets.getMetricSetsReopens());
        ps.setInt(++idx, tickets.getMetricSetsReplies());
        ps.setInt(++idx, tickets.getMetricSetsReplyTimeInMinutesBusiness());
        ps.setInt(++idx, tickets.getMetricSetsReplyTimeInMinutesCalendar());
        ps.setTimestamp(++idx, tickets.getMetricSetsRequesterUpdatedAt());
        ps.setInt(++idx, tickets.getMetricSetsRequesterWaitTimeInMinutesBusiness());
        ps.setInt(++idx, tickets.getMetricSetsRequesterWaitTimeInMinutesCalendar());
        ps.setTimestamp(++idx, tickets.getMetricSetsSolvedAt());
        ps.setTimestamp(++idx, tickets.getMetricSetsStatusUpdatedAt());
        ps.setTimestamp(++idx, tickets.getMetricSetsUpdatedAt());
    }

}
