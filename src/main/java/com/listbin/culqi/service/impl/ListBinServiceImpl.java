package com.listbin.culqi.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.listbin.culqi.model.InputModel;
import com.listbin.culqi.model.ResponseModel;
import com.listbin.culqi.model.ResultBinlistModel;
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

}
