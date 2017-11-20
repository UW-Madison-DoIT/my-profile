package edu.wisc.my.profile.log;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import org.apache.commons.lang3.StringUtils;

public class MaskedLoggables {
  private MaskedLoggables() {}
  public static String toStringForLogging(MaskedLoggable toLog) {
    String result = "";
    if (null != toLog) {
      result = toLog.toStringForLogging();
    }
    return result;
  }
  public static String toStringForLogging(MaskedLoggable... toLog) {
    StringBuilder result = new StringBuilder();
    result.append("[");
    if (null != toLog) {
      result.append(StringUtils.join(FluentIterable.from(toLog).transform(new Function<MaskedLoggable, String>() {
        @Override
        public String apply(MaskedLoggable element) {
          String result = null;
          if (null != element) {
            result = element.toStringForLogging();
          }
          return result;
        }
      }), ", "));
    }
    result.append("]");
    return result.toString();
  }
}
