package utility;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * Created by StefanB on 5/15/2017.
 */
@Singleton
public class TestConfiguration implements TestConfigurationHandler{

    @Inject
    @Named("SECURE")
    String protocol;
    @Inject
    @Named("URL")
    String url;
    @Inject
    @Named("BROWSER")
    String browserType;
    @Inject
    @Named("DEBUG")
    String debug;

    public String getBrowser() {
        return browserType;
    }

    public String getWebSite() {
        return getWebSite();
    }

    public String getWebSite(String TLD) {
        String serverUrl = getProtocol() + url;
        return serverUrl;
    }

    public String getProtocol() {
        return (protocol != null && protocol.equalsIgnoreCase("true")) ? "https://" : "http://";
    }
}
