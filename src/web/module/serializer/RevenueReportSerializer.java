package web.module.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import web.module.report.RevenueReport;

public class RevenueReportSerializer extends JsonSerializer<RevenueReport> {

	@Override
	public void serialize(RevenueReport report, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		
		jgen.writeStartObject();
		
		jgen.writeNumberField("id", report.getId());
		jgen.writeStringField("name", report.getName());
		jgen.writeStringField("start_date", report.getStart_date());
		jgen.writeStringField("end_date", report.getEnd_date());
		jgen.writeNumberField("orders_palced", report.getRevenue().getOrders_placed());
		jgen.writeNumberField("orders_open", report.getRevenue().getOrders_open());
		jgen.writeNumberField("orders_delivered", report.getRevenue().getOrders_delivered());
		jgen.writeNumberField("orders_cancelled", report.getRevenue().getOrders_cancelled());
		jgen.writeNumberField("food_revenue", report.getRevenue().getFood_revenue());
		jgen.writeNumberField("surcharge_revenue", report.getRevenue().getSurcharge_revenue());
		
		jgen.writeEndObject();
		
	}

}
