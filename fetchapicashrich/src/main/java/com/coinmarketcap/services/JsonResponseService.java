package com.coinmarketcap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coinmarketcap.entity.JsonResponse;
import com.coinmarketcap.repository.JsonResponseRepository;

@Service
public class JsonResponseService {
	
	@Autowired
    private JsonResponseRepository jsonResponseRepository;

    public JsonResponse saveJsonResponse(String jsonContent) {
        JsonResponse response = new JsonResponse();
        response.setJsonContent(jsonContent);
        return jsonResponseRepository.save(response);
    }
}
