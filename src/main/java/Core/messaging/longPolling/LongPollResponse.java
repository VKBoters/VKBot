package Core.messaging.longPolling;

import com.google.gson.annotations.SerializedName;

public class LongPollResponse {
	@SerializedName("ts")
	public int ts;
	@SerializedName("updates")
	public Object[][] updates;
}
