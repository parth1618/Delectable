package web.module.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import web.module.menu.MenuItem;

public class ExtendedMenuItemSerializer extends JsonSerializer<MenuItem> {

	@Override
	public void serialize(MenuItem item, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		jgen.writeStartObject();

		jgen.writeNumberField("id", item.getId());
		jgen.writeStringField("name", item.getName());
		jgen.writeNumberField("price_per_person", item.getPrice_per_person());
		jgen.writeNumberField("minimum_order", item.getMinimum_order());
		jgen.writeObjectField("categories", item.getCategories());
		jgen.writeStringField("created_date", item.getCreated_date());
		jgen.writeStringField("last_modified_date", item.getLast_modified_date());

		jgen.writeEndObject();

	}

}
