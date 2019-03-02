package com.campaingns.dao;

import com.campaigns.dao.CampaignsDao;
import com.campaigns.domain.Ad;
import com.campaigns.domain.Platform;
import com.campaigns.domain.Status;
import com.campaigns.webservices.config.DatabaseInitializer;
import com.campaigns.webservices.config.H2Connector;
import com.campaigns.webservices.config.IJdbcConnector;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 *
 * @author andrew
 */
public class CampaignDaoTest {
    private CampaignsDao campaignsDao;
    private IJdbcConnector con;
    private DatabaseInitializer initializer;
    
    public CampaignDaoTest() {
        con = new H2Connector();
        initializer = new DatabaseInitializer();
        campaignsDao = new CampaignsDao(con, initializer);
    }
    
    @Test
    public void createCampaignTest() {
        List<Platform> platforms = new ArrayList<>();
        platforms.add(Platform.WEB);
        platforms.add(Platform.ANDROID);
        Ad ad = new Ad(0, "some ad", platforms, "Asset url", Status.ACTIVE);
        
        int adId = campaignsDao.insertAd(ad, 1);
        Ad persistedAd = campaignsDao.getAdById(adId);
        List<Platform> persistedPlatforms = persistedAd.getPlatforms();
        
        assertNotNull(persistedAd);
        assertEquals(persistedAd.getAsserUrl(), "Asset url");
        assertEquals(persistedAd.getName(), "some ad");
        assertEquals(persistedAd.getStatus(), Status.ACTIVE);
        assertNotNull(persistedPlatforms);
        assertEquals(persistedPlatforms.size(), platforms.size());
        assertTrue(persistedPlatforms.containsAll(platforms));
    }
}
