package de.trundicho.onion.billing.application;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import lombok.Setter;
import lombok.SneakyThrows;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.RecordedRequest;

@Setter
class MockWebServerDispatcher extends Dispatcher {

    private Map<String, MockResponse> responses;

    @SneakyThrows
    @Override
    public okhttp3.mockwebserver.MockResponse dispatch(RecordedRequest recordedRequest) {
        String request = recordedRequest.getMethod() + recordedRequest.getPath();
        if (responses.containsKey(request)) {
            MockResponse response = responses.get(request);
            String responseJsonFile = response.getResponseJsonFile();
            String json = fileToString(responseJsonFile);
            okhttp3.mockwebserver.MockResponse mockResponse = new okhttp3.mockwebserver.MockResponse();
            if(json != null) {
                mockResponse.setBody(json);
            }
            return mockResponse
                               .setResponseCode(response.getHttpStatus().value())
                               .setHeader("Content-Type", "application/json");
        }
        throw new IllegalStateException("Request mapping not yet implemented " + request);
    }

    private String fileToString(String responseJsonFile) throws IOException, URISyntaxException {
        if (responseJsonFile != null) {
            URL resource = this.getClass().getClassLoader().getResource(responseJsonFile);
            return new String(Files.readAllBytes(Paths.get(resource.toURI())));
        }
        return null;
    }
}
