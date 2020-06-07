package io.devfactory.global.common.dialect;

import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

@Component
public class CustomDialect extends AbstractDialect implements IExpressionObjectDialect {

  @SuppressWarnings("squid:S116")
  private final IExpressionObjectFactory CUSTOM_DIALECT_EXPRESSION_OBJECTS_FACTORY = new CustomDialectExpressionFactory();

  public CustomDialect() {
    super("CustomDialect");
  }

  @Override
  public IExpressionObjectFactory getExpressionObjectFactory() {
    return CUSTOM_DIALECT_EXPRESSION_OBJECTS_FACTORY;
  }

}
