package web.module.controller;

import java.text.ParseException;

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
import web.module.menu.MenuManager.InvalidItemDetailException;
import web.module.menu.MenuManager.InvalidItemPriceForUpdateException;
import web.module.order.OrderManager.DeliveryDateIsInFutureException;

@Path("/admin")
public class AdminServiceController {

	private AdminBoundaryInterface adminManager = new AdminManager();

	@PUT
	@Path("/menu")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addMenuItem(MenuItem item, @Context UriInfo uriInfo) {

		try {
			adminManager.addItem(item);
		} catch (InvalidItemDetailException itde) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Invalid Item Detail. ERROR CODE: EM_101")
					.build();
		}

		UriBuilder builder = uriInfo.getBaseUriBuilder().path("menu/" + Integer.toString(item.getId()));
		return Response.created(builder.build()).entity("{\"id\":" + item.getId() + "}").build();
	}

	@POST
	@Path("/menu/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateMenuItemById(@PathParam("id") int id, MenuItem item, @Context UriInfo uriInfo) {

		if (adminManager.getItem(id).isNil()) {
			return Response.status(Response.Status.NO_CONTENT).entity("Entity not found for ID: " + id).build();
		}

		try {
			adminManager.updateItem(id, item);
		} catch (InvalidItemPriceForUpdateException itpue) {
			return Response.status(Response.Status.NOT_ACCEPTABLE)
					.entity("Invalid Price For Update.  ERROR CODE: EM_102").build();
		}

		UriBuilder builder = uriInfo.getBaseUriBuilder().path("menu/" + Integer.toString(id));
		return Response.created(builder.build()).build();
	}

	@POST
	@Path("/surcharge")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addSurcharge(JSONObject inputJsonObj, @Context UriInfo uriInfo) throws JSONException {

		double charge = inputJsonObj.getDouble("surcharge");
		adminManager.addSurcharge(charge);

		UriBuilder builder = uriInfo.getAbsolutePathBuilder().path("");
		return Response.created(builder.build()).build();
	}

	@GET
	@Path("/surcharge")
	public Response getSurcharge() {

		if (!adminManager.isSurchargeSet()) {
			return Response.status(Response.Status.NO_CONTENT).entity("No Surcharge Found").build();
		}
		double charge = adminManager.getSurcharge();
		return Response.status(Response.Status.OK).entity("{\"surcharge\":" + charge + "}").build();
	}

	@POST
	@Path("delivery/{id}")
	public Response cancelOrder(@PathParam("id") int id) throws ParseException {

		if (adminManager.getOrder(id).isNil()) {
			String returnString = "Entity not found for ID: " + id;
			return Response.status(Response.Status.NOT_FOUND).entity(returnString).build();
		}

		try {
			adminManager.updateDeliveryStatus(id);
		} catch (DeliveryDateIsInFutureException ddife) {
			return Response.status(Response.Status.NOT_ACCEPTABLE)
					.entity("Delivery Date For the Order is in Future.  ERROR CODE: EO_104").build();
		}
		return Response.status(Response.Status.OK).build();
	}

}
