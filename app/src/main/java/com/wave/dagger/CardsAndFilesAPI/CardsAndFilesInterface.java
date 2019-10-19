package com.wave.dagger.CardsAndFilesAPI;

import com.wave.dagger.model.Document;
import com.wave.dagger.model.Friendship;
import com.wave.dagger.model.JwtRequest;
import com.wave.dagger.model.JwtResponse;
import com.wave.dagger.model.Member;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface CardsAndFilesInterface {

    interface DocumentAPI {

        @DELETE("members/documents")
        Call<String> deleteDocument(@Header("Authorization") String authorization, @Header("documentId") int documentId);

        @GET("members/documents")
        Call<List<Document>> getDocuments(@Header("Authorization") String authorization);

        @POST("members/documents")
        Call<String> sendDocumentOnEmail(@Header("Authorization") String authorization, @Header("documentId") int documentId);

        @Multipart
        @POST("members/documents")
        Call<String> uploadDocument(@Header("Authorization") String authorization,
                                    @Part MultipartBody.Part file,
                                    @Header("filename") String fileName,
                                    @Header("type") String type);

        @PUT("members/documents")
        Call<String> updateDocument(@Header("Authorization") String authorization, @Body Document document);

    }

    interface LoginAPI {
        //done
        @POST("authenticate")
        Call<JwtResponse> authenticate(@Body JwtRequest request);
        //done
        @GET("members/token")
        Call<Member> getMemberFromToken(@Header("Authorization") String authorization);
        //done
        @POST("register")
        Call<String> registerMember(@Body Member member);
        //done
        @POST("recovery")
        Call<String> goRecovery(@Header("email") String email);

    }

    interface FriendshipAPI {
        @POST("members/friendships")
        Call<String> addFriendship(@Header("Authorization") String authorization, @Header("friendEmail") String friendEmail);

        @GET("members/friendships")
        Call<List<Friendship>> getListofFriends(@Header("Authorization") String authorization);

        @PUT("members/friendships")
        Call<String> acceptOrDenyFriendship(@Header("Authorization") String authorization, @Header("friendId") int friendId, @Header("answer") int answer);

        @DELETE("members/friendships")
        Call<String> deleteFriendship(@Header("Authorization") String authorization,@Header("friendId") int friendId);


    }

    interface MemberProfile {
        //done
        @DELETE("members")
        Call<String> deleteMember(@Header("Authorization") String authorization);

        @PUT("members")
        Call<Member> updateMember(@Header("Authorization") String authorization, @Body Member member);


    }
}
