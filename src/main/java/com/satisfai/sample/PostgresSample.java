package com.satisfai.sample;

import java.sql.*;

/**
 * @author Lu Fangjian
 */
public class PostgresSample {

    public static void main(String[] args) {

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:postgresql://35.188.61.177:5432/groupon_2016";
        String user = "postgres";
        String password = "satisfai";

        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery("SELECT max(metric_sets_agent_wait_time_in_minutes_business) from groupon_tickets");

            if (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
