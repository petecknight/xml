
package com.github.jcustenborder.kafka.connect.transform.xml;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.sink.SinkRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

public class FromXmlTest {

  FromXml.Value transform;

  @BeforeEach
  public void before() throws MalformedURLException {
    File file = new File("src/test/resources/com/github/jcustenborder/kafka/connect/transform/xml/books.xsd");
    this.transform = new FromXml.Value();
    this.transform.configure(
        ImmutableMap.of(FromXmlConfig.SCHEMA_PATH_CONFIG, file.getAbsoluteFile().toURL().toString())
    );
  }

  @AfterEach
  public void after() {
    this.transform.close();
  }

  @Test
  public void apply() throws IOException {
    final byte[] input = Files.toByteArray(new File("src/test/resources/com/github/jcustenborder/kafka/connect/transform/xml/books.xml"));
    final ConnectRecord inputRecord = new SinkRecord(
        "test",
        1,
        null,
        null,
        org.apache.kafka.connect.data.Schema.BYTES_SCHEMA,
        input,
        new Date().getTime()
    );

    ConnectRecord record = this.transform.apply(inputRecord);
  }

}
