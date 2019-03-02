package com.campaigns.dao;

import com.campaigns.domain.Ad;
import com.campaigns.domain.Campaign;
import com.campaigns.domain.Platform;
import com.campaigns.domain.Status;
import com.campaigns.webservices.config.DatabaseInitializer;
import com.campaigns.webservices.config.IJdbcConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author andrew
 */
public class CampaignsDao {
    private IJdbcConnector connector;
    private DatabaseInitializer databaseInitializer;
    
    @Inject
    public CampaignsDao(IJdbcConnector con, DatabaseInitializer initializer) {
        this.connector = con;
        this.databaseInitializer = initializer;
        initializer.createDatabase();
    }
    
    public Campaign getCampaign(int id) {
        Campaign c = new Campaign();c.setName("Nadia a");
        return c;
    }
    
    public Ad getAdById(int id) {
        Ad ad = null;
        try {
            String query = "select * from ADS where id = ?";
            Connection con = connector.getConnection();
            PreparedStatement selectStatement = con.prepareStatement(query);
            selectStatement.setInt(1, id);
            ResultSet rs = selectStatement.executeQuery();
            
            while (rs.next()) {
                ad = new Ad();
                ad.setId(id);
                ad.setName(rs.getString("name"));
                ad.setStatus(Status.fromInteger(rs.getInt("status")));
                ad.setAsserUrl(rs.getString("asset_url"));
            }
            selectStatement.close();
            ad.setPlatforms(getPlatformsForAd(id, con));
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CampaignsDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ad;
    }
    
    private List<Platform> getPlatformsForAd(int adId, Connection con) throws SQLException {
        List<Platform> platforms = new ArrayList<>();
        String query = "select * from PLATFORM where ad_id = ?";
        PreparedStatement selectStatement = con.prepareStatement(query);
        selectStatement.setInt(1, adId);
        ResultSet rs = selectStatement.executeQuery();
        
        while (rs.next()) {
            platforms.add(Platform.fromInteger(rs.getInt("name")));
        }
        selectStatement.close();
        return platforms;
    }

    public int insertAd(Ad ad, int campaignId) {
        int id = 0;
        try {            
            String[] columnNames = new String[] { "id" };
            Connection con = connector.getConnection();
            String query = "INSERT INTO ADS" + "(name, status, asset_url, campaign_id) values" + "(?,?,?,?)";
            
            PreparedStatement insertStatement = null;
            insertStatement = con.prepareStatement(query, columnNames);
            insertStatement.setString(1, ad.getName());
            insertStatement.setInt(2, ad.getStatus().getValue());
            insertStatement.setString(3, ad.getAsserUrl());
            insertStatement.setInt(4, campaignId);
            if (insertStatement.executeUpdate() > 0) {
                java.sql.ResultSet generatedKeys = insertStatement.getGeneratedKeys();
                if ( generatedKeys.next() ) {
                    id = generatedKeys.getInt(1);
                }
            }
            insertStatement.close();
            insertPlatforms(id, ad.getPlatforms(), con);
            con.commit();
            con.close();            
        } catch (SQLException ex) {
            Logger.getLogger(CampaignsDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    private void insertPlatforms(int ad_id, List<Platform> platforms, Connection con) {
        platforms.forEach((Platform p) -> executeInsertPlatformStatement(con, p.getValue(), ad_id));
    }
    
    private void executeInsertPlatformStatement(Connection connection, int id, int ad_id) {
        try {
            String query = "INSERT INTO PLATFORM" + "(name, ad_id) values" + "(?,?)";
            PreparedStatement insertStatement = connection.prepareStatement(query);
            insertStatement.setInt(1, id);
            insertStatement.setInt(2, ad_id);
            insertStatement.executeUpdate();
            insertStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(CampaignsDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
