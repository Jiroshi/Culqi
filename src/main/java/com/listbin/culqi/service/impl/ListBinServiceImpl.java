package com.listbin.culqi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.listbin.culqi.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.listbin.culqi.service.ListBinService;
import com.listbin.culqi.util.Constants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class ListBinServiceImpl implements ListBinService{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public ResponseModel getBrandResponse(InputModel inputModel) {
		ResponseModel responseModel = new ResponseModel();
		try {
			RestTemplate restTemplate = new RestTemplate();
			String bin = inputModel.getPan().substring(0, 6);
			ResultBinlistModel result = restTemplate.getForObject(
					Constants.BASE_URL+bin, ResultBinlistModel.class);

			Long time = System.currentTimeMillis();
			
			String token = generateToken(inputModel, time);
			responseModel.setToken(token);
			responseModel.setBrand(result.getScheme());
			responseModel.setCreacion_dt(String.valueOf(new Date(time)));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return responseModel;
	}
	
	private String generateToken(InputModel inputModel, Long time) {

		String token = Jwts.builder().setSubject(Constants.NAME).signWith(SignatureAlgorithm.HS512, Constants.KEY).
				claim(Constants.VAR_PAN, inputModel.getPan()).claim(Constants.VAR_EXP_YEAR, inputModel.getExp_year()).
				claim(Constants.VAR_EXP_MONTH, inputModel.getExp_month()).setIssuedAt(new Date(time)).compact();
	
		return token;
	}

	public ResponseValidModel getValidService(String api_key){
		ResponseValidModel responseValidModel = new ResponseValidModel();

		RestTemplate restTemplate = new RestTemplate();

//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
		
		
		
//		HttpEntity entity = new HttpEntity(headers);
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("api_key", api_key);
//		InputValidMOdel inputValidMOdel = new InputValidMOdel();
//		inputValidMOdel.setApi_key(api_key);

//		String params = api_key;
//		responseValidModel= restTemplate.getForObject(
//				Constants.END_POINT_VALIDATE, HttpMethod.GET,ResponseValidModel.class, inputValidMOdel);

//		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("api_key", api_key);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

//		ResponseEntity<ResponseValidModel> responseValidModelAux = restTemplate.exchange(Constants.END_POINT_VALIDATE, HttpMethod.POST, entity, ResponseValidModel.class, api_key);

		ResponseEntity<ResponseValidModel> responseValidModelAux = restTemplate.exchange(Constants.END_POINT_VALIDATE, HttpMethod.POST, request, ResponseValidModel.class);
//		responseValidModel.setValid(responseValidModelAux.getBody().getValid());
		String value = responseValidModelAux.getBody().getValid();
		
		responseValidModel.setValid(value);
//		responseValidModel = restTemplate.postForObject(Constants.END_POINT_VALIDATE, inputValidMOdel, ResponseValidModel.class);
		return responseValidModel;
	}

}
