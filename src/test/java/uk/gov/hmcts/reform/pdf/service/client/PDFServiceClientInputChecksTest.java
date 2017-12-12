package uk.gov.hmcts.reform.pdf.service.client;

import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import static java.util.Collections.emptyMap;

public class PDFServiceClientInputChecksTest {

    private PDFServiceClient client;

    @Before
    public void beforeEachTest() throws URISyntaxException {
        client = new PDFServiceClient(new URI("http://this-can-be-anything/"));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowIllegalArgumentExceptionWhenGivenNullTemplate() {
        client.generateFromHtml(null, null, emptyMap());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenGivenEmptyTemplate() {
        client.generateFromHtml(null, new byte[] { }, emptyMap());
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowIllegalArgumentExceptionWhenGivenNullPlaceholders() {
        client.generateFromHtml(null, "content".getBytes(Charset.defaultCharset()), null);
    }

    @Test(expected = NullPointerException.class)
    public void constructorShouldThrowNullPointerWWhenGivenNullServiceURLString() {
        new PDFServiceClient(null, null);
    }

}
