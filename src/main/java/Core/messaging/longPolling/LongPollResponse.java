package Core.messaging.longPolling;

import com.google.gson.JsonArray;

public class LongPollResponse {
	public int ts;
	public JsonArray updates;
}
