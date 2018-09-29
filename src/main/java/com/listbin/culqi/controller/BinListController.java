package com.listbin.culqi.controller;

import com.listbin.culqi.model.InputValidMOdel;
import com.listbin.culqi.model.ResponseValidModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.listbin.culqi.model.InputModel;
import com.listbin.culqi.model.ResponseModel;
import com.listbin.culqi.service.ListBinService;

@RestController
public class BinListController {

	@Autowired
	private ListBinService listBinService;
	
	@RequestMapping(value = "/tokens", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> getServiceJson(@RequestBody InputModel inputModel, @RequestHeader String api_key) throws Exception
	{
		ResponseModel responseModel = new ResponseModel();
		ResponseValidModel responseValidModel = new ResponseValidModel();
		
		if (api_key.substring(1, api_key.length()-1).trim().equals("")){
			return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.UNAUTHORIZED);
		}else {
			responseValidModel = listBinService.getValidService(api_key);
		}
			

		responseModel = listBinService.getBrandResponse(inputModel);
		
		return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
	}
}
