package com.ssafy.userservice.util.error;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		switch (response.status()) {
			case 400:
				break;
			case 404:
				if (methodKey.contains("getOreders"))
					return new ResponseStatusException(HttpStatus.valueOf(response.status()), "exception");
				break;
			default:
				return new Exception(response.reason());

		}
		return null;
	}
}
