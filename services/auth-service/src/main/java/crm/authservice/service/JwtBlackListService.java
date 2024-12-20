package crm.authservice.service;

public interface JwtBlackListService {

    boolean blackListToken(String token);

    boolean isTokenBlacklisted(String token);

    long getExpirationTime(String token);
}
