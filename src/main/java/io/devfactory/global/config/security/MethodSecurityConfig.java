package io.devfactory.global.config.security;

import io.devfactory.global.config.security.factory.MethodResourcesMapFactoryBean;
import io.devfactory.global.config.security.processor.ProtectPointcutPostProcessor;
import io.devfactory.global.config.security.service.ResourceMappingConfigService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.method.MapBasedMethodSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

// prePostEnabled 기본 값인 false로 해도 됨, 다른 곳에서 샘플로 쓴 곳이 있어서 그냥 둠...
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

  private final ResourceMappingConfigService resourceMappingConfigService;

  @Override
  protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
    return mapBasedMethodSecurityMetadataSource();
  }

  @Bean
  public MapBasedMethodSecurityMetadataSource mapBasedMethodSecurityMetadataSource() {
    return new MapBasedMethodSecurityMetadataSource(
        Objects.requireNonNull(methodResourcesMapFactoryBean().getObject()));
  }

  @Bean
  public MethodResourcesMapFactoryBean methodResourcesMapFactoryBean() {
    final MethodResourcesMapFactoryBean methodResourcesMapFactoryBean =
        new MethodResourcesMapFactoryBean(resourceMappingConfigService);

    methodResourcesMapFactoryBean.setResourceType("method");
    return methodResourcesMapFactoryBean;
  }

  @Bean
  public MethodResourcesMapFactoryBean pointcutResourcesMapFactoryBean() {
    final MethodResourcesMapFactoryBean methodResourcesMapFactoryBean =
        new MethodResourcesMapFactoryBean(resourceMappingConfigService);

    methodResourcesMapFactoryBean.setResourceType("pointcut");
    return methodResourcesMapFactoryBean;
  }

  @Bean
  public ProtectPointcutPostProcessor protectPointcutPostProcessor() {
    ProtectPointcutPostProcessor protectPointcutPostProcessor = new ProtectPointcutPostProcessor(
        mapBasedMethodSecurityMetadataSource());
    protectPointcutPostProcessor.setPointcutMap(pointcutResourcesMapFactoryBean().getObject());
    return protectPointcutPostProcessor;
  }

//  @Bean
//  BeanPostProcessor protectPointcutPostProcessor() throws Exception {
//    Class<?> clazz = Class
//        .forName("org.springframework.security.config.method.ProtectPointcutPostProcessor");
//
//    Constructor<?> declaredConstructor = clazz
//        .getDeclaredConstructor(MapBasedMethodSecurityMetadataSource.class);
//    declaredConstructor.setAccessible(true);
//
//    Object instance = declaredConstructor.newInstance(mapBasedMethodSecurityMetadataSource());
//    Method setPointcutMap = instance.getClass().getMethod("setPointcutMap", Map.class);
//
//    setPointcutMap.setAccessible(true);
//    setPointcutMap.invoke(instance, pointcutResourcesFactoryBean().getObject());
//
//    return (BeanPostProcessor) instance;
//  }

}
