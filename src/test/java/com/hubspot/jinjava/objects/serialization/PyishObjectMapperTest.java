package com.hubspot.jinjava.objects.serialization;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableMap;
import com.hubspot.jinjava.objects.collections.SizeLimitingPyMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class PyishObjectMapperTest {

  @Test
  public void itSerializesMapWithNullKeysAsEmptyString() {
    Map<String, Object> map = new SizeLimitingPyMap(new HashMap<>(), 10);
    map.put("foo", "bar");
    map.put(null, "null");
    assertThat(PyishObjectMapper.getAsPyishString(map))
      .isEqualTo("{'': 'null', 'foo': 'bar'} ");
  }

  @Test
  public void itSerializesMapEntrySet() throws JsonProcessingException {
    SizeLimitingPyMap map = new SizeLimitingPyMap(new HashMap<>(), 10);
    map.put("foo", "bar");
    map.put("bar", ImmutableMap.of("foobar", new ArrayList<>()));
    String result = PyishObjectMapper.getAsPyishString(map.items());
    assertThat(result)
      .isEqualTo("[fn:map_entry('bar', {'foobar': []} ), fn:map_entry('foo', 'bar')]");
  }

  @Test
  public void itSerializesMapWithNullValues() {
    Map<String, Object> map = new SizeLimitingPyMap(new HashMap<>(), 10);
    map.put("foo", "bar");
    map.put("foobar", null);
    assertThat(PyishObjectMapper.getAsPyishString(map))
      .isEqualTo("{'foobar': null, 'foo': 'bar'} ");
  }

  @Test
  public void itLimitsDepth() {
    final List<Object> original = new ArrayList<>();
    List<Object> list = original;
    for (int i = 0; i < 20; i++) {
      List<Object> temp = new ArrayList<>();
      list.add(i);
      list.add(temp);
      list = temp;
    }
    list.add("a");
    assertThat(PyishObjectMapper.getAsPyishString(original))
      .isEqualTo("[0, [1, [2, [3, [4, []]]]]]");
  }
}
