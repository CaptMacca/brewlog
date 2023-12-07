package brews.app.config;

public interface JwtConfig {

    String getJwtSecretKey();

    Integer getJwtExpiration() ;
}