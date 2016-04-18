package web.module.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import web.module.order.Order;
import web.module.order.OrderBoundaryInterface;
import web.module.order.OrderManager;
import web.module.order.OrderManager.InvalidOrderDetailException;
import web.module.order.OrderManager.OrderCanNotBeCancelledException;
import web.module.serializer.CompactOrderSerializer;
import web.module.serializer.ExtendedOrderSerializer;
import web.module.utility.JSONPrettyPrinterInterface;

@Path("/order")
public class OrderServiceController{
	
	private OrderBoundaryInterface orderManager = new OrderManager();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllOrder(@QueryParam("date") String date) throws JsonGenerationException, JsonMappingException, IOException, ParseException{
		
		String objectString = "";
		
		if(date != null){
			objectString = JSONPrettyPrinterInterface.printPrettyJSONCustomSerializer("CompactOrderModule", Order.class, new CompactOrderSerializer(), orderManager.getAllOrder(date));
		}
		else{
			objectString = JSONPrettyPrinterInterface.printPrettyJSONCustomSerializer("CompactOrderModule", Order.class, new CompactOrderSerializer(), orderManager.getAllOrder());
		}
		return Response.status(Response.Status.OK).entity(objectString).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addOrder(Order order, @Context UriInfo uriInfo) throws ParseException{
	
		try{
			orderManager.addOrder(order);
		}catch(InvalidOrderDetailException iode){
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Invalid Order Detail").build();
		}
		
		UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Integer.toString(order.getId()));
		
		String cancel_url = uriInfo.getPath() + "/cancel/" + Integer.toString(order.getId()); 
		String objectString = "{\"id\":" + order.getId() + "," + "\"cancel_url\":\"" + cancel_url + "\"}";
		
		return Response.created(builder.build()).entity(objectString).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrderById(@PathParam("id") int id) throws JsonGenerationException, JsonMappingException, IOException {
		
		if(orderManager.getOrder(id).isNil()){
			return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
		}
	
		String objectString = JSONPrettyPrinterInterface.printPrettyJSONCustomSerializer("ExtendedOrderModule", Order.class, new ExtendedOrderSerializer(), orderManager.getOrder(id));
		
		return Response.status(Response.Status.OK).entity(objectString).build();
	}

	@POST
	@Path("cancel/{id}")
	public Response cancelOrder(@PathParam("id") int id) throws ParseException {
		
		if(orderManager.getOrder(id).isNil()){
			return Response.status(Response.Status.NO_CONTENT).entity("Entity not found for ID: " + id).build();
		}
		
		try{
			orderManager.cancelOrder(id);
			return Response.status(Response.Status.OK).build();
		}
		catch(OrderCanNotBeCancelledException ocbce){
			return Response.status(Response.Status.FORBIDDEN).entity("Can note cancel Order for Today").build();
		}
	}

}
