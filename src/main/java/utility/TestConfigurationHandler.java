package utility;

import com.google.inject.Singleton;

/**
 * Created by StefanB on 5/15/2017.
 *
 * Environment variables
 */
@Singleton
public interface TestConfigurationHandler {

    String getBrowser();

    String getWebSite();

}