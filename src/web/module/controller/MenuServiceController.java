package web.module.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import web.module.menu.MenuBoundaryInterface;
import web.module.menu.MenuItem;
import web.module.menu.MenuManager;
import web.module.serializer.CompactMenuItemSerializer;
import web.module.serializer.ExtendedMenuItemSerializer;
import web.module.utility.JSONPrettyPrinterInterface;

@Path("/menu")
public class MenuServiceController {

	private MenuBoundaryInterface menuManager = new MenuManager();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMenu() {

		String objectString = "";

		try {
			objectString = JSONPrettyPrinterInterface.printPrettyJSONCustomSerializer("CompactMenuItemModule",
					MenuItem.class, new CompactMenuItemSerializer(), menuManager.getAllMenuItems());
		} catch (Exception e) {
			String returnString = "OOPs! Something went wrong. " + e;
			return Response.status(Response.Status.BAD_REQUEST).entity(returnString).build();
		}

		return Response.status(Response.Status.OK).entity(objectString).build();
	}

	@GET
	@Path("/{mid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMenuItemById(@PathParam("mid") int mid) {

		String objectString = "";

		if (menuManager.getItem(mid).isNil()) {
			String returnString = "Entity not found for ID: " + mid;
			return Response.status(Response.Status.NOT_FOUND).entity(returnString).build();
		}

		try {
			objectString = JSONPrettyPrinterInterface.printPrettyJSONCustomSerializer("ExtendedMenuItemModule",
					MenuItem.class, new ExtendedMenuItemSerializer(), menuManager.getItem(mid));
		} catch (Exception e) {
			String returnString = "OOPs! Something went wrong. " + e;
			return Response.status(Response.Status.BAD_REQUEST).entity(returnString).build();
		}
		return Response.status(Response.Status.OK).entity(objectString).build();
	}

}
