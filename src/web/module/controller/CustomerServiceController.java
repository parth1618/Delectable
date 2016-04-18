package web.module.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import web.module.customer.Customer;
import web.module.customer.CustomerBoundaryInterface;
import web.module.customer.CustomerManager;
import web.module.serializer.CompactCustomerSerializer;
import web.module.serializer.ExtendedCustomerSerializer;
import web.module.utility.JSONPrettyPrinterInterface;

@Path("/customer")
public class CustomerServiceController {

	private CustomerBoundaryInterface customerManager = new CustomerManager();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCustomer(@QueryParam("key") String query_string) {

		String objectString = "";

		try {
			if (query_string != null) {
				objectString = JSONPrettyPrinterInterface.printPrettyJSONCustomSerializer("CompactCustomerModule",
						Customer.class, new CompactCustomerSerializer(), customerManager.getAllCustomer(query_string));
			} else {
				objectString = JSONPrettyPrinterInterface.printPrettyJSONCustomSerializer("CompactCustomerModule",
						Customer.class, new CompactCustomerSerializer(), customerManager.getAllCustomer());
			}
		} catch (Exception e) {
			String returnString = "OOPs! Something went wrong. " + e;
			return Response.status(Response.Status.BAD_REQUEST).entity(returnString).build();
		}

		return Response.status(Response.Status.OK).entity(objectString).build();
	}

	@GET
	@Path("/{cid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomerById(@PathParam("cid") int cid) {

		String objectString = "";

		if (customerManager.getCustomer(cid).isNil()) {
			String returnString = "Entity not found for ID: " + cid;
			return Response.status(Response.Status.NOT_FOUND).entity(returnString).build();
		}

		try {
			objectString = JSONPrettyPrinterInterface.printPrettyJSONCustomSerializer("ExtendedCustomerModule",
					Customer.class, new ExtendedCustomerSerializer(), customerManager.getCustomer(cid));
		} catch (Exception e) {
			String returnString = "OOPs! Something went wrong. " + e;
			return Response.status(Response.Status.BAD_REQUEST).entity(returnString).build();
		}

		return Response.status(Response.Status.OK).entity(objectString).build();
	}

}
