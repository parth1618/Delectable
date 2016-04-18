package web.module.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import web.module.report.OrderDeliveryReport;
import web.module.report.Report;
import web.module.report.ReportBoundaryInterface;
import web.module.report.ReportList;
import web.module.report.ReportManager;
import web.module.report.RevenueReport;
import web.module.serializer.DeliveryReportSerializer;
import web.module.serializer.RevenueReportSerializer;
import web.module.serializer.SimpleReportListSerializer;
import web.module.utility.JSONPrettyPrinterInterface;

@Path("/report")
public class ReportServiceController{
	
private ReportBoundaryInterface rm = new ReportManager();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReportList() throws JsonGenerationException, JsonMappingException, IOException{
		
		String objectString = JSONPrettyPrinterInterface.printPrettyJSONCustomSerializer("ReportModule", Report.class, 
				new SimpleReportListSerializer(), rm.getReportList());
		
		return Response.status(Response.Status.OK).entity(objectString).build();
	}
	
	@GET
	@Path("/{rid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReportById(@PathParam("rid") int rid, @QueryParam("start_date") String startDate, 
			@QueryParam("end_date") String endDate) throws JsonGenerationException, JsonMappingException, IOException, ParseException{
		
		String  objectString = "";
		Status response_status = Response.Status.OK;
		
		if(rm.getReport(rid).isNil()){
			objectString = "Entity not found for ID: " + rid;
			response_status = Response.Status.NOT_FOUND;
		}
		else if(rid == ReportList.RID_803){
			objectString = JSONPrettyPrinterInterface.printPrettyJSONCustomSerializer("RevenueReportModule", RevenueReport.class, 
					new RevenueReportSerializer(), rm.generateReport(rid, startDate, endDate));
		}
		else{
			objectString = JSONPrettyPrinterInterface.printPrettyJSONCustomSerializer("DeliveryReportModule", OrderDeliveryReport.class, 
					new DeliveryReportSerializer(), rm.generateReport(rid, startDate, endDate));
		}
		return Response.status(response_status).entity(objectString).build();
	}

}
