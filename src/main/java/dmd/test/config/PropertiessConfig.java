package dmd.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:custom.properties")
public class PropertiessConfig {
    @Autowired
    private Environment env;

    public String getValue(String key) {
        return env.getProperty(key);
    }
}
