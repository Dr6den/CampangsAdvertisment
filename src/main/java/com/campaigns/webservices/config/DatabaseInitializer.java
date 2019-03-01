package com.campaigns.webservices.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andrew
 */
public class DatabaseInitializer {
    public static void main(String[] args0) {
        H2Connector connector = new H2Connector();
        Connection con = connector.getConnection();
        PreparedStatement createPlatformStatement = null;
        PreparedStatement createAdStatement = null;
        PreparedStatement createCampaignStatement = null;
        
        PreparedStatement dropPlatformStatement = null;
        PreparedStatement dropAdStatement = null;
        PreparedStatement dropCampaignStatement = null;       

        String createPlatformQuery = "CREATE TABLE PLATFORM(name int, ad_id INT, FOREIGN KEY (ad_id)  REFERENCES ADS (id))";
        String dropPlatformQuery = "DROP TABLE IF EXISTS PLATFORM";
        String insertPlatformQuery = "INSERT INTO PLATFORM" + "(name, ad_id) values" + "(?,?)";
        String selectPlatformQuery = "select * from PLATFORM";
        
        String createAdQuery = "CREATE TABLE ADS(id int primary key, name varchar(255), status int, asset_url varchar(255), campaign_id INT,"
                + " FOREIGN KEY (campaign_id)  REFERENCES CAMPAIGNS (id));";
        String dropAdQuery = "DROP TABLE IF EXISTS AD";
        String insertAdQuery = "INSERT INTO ADS" + "(id, name, status, asset_url, campaign_id) values" + "(?,?,?,?,?)";
        String selectAdQuery = "select * from ADS";
        
        String createCampaignQuery = "CREATE TABLE CAMPAIGNS(id int primary key, name varchar(255), status int, start_date timestamp, end_date timestamp)";         
        String dropCampaignsQuery = "DROP TABLE IF EXISTS CAMPAIGNS";
        String insertCampaignQuery = "INSERT INTO CAMPAIGNS" + "(id, name, status, start_date, end_date) values" + "(?,?,?,?,?)";
        String selectCampaignQuery = "select * from CAMPAIGNS";
        
        try {
            con.setAutoCommit(false);

            dropPlatformStatement = con.prepareStatement(dropPlatformQuery);
            dropPlatformStatement.execute();
            dropPlatformStatement.close();
            
            dropAdStatement = con.prepareStatement(dropAdQuery);
            dropAdStatement.execute();
            dropAdStatement.close();
            
            dropCampaignStatement = con.prepareStatement(dropCampaignsQuery);
            dropCampaignStatement.execute();
            dropCampaignStatement.close();
            
            createCampaignStatement = con.prepareStatement(createCampaignQuery);
            createCampaignStatement.execute();
            createCampaignStatement.close();
            
            createAdStatement = con.prepareStatement(createAdQuery);
            createAdStatement.execute();
            createAdStatement.close();
            
            createPlatformStatement = con.prepareStatement(createPlatformQuery);
            createPlatformStatement.execute();
            createPlatformStatement.close();            

            executeInserCampaignStatement(insertCampaignQuery, con, 1, "Inna", 1, new Timestamp(1920,1,1,1,1,1,1), new Timestamp(1920,1,1,1,1,1,1));
            executeInserCampaignStatement(insertCampaignQuery, con, 2, "Patti", 2, new Timestamp(1920,1,1,1,1,1,1), new Timestamp(1920,1,1,1,1,1,1));
            executeInserCampaignStatement(insertCampaignQuery, con, 3, "Faith", 3, new Timestamp(1920,1,1,1,1,1,1), new Timestamp(1920,1,1,1,1,1,1));
            executeInsertAdStatement(insertAdQuery, con, 1, "Nadia", 1, "Nadia's asset", 1);
            executeInsertAdStatement(insertAdQuery, con, 2, "Greta", 1, "Greta's asset", 1);
            executeInsertAdStatement(insertAdQuery, con, 3, "Lussi", 2, "Lussi's asset", 2);
            executeInsertAdStatement(insertAdQuery, con, 4, "Maria", 2, "Maria's asset", 2);
            executeInsertAdStatement(insertAdQuery, con, 5, "Hope", 3, "Hope's asset", 3);
            executeInsertAdStatement(insertAdQuery, con, 6, "Jane", 3, "Jane's asset", 3);
            executeInsertPlatformStatement(insertPlatformQuery, con, 0, 1);
            executeInsertPlatformStatement(insertPlatformQuery, con, 0, 2);
            executeInsertPlatformStatement(insertPlatformQuery, con, 0, 3);
            executeInsertPlatformStatement(insertPlatformQuery, con, 1, 4);
            executeInsertPlatformStatement(insertPlatformQuery, con, 1, 5);
            executeInsertPlatformStatement(insertPlatformQuery, con, 1, 6);
            executeInsertPlatformStatement(insertPlatformQuery, con, 2, 1);
            executeInsertPlatformStatement(insertPlatformQuery, con, 2, 2);
            executeInsertPlatformStatement(insertPlatformQuery, con, 2, 3);
            
            selectAllPlatforms(selectPlatformQuery, con);
            selectAllAds(selectAdQuery, con);
            selectAllCampaigns(selectCampaignQuery, con);

            con.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseInitializer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private static void executeInsertPlatformStatement(String query, Connection connection, int id, int ad_id) throws SQLException {
        PreparedStatement insertStatement = null;
        insertStatement = connection.prepareStatement(query);
        insertStatement.setInt(1, id);
        insertStatement.setInt(2, ad_id);
        insertStatement.executeUpdate();
        insertStatement.close();
    }
    
    private static void executeInsertAdStatement(String query, Connection connection, int id, String name, int status, String assetUrl,
            int campaign_id) throws SQLException {
        PreparedStatement insertStatement = null;
        insertStatement = connection.prepareStatement(query);
        insertStatement.setInt(1, id);
        insertStatement.setString(2, name);
        insertStatement.setInt(3, status);
        insertStatement.setString(4, assetUrl);
        insertStatement.setInt(5, campaign_id);
        insertStatement.executeUpdate();
        insertStatement.close();
    }
    
    private static void executeInserCampaignStatement(String query, Connection connection, int id, String name, int status, Timestamp startDate, 
            Timestamp endDate) throws SQLException {
        PreparedStatement insertStatement = null;
        insertStatement = connection.prepareStatement(query);
        insertStatement.setInt(1, id);
        insertStatement.setString(2, name);
        insertStatement.setInt(3, status);
        insertStatement.setTimestamp(4, startDate);
        insertStatement.setTimestamp(5, endDate);
        insertStatement.executeUpdate();
        insertStatement.close();
    }
    
    private static void selectAllCampaigns(String query, Connection connection) throws SQLException {
        PreparedStatement selectStatement = null;
        selectStatement = connection.prepareStatement(query);
        ResultSet rs = selectStatement.executeQuery();
        System.out.println("CAMPAIGNS: ");
        while (rs.next()) {
            System.out.println("Id " + rs.getInt("id") + " Name " + rs.getString("name") + " Status " + rs.getInt("status") + " Start date " 
                    + rs.getTimestamp("start_date")  + " End date " + rs.getTimestamp("end_date"));
        }
        selectStatement.close();
    }
    
    private static void selectAllAds(String query, Connection connection) throws SQLException {
        PreparedStatement selectStatement = null;
        selectStatement = connection.prepareStatement(query);
        ResultSet rs = selectStatement.executeQuery();
        System.out.println("ADS: ");
        while (rs.next()) {
            System.out.println("Id " + rs.getInt("id") + " Name " + rs.getString("name") + " Status " + rs.getInt("status") + " Asset " 
                    + rs.getString("asset_url")  + " Campaign REF " + rs.getInt("campaign_id"));
        }
        selectStatement.close();
    }
    
    private static void selectAllPlatforms(String query, Connection connection) throws SQLException {
        PreparedStatement selectStatement = null;
        selectStatement = connection.prepareStatement(query);
        ResultSet rs = selectStatement.executeQuery();
        System.out.println("PLATFORMS: ");
        while (rs.next()) {
            System.out.println("Id " + rs.getInt("name") + " AD REF " + rs.getInt("ad_id"));
        }
        selectStatement.close();
    }
}
