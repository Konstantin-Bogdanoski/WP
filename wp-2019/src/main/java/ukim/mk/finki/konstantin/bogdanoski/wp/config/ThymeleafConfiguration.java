package ukim.mk.finki.konstantin.bogdanoski.wp.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
//@Configuration
public class ThymeleafConfiguration {
    private final ApplicationContext applicationContext;

    public ThymeleafConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private SpringResourceTemplateResolver templateResolver() {
        final SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setCacheable(false);
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    //@Bean
    public SpringTemplateEngine getSpringTemplateEngine() {
        final SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.addTemplateResolver(templateResolver());
        return springTemplateEngine;
    }

}
