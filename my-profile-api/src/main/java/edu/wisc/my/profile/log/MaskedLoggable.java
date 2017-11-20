package edu.wisc.my.profile.log;

public interface MaskedLoggable {
  /**
   * Use for logging when masking values is needed.
   * Data that is considered sensitive will be masked.
   * @return a JSON representation with the value data masked, useful for logging
   */
  public String toStringForLogging();
}
