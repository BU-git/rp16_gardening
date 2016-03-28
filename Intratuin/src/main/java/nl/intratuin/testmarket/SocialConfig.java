package nl.intratuin.testmarket;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

@Configuration
public class SocialConfig implements SocialConfigurer {

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
        cfConfig.addConnectionFactory(new FacebookConnectionFactory(
                env.getProperty("facebook.appId"),
                env.getProperty("facebook.appSecret")));
    }

    @Override
    public org.springframework.social.UserIdSource getUserIdSource() {
        return null;
    }

    @Override
    public org.springframework.social.connect.UsersConnectionRepository getUsersConnectionRepository(org.springframework.social.connect.ConnectionFactoryLocator connectionFactoryLocator) {
        return null;
    }
}

