package com.kolmanfreecss.kolmanfreecss.datagithub.application.service.github;

import com.kolmanfreecss.kolmanfreecss.datagithub.domain.model.GithubData;
import reactor.core.publisher.Mono;

/**
 * Github Data Service
 *
 * @version 1.0
 * @uthor Kolman-Freecss
 */
public interface GithubDataService {
    Mono<GithubData> getBasicInfo();
    
}
