package web.module.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import web.module.customer.Customer;
import web.module.order.Order;

public class ExtendedCustomerSerializer extends JsonSerializer<Customer> {

	@Override
	public void serialize(Customer customer, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		
		jgen.writeStartObject();
		
		jgen.writeNumberField("id", customer.getId());
		jgen.writeStringField("name", customer.getPersonal_info().getName());
		jgen.writeStringField("email", customer.getPersonal_info().getEmail());
		jgen.writeStringField("phone", customer.getPersonal_info().getPhone());
		jgen.writeObjectField("other_known_alias", customer.getKnown_alias());
		
		jgen.writeArrayFieldStart("orders");
		for(Order order : customer.getOrder_history()){
			
			jgen.writeStartObject();
			
			jgen.writeNumberField("id", order.getId());
			jgen.writeStringField("order_date", order.getOrder_date());
			jgen.writeStringField("delivery_date", order.getDelivery_date());
			jgen.writeNumberField("amount", order.getAmount());
			jgen.writeNumberField("surcharge", order.getSurcharge());
			jgen.writeStringField("status", order.getStatus());
			
			jgen.writeEndObject();
		}
		jgen.writeEndArray();
	
		jgen.writeEndObject();
		
	}

}
