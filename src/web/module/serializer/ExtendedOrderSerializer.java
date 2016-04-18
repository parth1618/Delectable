package web.module.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import web.module.order.Order;

public class ExtendedOrderSerializer extends JsonSerializer<Order>{
	
	@Override
	public void serialize(Order order, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		
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
		jgen.writeObjectField("ordered_detail", order.getOrder_detail());
	
		jgen.writeEndObject();
		
	}

}
