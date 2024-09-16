package com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.adapters.output.github;

import com.kolmanfreecss.kolmanfreecss.datagithub.domain.model.GithubData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/**
 * Github Data Service Implementation
 *
 * @author Kolman-Freecss
 * @version 1.0
 */
class GithubDataServiceImplTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    // Initializes the mocks and injects them into the GithubDataServiceImpl instance
    // Not works correctly because the constructor wants a WebClient.Builder before the mocks are initialized in "setUp"
//    @InjectMocks 
    private GithubDataServiceImpl githubDataServiceImpl;

    @BeforeEach
    void setUp() {
        // Initializes the mocks
        MockitoAnnotations.openMocks(this);

        // Configures the WebClient.Builder mock to return the builder after baseUrl and defaultHeader
        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.defaultHeader(anyString(), anyString())).thenReturn(webClientBuilder);

        // Configures the WebClient mock to return the WebClient when build is called
        when(webClientBuilder.build()).thenReturn(webClient);
        
        // Initializes the real GithubDataServiceImpl instance with the mocked WebClient
        // This is unnecessary if we are using the @InjectMocks annotation
         githubDataServiceImpl = new GithubDataServiceImpl(webClientBuilder); 
    }

    @Test
    void testGetBasicInfo() {
        // Mocking the webClient behavior
        GithubData mockGithubData = new GithubData(); // Initialize expected data

        doReturn(requestHeadersUriSpec).when(webClient).get(); // Workaround to avoid type-safety issues
        doReturn(requestHeadersUriSpec).when(requestHeadersUriSpec).uri(anyString()); // Workaround to avoid type-safety issues
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(GithubData.class)).thenReturn(Mono.just(mockGithubData));

        // Call the method under test
        Mono<GithubData> result = githubDataServiceImpl.getBasicInfo();

        // Assertions
        assertNotNull(result);
        GithubData githubData = result.block(); // Synchronously retrieve the result for testing
        assertNotNull(githubData);
    }

    @Test
    void testGetBasicInfoError() {
        // Mocking an error response
        doReturn(requestHeadersUriSpec).when(webClient).get(); // Workaround to avoid type-safety issues
        doReturn(requestHeadersUriSpec).when(requestHeadersUriSpec).uri(anyString()); // Workaround to avoid type-safety issues
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(GithubData.class)).thenReturn(Mono.error(new RuntimeException("Error")));

        // Call the method under test
        Mono<GithubData> result = githubDataServiceImpl.getBasicInfo();

        // Assertions for error case
        assertThrows(RuntimeException.class, result::block);
    }

}
