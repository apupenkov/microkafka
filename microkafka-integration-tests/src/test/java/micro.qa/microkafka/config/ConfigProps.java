package micro.qa.microkafka.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({
        "classpath:${env}.properties"
})
public interface ConfigProps extends Config {

    @Key("micro.qa.microkafka.service.name") String getServiceName();

    @Key("api.baseurl") String getBaseUrl();

    @Key("api.fronturl") String getFrontUrl();

    @Key("api.authurl") String getAuthUrl();

    @Key("api.userdataurl") String getUserDataUrl();

    @Key("api.geourl") String getGeoUrl();

    @Key("api.photourl") String getPhotoUrl();

    @Key("db.host") String getDBHost();

    @Key("db.port") Integer getDBPort();

    @Key("db.user") String getDBUser();

    @Key("db.password") String getDBPassword();

    @Key("db.dialect") String getDBDialect();

    @Key("db.driver") String getDBDriver();

    @Key("db.url") String getDBUrl(String serviceName);

    @Key("db.p6spyurl") String getP6SpyUrl(String serviceName);

    @Key("db.p6spydriver") String getP6SpyDriver();
}
