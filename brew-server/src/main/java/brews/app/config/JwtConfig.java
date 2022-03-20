package brews.app.config;

public interface JwtConfig {
    public String getJwtSecretKey();
    public Integer getJwtExpiration();
}
