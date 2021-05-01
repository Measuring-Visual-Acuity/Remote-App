package com.securevision.remote.apiService;

import com.securevision.remote.parsers.ImageDisplayParser;
import com.securevision.remote.parsers.SendDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/")
    Call<ImageDisplayParser> displayImage(@Query("position") int position, @Query("dpi") int dpi,
                                          @Query("distance") double distance,
                                          @Query("chart_type") String type);

    @GET("/total_score")
    Call<SendDetail> sendDetail(@Query("name") String name, @Query("email") String email, @Query(
            "age") String age, @Query("mobile_no") String mobile_no, @Query("gender") String gender,
                                @Query("chart_type") String chart_type,
                                @Query("left_eye") String left_eye,
                                @Query("right_eye") String right_eye,
                                @Query("distance") float distance);
}
