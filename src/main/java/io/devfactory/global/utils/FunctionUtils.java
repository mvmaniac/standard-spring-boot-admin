package io.devfactory.global.utils;

import java.util.function.UnaryOperator;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FunctionUtils {

    public static final UnaryOperator<String> REDIRECT = path -> "redirect:"+ path;

}
