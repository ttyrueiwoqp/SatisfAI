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

    // TODO Change this to your folder
    private static final String FILE_PATH = "C:\\Users\\lvfan\\Downloads\\";

    // TODO To run a new file, comment off the previous file and add the new file
    // 1-10 done
//    private static final String FILE_NAME = "20170524_001841_1495577921643_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_003039_1495578639692_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_004307_1495579387893_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_005620_1495580180385_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_011053_1495581053694_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_012503_1495581903320_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_013918_1495582758729_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_015329_1495583609819_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_020748_1495584468813_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_022156_1495585316409_cs_na_desk.output";

    // 11-20 done
//    private static final String FILE_NAME = "20170524_023602_1495586162084_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_025009_1495587009340_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_030415_1495587855991_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_031824_1495588704718_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_033226_1495589546558_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_034625_1495590385680_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_040035_1495591235652_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_041448_1495592088826_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_042849_1495592929653_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_044300_1495593780589_cs_na_desk.output";

    // 21-30 done
//    private static final String FILE_NAME = "20170524_045716_1495594636320_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_051129_1495595489764_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_052553_1495596353200_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_054014_1495597214075_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_055431_1495598071150_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_060846_1495598926652_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_062303_1495599783691_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_063720_1495600640524_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_065133_1495601493442_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_070549_1495602349586_cs_na_desk.output";

    // 31-40 done
//    private static final String FILE_NAME = "20170524_071959_1495603199713_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_073418_1495604058434_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_074836_1495604916982_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_080258_1495605778822_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_081710_1495606630251_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_083142_1495607502519_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_084619_1495608379145_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_090105_1495609265668_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_091552_1495610152971_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_093041_1495611041689_cs_na_desk.output";

    // 41-50 done except Invalid 20170524_162603_1495635963984_cs_na_desk.output
//    private static final String FILE_NAME = "20170524_094526_1495611926571_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_100012_1495612812111_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_101450_1495613690457_cs_na_desk.output";
    private static final String FILE_NAME = "20170524_162603_1495635963984_cs_na_desk.output";  // Too many invalid entries with "metric item is missing - ZD API BUG", skipped
//    private static final String FILE_NAME = "20170524_164010_1495636810822_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_165418_1495637658866_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_170736_1495638456345_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_172111_1495639271054_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_173232_1495639952457_cs_na_desk.output";
//    private static final String FILE_NAME = "20170524_173801_1495640281955_cs_na_desk.output";

    // TODO Go to Google Cloud console > Access Control > Authorization, add your IP there
    private static final String url = "jdbc:postgresql://35.188.61.177:5432/groupon_2016";
    private static final String user = "postgres";

    // TODO Enter password here, never commit this
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
            ps = con.prepareStatement(INSERT);

            JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(FILE_PATH + FILE_NAME)));
            reader.beginArray();

            int i = 0;
            while (reader.hasNext()) {
                GrouponTickets tickets = gson.fromJson(reader, GrouponTickets.class);
                i++;

                buildPrepareStatement(ps, tickets);
                ps.addBatch();
                if (i % BATCH_LIMIT == 0) {
                    try {
                        System.out.println("Executing Batch " + (i / BATCH_LIMIT));
                        long st = System.currentTimeMillis();
                        ps.executeBatch();
                        System.out.println("Executed Batch " + (i / BATCH_LIMIT) + " in " + (System.currentTimeMillis() - st) + "ms");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    ps.clearBatch();
                }
                ps.clearParameters();
            }

            if (i % BATCH_LIMIT != 0) {
                try {
                    System.out.println("Executing Final Batch");
                    long st = System.currentTimeMillis();
                    ps.executeBatch();
                    System.out.println("Executed Final Batch in " + (System.currentTimeMillis() - st) + "ms");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                ps.clearBatch();
            }

            reader.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ps != null) {
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

    private static final String DELETE = "DELETE FROM groupon_tickets where tickets_id = ?";
    private static final String INSERT = "INSERT INTO groupon_tickets " +
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
            " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" +
            " ON CONFLICT DO NOTHING";

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
        ps.setString(++idx, tickets.getTicketsDescription().replaceAll("\\x00", ""));
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
