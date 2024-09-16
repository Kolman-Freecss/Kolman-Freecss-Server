package com.kolmanfreecss.kolmanfreecss.datagithub.domain.dto;

import java.util.Optional;

/**
 * @author Kolman-Freecss
 * @version 1.0
 */
public record GithubDataDto(String title, String description, String url, Optional<String> email) {

}
