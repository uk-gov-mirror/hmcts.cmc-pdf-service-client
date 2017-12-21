package uk.gov.hmcts.reform.pdf.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.util.function.Supplier;

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PDFServiceClientBuilderTest {

    private static final byte[] TEST_TEMPLATE = "<html><body>Hello</body></html>".getBytes();

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private RestOperations restOperations;
    @Mock
    private Supplier<String> s2sAuthTokenSupplier;

    private URI baseUri = URI.create("https://some-host");

    @Test
    public void itShouldBePossibleToBuildClientInstanceWithDefaults() {
        PDFServiceClient client = PDFServiceClient.builder().build(s2sAuthTokenSupplier, baseUri);
        assertThat(client).isNotNull();
    }

    @Test
    public void itShouldUseProvidedRestOperations() {
        PDFServiceClient client = PDFServiceClient.builder()
            .restOperations(restOperations)
            .build(s2sAuthTokenSupplier, baseUri);

        client.generateFromHtml(TEST_TEMPLATE, emptyMap());

        verify(restOperations).postForObject(any(URI.class), any(HttpEntity.class), eq(byte[].class));
    }

    @Test
    public void itShouldUseProvidedObjectMapper() throws Exception {
        PDFServiceClient client = PDFServiceClient.builder()
            .objectMapper(objectMapper)
            .restOperations(restOperations)
            .build(s2sAuthTokenSupplier, baseUri);

        client.generateFromHtml(TEST_TEMPLATE, emptyMap());

        verify(objectMapper).writeValueAsString(anyObject());
    }

}
