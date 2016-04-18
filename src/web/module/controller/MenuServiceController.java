package web.module.controller;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import web.module.menu.MenuBoundaryInterface;
import web.module.menu.MenuItem;
import web.module.menu.MenuManager;
import web.module.serializer.CompactMenuItemSerializer;
import web.module.serializer.ExtendedMenuItemSerializer;
import web.module.utility.JSONPrettyPrinterInterface;

@Path("/menu")
public class MenuServiceController{
	
	private MenuBoundaryInterface menuManager = new MenuManager();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMenu() throws JsonGenerationException, JsonMappingException, IOException {
		
		String objectString = JSONPrettyPrinterInterface.printPrettyJSONCustomSerializer("CompactMenuItemModule", MenuItem.class, 
				new CompactMenuItemSerializer(), menuManager.getAllMenuItems());
		
		return Response.status(Response.Status.OK).entity(objectString).build();
	}

	@GET
	@Path("/{mid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMenuItemById(@PathParam("mid") int mid) throws JsonGenerationException, JsonMappingException, IOException {
		
		if(menuManager.getItem(mid).isNil()){
			return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + mid).build();
		}
	
		String objectString = JSONPrettyPrinterInterface.printPrettyJSONCustomSerializer("ExtendedMenuItemModule", MenuItem.class, 
				new ExtendedMenuItemSerializer(), menuManager.getItem(mid));
		
		return Response.status(Response.Status.OK).entity(objectString).build();
	}

}
