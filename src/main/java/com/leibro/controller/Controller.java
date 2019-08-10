package com.leibro.controller;

import com.leibro.http.HttpRequest;
import com.leibro.http.HttpResponse;

public interface Controller {
    HttpResponse doHandlerRequest(HttpRequest request);
}
