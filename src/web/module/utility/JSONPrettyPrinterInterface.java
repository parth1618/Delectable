package web.module.utility;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;

public class JSONPrettyPrinterInterface {

	private static ObjectMapper mapper = new ObjectMapper();

	public static String printPrettyJSON(Object valueObject)
			throws JsonGenerationException, JsonMappingException, IOException {
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(valueObject);
	}

	public static <T> String printPrettyJSONCustomSerializer(String name, Class<T> type, JsonSerializer<T> ser,
			Object valueObject) throws JsonGenerationException, JsonMappingException, IOException {

		ObjectMapper omap = new ObjectMapper();
		SimpleModule module = new SimpleModule(name, new Version(1, 0, 0, null));
		module.addSerializer(type, ser);
		omap.registerModule(module);
		return omap.writerWithDefaultPrettyPrinter().writeValueAsString(valueObject);

	}

}
