package com.campaigns.domain.service;

import com.campaigns.dao.CampaignsDao;
import com.campaigns.domain.Campaign;
import javax.inject.Inject;

/**
 *
 * @author andrew
 */
public class CampaignsService {
    private CampaignsDao dao;
    
    @Inject
    public CampaignsService(CampaignsDao dao) {
        this.dao = dao;
    }
    
    public Campaign getCampaign(int id) {
        return dao.getCampaign(id);
    }
}
