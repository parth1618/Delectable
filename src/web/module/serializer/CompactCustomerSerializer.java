package web.module.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import web.module.customer.Customer;

public class CompactCustomerSerializer extends JsonSerializer<Customer>{

	@Override
	public void serialize(Customer customer, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		
		jgen.writeStartObject();
		
		jgen.writeNumberField("id", customer.getId());
		jgen.writeStringField("name", customer.getPersonal_info().getName());
		jgen.writeStringField("email", customer.getPersonal_info().getEmail());
		jgen.writeStringField("phone", customer.getPersonal_info().getPhone());
		
		jgen.writeEndObject();
		
	}

}
