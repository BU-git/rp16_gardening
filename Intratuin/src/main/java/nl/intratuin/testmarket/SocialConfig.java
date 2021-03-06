package nl.intratuin.testmarket;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

@Configuration
public class SocialConfig implements SocialConfigurer {

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
        cfConfig.addConnectionFactory(new FacebookConnectionFactory(
                env.getProperty("facebook.appId"),
                env.getProperty("facebook.appSecret")));
        cfConfig.addConnectionFactory(new TwitterConnectionFactory(
                env.getProperty("twitter.consumerKey"),
                env.getProperty("twitter.consumerSecret")));
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

