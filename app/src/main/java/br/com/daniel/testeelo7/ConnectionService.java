package br.com.daniel.testeelo7;

import java.util.List;

import br.com.daniel.testeelo7.Models.RawRepositorio;
import br.com.daniel.testeelo7.Models.Repositorio;
import br.com.daniel.testeelo7.Models.Token;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface ConnectionService {

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("login/oauth/access_token")
    Call<Token> getToken(
            @Field("client_id") String ClientId, @Field("client_secret") String ClientSecret,
            @Field("code") String Code
    );

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/user/repos")
    Call<List<Repositorio>> getRepos(
            @Header("Authorization") String authorization, @Query("visibility") String visibilidade
    );

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("")
    Call<RawRepositorio> getReadme(@Url String url);

}
