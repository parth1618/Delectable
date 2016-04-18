package web.module.controller;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import web.module.customer.Customer;
import web.module.customer.CustomerBoundaryInterface;
import web.module.customer.CustomerManager;
import web.module.serializer.CompactCustomerSerializer;
import web.module.serializer.ExtendedCustomerSerializer;
import web.module.utility.JSONPrettyPrinterInterface;

@Path("/customer")
public class CustomerServiceController{
	
	private CustomerBoundaryInterface customerManager = new CustomerManager();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCustomer(@QueryParam("key") String query_string) throws JsonGenerationException, JsonMappingException, IOException{
		
		String objectString = "";
		
		if(query_string != null){
			objectString = JSONPrettyPrinterInterface.printPrettyJSONCustomSerializer("CompactCustomerModule", Customer.class, new CompactCustomerSerializer(), customerManager.getAllCustomer(query_string));
		}
		else{
			objectString = JSONPrettyPrinterInterface.printPrettyJSONCustomSerializer("CompactCustomerModule", Customer.class, new CompactCustomerSerializer(), customerManager.getAllCustomer());
		}
		return Response.status(Response.Status.OK).entity(objectString).build();
	}
	
	@GET
	@Path("/{cid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomerById(@PathParam("cid") int cid) throws JsonGenerationException, JsonMappingException, IOException{
		
		if(customerManager.getCustomer(cid).isNil()){
			return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + cid).build();
		}
		
		String objectString = JSONPrettyPrinterInterface.printPrettyJSONCustomSerializer("ExtendedCustomerModule", Customer.class, new ExtendedCustomerSerializer(), customerManager.getCustomer(cid));
		
		return Response.status(Response.Status.OK).entity(objectString).build();
	}

}
