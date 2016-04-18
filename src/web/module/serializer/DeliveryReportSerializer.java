package web.module.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import web.module.order.Order;
import web.module.report.OrderDeliveryReport;
import web.module.report.ReportList;

public class DeliveryReportSerializer extends JsonSerializer<OrderDeliveryReport> {

	@Override
	public void serialize(OrderDeliveryReport report, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		jgen.writeStartObject();
		jgen.writeNumberField("id", report.getId());
		jgen.writeStringField("name", report.getName());
		if(report.getId() == ReportList.RID_804){
			jgen.writeStringField("start_date", report.getStart_date());
			jgen.writeStringField("end_date", report.getEnd_date());
		}
		jgen.writeArrayFieldStart("orders");
		
		for(Order order : report.getOrderList()){
			
			jgen.writeStartObject();
			
			jgen.writeNumberField("id", order.getId());
			jgen.writeNumberField("amount", order.getAmount());
			jgen.writeNumberField("surcharge", order.getSurcharge());
			jgen.writeStringField("status", order.getStatus());
			jgen.writeStringField("order_date", order.getOrder_date());
			jgen.writeStringField("delivery_date", order.getDelivery_date());
			jgen.writeObjectField("ordered_by", order.getPersonal_info());
			jgen.writeStringField("delivery_address", order.getDelivery_address());
			jgen.writeStringField("note", order.getNote());
			jgen.writeObjectField("order_detail", order.getOrder_detail());
		
			jgen.writeEndObject();
		}
		jgen.writeEndArray();
			
		jgen.writeEndObject();
	}

}
