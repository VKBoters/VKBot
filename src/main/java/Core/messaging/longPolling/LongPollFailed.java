package Core.messaging.longPolling;

import com.google.gson.annotations.SerializedName;

public class LongPollFailed{
	@SerializedName("failed")
	public Object fail; 
}
