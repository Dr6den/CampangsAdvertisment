package com.campaigns.webservices.endpoint;

import com.campaigns.domain.Campaign;
import com.campaigns.domain.service.CampaignsService;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/RegisterProcess")
public class CampaignsEndpoint
{
    private CampaignsService service;
    
    @Inject
    public CampaignsEndpoint(CampaignsService service) {
       this.service = service; 
    }
    
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRegisterDetails()
	{Campaign c = service.getCampaign(0);
		String msg=c.getName();

		return Response.ok().status(200).entity(msg).build();

	}

	// *************************************************

	@POST	
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRegisterDetails1()
	{
		
		String msg="You Clicked Post Request";
		return Response.ok().status(200).entity(msg).build();

	}

	// *************************************************

	
}