package kr.hdd.carleamingTest.req;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class PidSerialiser implements JsonSerializer<Pid> {

	@Override
	public JsonElement serialize(Pid pid, Type typeOfSrc, JsonSerializationContext context) {
		final JsonObject jsonObject = new JsonObject();
//	    jsonObject.addProperty("userId", pid.getUserId());
//	    jsonObject.addProperty("date", pid.getDate());

	    final JsonElement pidData = context.serialize(pid.getPidData());
	    jsonObject.add("pidDatas", pidData);

	    return jsonObject;

	}

}
