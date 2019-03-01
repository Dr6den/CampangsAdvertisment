package com.campaigns.dao;

import com.campaigns.domain.Campaign;
import com.campaigns.webservices.config.IJdbcConnector;
import javax.inject.Inject;

/**
 *
 * @author andrew
 */
public class CampaignsDao {
    private IJdbcConnector connector;
    
    @Inject
    public CampaignsDao(IJdbcConnector con) {
        this.connector = con;
    }
    
    public Campaign getCampaign(int id) {
        Campaign c = new Campaign();c.setName("Nadia a");
        return c;
    }
}
