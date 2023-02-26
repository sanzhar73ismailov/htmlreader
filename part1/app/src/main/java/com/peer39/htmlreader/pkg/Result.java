package com.peer39.htmlreader.pkg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {

    private String url;
    private String text;

    private boolean success;

    private String errorMessage;
}
