package com.currencyconvert.web.rest;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import com.currencyconvert.CurrencyConvertApplication;
import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

@ActiveProfiles("api-test")
@AutoConfigureMockMvc
@SpringBootTest(
    classes = {
        CurrencyConvertApplication.class
    },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public abstract class BaseApiTest {

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  protected JsonSchemaFactory jsonSchemaFactory;

  @BeforeEach
  public void setUpBase() {
    RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

    final var validationConfiguration = ValidationConfiguration.newBuilder()
        .setDefaultVersion(SchemaVersion.DRAFTV4)
        .freeze();

    jsonSchemaFactory = JsonSchemaFactory.newBuilder()
        .setValidationConfiguration(validationConfiguration)
        .freeze();
  }
}