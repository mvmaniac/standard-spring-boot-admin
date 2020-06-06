package io.devfactory.global.dialect;

import java.util.Set;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;

public class CustomDialectExpressionFactory implements IExpressionObjectFactory {

  private static final String VARIABLE_NAME = "custom";
  private static final Set<String> ALL_EXPRESSION_OBJECT_NAMES = Set.of(VARIABLE_NAME);

  @Override
  public Set<String> getAllExpressionObjectNames() {
    return ALL_EXPRESSION_OBJECT_NAMES;
  }

  @Override
  public Object buildObject(IExpressionContext context, String expressionObjectName) {
    if (VARIABLE_NAME.equals(expressionObjectName)) {
      return new CustomDialectUtil();
    }
    return null;
  }

  @Override
  public boolean isCacheable(String expressionObjectName) {
    return false;
  }

}
