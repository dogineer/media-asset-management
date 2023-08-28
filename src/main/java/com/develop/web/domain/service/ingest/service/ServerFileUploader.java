package com.develop.web.domain.service.ingest.service;

import com.develop.web.domain.service.ingest.dto.Metadata;
import com.develop.web.domain.service.ingest.mapper.UploadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ServerFileUploader {

    @Value("${CNPS.MC.URL}")
    private String mc;
    private final UploadMapper uploadMapper;
    public WebClient webClient() {
        return WebClient
            .builder()
            .baseUrl(mc)
            .build();
    }

    public Mono<Metadata> uploadFileAndIngestId(Resource files, Integer ingestId) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("files", files);
        body.add("ingestId", ingestId);

        return webClient()
            .method(HttpMethod.POST)
            .uri("/api/upload/")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(body))
            .retrieve()
            .bodyToMono(Metadata.class)
            .doOnSuccess(uploadMapper::insertMetadata);
    }
}