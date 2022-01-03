package com.hubspot.jinjava.lib.exptest;

import com.hubspot.jinjava.doc.annotations.JinjavaDoc;
import com.hubspot.jinjava.doc.annotations.JinjavaParam;
import com.hubspot.jinjava.doc.annotations.JinjavaSnippet;
import com.hubspot.jinjava.el.TruthyTypeConverter;
import com.hubspot.jinjava.interpret.JinjavaInterpreter;
import com.hubspot.jinjava.interpret.TemplateSyntaxException;
import com.hubspot.jinjava.el.misc.BooleanOperations;
import com.hubspot.jinjava.el.misc.TypeConverter;

@JinjavaDoc(
  value = "Returns true if the first object's value is strictly less than the second",
  input = @JinjavaParam(value = "first", type = "object", required = true),
  params = {
    @JinjavaParam(
      value = "other",
      type = "object",
      desc = "Another object to compare against",
      required = true
    )
  },
  snippets = {
    @JinjavaSnippet(
      code = "{% if foo.expression is lt 42 %}\n" +
      "    the foo attribute evaluates to the constant 41\n" +
      "{% endif %}\n"
    ),
    @JinjavaSnippet(
      desc = "Usage with the selectattr filter",
      code = "{{ users|selectattr(\"num\", \"lt\", \"2\") }}"
    )
  }
)
public class IsLtTest implements ExpTest {
  private static final TypeConverter TYPE_CONVERTER = new TruthyTypeConverter();

  @Override
  public String getName() {
    return "lt";
  }

  @Override
  public boolean evaluate(Object var, JinjavaInterpreter interpreter, Object... args) {
    if (args.length == 0) {
      throw new TemplateSyntaxException(
        interpreter,
        getName(),
        "requires 1 argument (other object to compare against)"
      );
    }

    return BooleanOperations.lt(TYPE_CONVERTER, var, args[0]);
  }
}
