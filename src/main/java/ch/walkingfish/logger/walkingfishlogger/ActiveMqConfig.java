package ch.walkingfish.logger.walkingfishlogger;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
@EnableJms
public class ActiveMqConfig {
    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String username;

    @Value("${spring.activemq.password}")
    private String password;


    /**
     * Template de gestion des messages
     * @param connectionFactory 
     * @param configurer le configurer
     * @return une instance de JmsListenerContainerFactory
     */
    @Bean
    public JmsListenerContainerFactory<?> jmsFactory(ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        // Use object mapper
        factory.setMessageConverter(messageConverter());

        configurer.configure(factory, connectionFactory);
        return factory;
    }

    /**
     * Composant de gestion de la connection jms
     * 
     * @return une instance de ConnectionFactory
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        org.apache.activemq.ActiveMQConnectionFactory connectionFactory = new org.apache.activemq.ActiveMQConnectionFactory();
        connectionFactory.setTrustAllPackages(true);
        connectionFactory.setBrokerURL(brokerUrl);
        connectionFactory.setUserName(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    /**
     * Composant de gestion des messages jms
     * 
     * @return une instance de MessageConverter
     */
    // @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setObjectMapper(objectMapper());
        // converter.setTypeIdPropertyName("_type");
        return converter;
    }

    /**
     * Instance de mapper pour désérialiser les messages json en Article
     * 
     * @return une instance de objectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return mapper;
    }
}
