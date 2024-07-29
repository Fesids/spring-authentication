package com.application.app.configs;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Getter
@Configuration("swaggerProperties")
public class SwaggerProperties {
    @Value("${api.version}")
    private String apiVersion;

    @Value("${swagger.enabled}")
    private String enabled = "false";

    @Value("${swagger.title}")
    private String title;

    @Value("${swagger.description}")
    private String description;

    @Value("${swagger.useDefaultResponseMessages}")
    private String useDefaultResponseMessages;

    @Value("${swagger.enableUrlTemplating}")
    private String enableUrlTemplating;

    @Value("${swagger.deepLinking}")
    private String deepLinking;

    @Value("${swagger.defaultModelsExpandDepth}")

    private String defaultModelsExpandDepth;

    @Value("${swagger.defaultModelExpandDepth}")
    private String defaultModelExpandDepth;

    @Value("${swagger.displayOperationId}")
    private String displayOperationId;

    @Value("${swagger.displayRequestDuration}")
    private String displayRequestDuration;

    @Value("${swagger.filter}")
    private String filter;

    @Value("${swagger.maxDisplayedTags}")
    private String maxDisplayedTags;

    @Value("${swagger.showExtensions}")
    private String showExtensions;

    public SwaggerProperties setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
        return this;
    }

    public SwaggerProperties setEnabled(String enabled) {
        this.enabled = enabled;
        return this;
    }

    public SwaggerProperties setTitle(String title) {
        this.title = title;
        return this;
    }

    public SwaggerProperties setDescription(String description) {
        this.description = description;
        return this;
    }

    public SwaggerProperties setUseDefaultResponseMessages(String useDefaultResponseMessages) {
        this.useDefaultResponseMessages = useDefaultResponseMessages;
        return this;
    }

    public SwaggerProperties setEnableUrlTemplating(String enableUrlTemplating) {
        this.enableUrlTemplating = enableUrlTemplating;
        return this;
    }

    public SwaggerProperties setDeepLinking(String deepLinking) {
        this.deepLinking = deepLinking;
        return this;
    }

    public SwaggerProperties setDefaultModelsExpandDepth(String defaultModelsExpandDepth) {
        this.defaultModelsExpandDepth = defaultModelsExpandDepth;
        return this;
    }

    public SwaggerProperties setDefaultModelExpandDepth(String defaultModelExpandDepth) {
        this.defaultModelExpandDepth = defaultModelExpandDepth;
        return this;
    }

    public SwaggerProperties setDisplayOperationId(String displayOperationId) {
        this.displayOperationId = displayOperationId;
        return this;
    }

    public SwaggerProperties setDisplayRequestDuration(String displayRequestDuration) {
        this.displayRequestDuration = displayRequestDuration;
        return this;
    }

    public SwaggerProperties setFilter(String filter) {
        this.filter = filter;
        return this;
    }

    public SwaggerProperties setMaxDisplayedTags(String maxDisplayedTags) {
        this.maxDisplayedTags = maxDisplayedTags;
        return this;
    }

    public SwaggerProperties setShowExtensions(String showExtensions) {
        this.showExtensions = showExtensions;
        return this;
    }
}
