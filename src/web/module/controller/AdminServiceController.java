package web.module.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import web.module.admin.AdminBoundaryInterface;
import web.module.admin.AdminManager;
import web.module.menu.MenuItem;

@Path("/admin")
public class AdminServiceController{
	
private AdminBoundaryInterface adminManager = new AdminManager();
	
	@PUT
	@Path("/menu")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addMenuItem(MenuItem item, @Context UriInfo uriInfo){
		
		adminManager.addItem(item);
		
		UriBuilder builder = uriInfo.getBaseUriBuilder().path("menu/"+ Integer.toString(item.getId()));
		
		return Response.created(builder.build()).entity("{\"id\":" + item.getId() + "}").build();
	}
	
	@POST
	@Path("/menu/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateMenuItemById(@PathParam("id") int id, MenuItem item, @Context UriInfo uriInfo){
		
		if(adminManager.getItem(id).isNil()){
			return Response.status(Response.Status.NO_CONTENT).entity("Entity not found for ID: " + id).build();
		}
		 
		adminManager.updateItem(id, item);
		
		UriBuilder builder = uriInfo.getBaseUriBuilder().path("menu/" + Integer.toString(id));
		
		return Response.created(builder.build()).build();
	}
	
	
	@POST
	@Path("/surcharge")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addSurcharge(JSONObject inputJsonObj, @Context UriInfo uriInfo) throws JSONException{
		
		double charge = inputJsonObj.getDouble("surcharge");
		adminManager.addSurcharge(charge);
		
		UriBuilder builder = uriInfo.getAbsolutePathBuilder().path("");
		
		return Response.created(builder.build()).build();
	}
	
	@GET
	@Path("/surcharge")
	public Response getSurcharge(){
		
		if(!adminManager.isSurchargeSet()){
			return Response.status(Response.Status.NO_CONTENT).entity("No Surcharge Found").build();
		}
		double charge = adminManager.getSurcharge();
		return Response.status(Response.Status.OK).entity("{\"surcharge\":" + charge + "}").build();
	}
	
	@POST
	@Path("delivery/{id}")
	public Response cancelOrder(@PathParam("id") int id) {
		
		if(adminManager.getOrder(id).isNil()){
			return Response.status(Response.Status.NO_CONTENT).entity("Entity not found for ID: " + id).build();
		}
		
		adminManager.updateDeliveryStatus(id);
		
		return Response.status(Response.Status.OK).build();
	}

}
