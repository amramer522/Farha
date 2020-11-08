package com.farhachat.farha.Notifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA504ZSqQ:APA91bG8qNj01-tLU0v1JTkKnMsgIWh9xZfEa-wjuM0GxClVCPRviTZoyGQQ5QFOrBeU5ttMgFvTdw-yTGxC0wew0CD-L_X_qqB90SIZNieikNWEk9WIbvC6D6e80-oKkCSJGSbbj6W2"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
