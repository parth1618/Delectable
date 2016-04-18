package web.module.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import web.module.report.Report;

public class SimpleReportListSerializer extends JsonSerializer<Report> {

	@Override
	public void serialize(Report report, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		jgen.writeStartObject();
		jgen.writeNumberField("id", report.getId());
		jgen.writeStringField("name", report.getName());
		jgen.writeEndObject();
	}

}
