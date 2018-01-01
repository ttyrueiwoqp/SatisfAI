package com.satisfai.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * @author Lu Fangjian
 */
public class DealsConverter2 {

    private static final int BATCH_LIMIT = 1000;
    private static final int CURRENT_MAX_ID = 20000000; // After disconnect and reconnect, change this by `select max(id) from groupon_deals_email`
    private static final int NEXT_MAX_ID = 40000000;

    // TODO Change this to your folder
    private static final String FILE_PATH = "/Users/fangjian/Downloads/";

    // TODO To run a new file, comment off the previous file and add the new file

    private static final String FILE_NAME = "data.tsv";

    // TODO Go to Google Cloud console > Access Control > Authorization, add your IP there
    private static final String url = "jdbc:postgresql://35.188.61.177:5432/groupon_2016";
    private static final String user = "postgres";

    // TODO Enter password here, never commit this
    private static final String password = "";

    private static final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
            .create();

    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private static final String DELETE = "DELETE FROM groupon_tickets where tickets_id = ?";
    private static final String INSERT = "INSERT INTO groupon_deals_email " +
            "(id," +
            " user_id," +
            " email_address," +
            " deal_uuid," +
            " purchase_date," +
            " purchase_amount," +
            " lob," +
            " refund_flag," +
            " recency_segment," +
            " frequency_segment)" +
            " VALUES (?,?,?,?,?,?,?,?,?,?)" +
            " ON CONFLICT DO NOTHING";

    public static void main(String[] args) throws IOException, ParseException {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(true);
            ps = con.prepareStatement(INSERT);

            File f = new File(FILE_PATH + FILE_NAME);

            BufferedReader b = new BufferedReader(new FileReader(f));

            String line = "";
            b.readLine();

            int i = 0;
            while ((line = b.readLine()) != null) {
                i++;
                if (i < CURRENT_MAX_ID) {
                    continue;
                }
                if (i > NEXT_MAX_ID) {
                    break;
                }

                String[] strs = line.split("\\t");

                GrouponDealsEmail deal = new GrouponDealsEmail();
                int idx = 0;
                deal.setId(i);
                deal.setUserId(Long.parseLong(strs[idx++]));
                deal.setEmailAddress(strs[idx++]);
                deal.setDealUuid(UUID.fromString(strs[idx++]));
                deal.setPurchaseDate(new Timestamp(df.parse(strs[idx++]).getTime()));
                deal.setPurchaseAmount(Double.parseDouble(strs[idx++]));
                deal.setLob(Short.parseShort(strs[idx++]));
                deal.setRefundFlag(Short.parseShort(strs[idx++]));
                deal.setRecencySegment(Short.parseShort(strs[idx++]));
                deal.setFrequencySegment(Short.parseShort(strs[idx++]));

                buildPrepareStatement(ps, deal);
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

            b.close();

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

    private static void buildPrepareStatement(PreparedStatement ps, GrouponDealsEmail deal) throws SQLException {
        int idx = 0;
        ps.setLong(++idx, deal.getId());
        ps.setLong(++idx, deal.getUserId());
        ps.setString(++idx, deal.getEmailAddress());
        ps.setObject(++idx, deal.getDealUuid());
        ps.setTimestamp(++idx, deal.getPurchaseDate());
        ps.setDouble(++idx, deal.getPurchaseAmount());
        ps.setShort(++idx, deal.getLob());
        ps.setShort(++idx, deal.getRefundFlag());
        ps.setShort(++idx, deal.getRecencySegment());
        ps.setShort(++idx, deal.getFrequencySegment());

    }

}
