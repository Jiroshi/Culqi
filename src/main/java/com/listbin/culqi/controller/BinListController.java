package com.listbin.culqi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.listbin.culqi.model.InputModel;
import com.listbin.culqi.model.ResponseModel;
import com.listbin.culqi.service.ListBinService;

@RestController
public class BinListController {

	@Autowired
	private ListBinService listBinService;
	
	@RequestMapping(value = "/tokens", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> getServiceJson(@RequestBody InputModel inputModel) throws Exception
	{
		ResponseModel responseModel = new ResponseModel();
		
		responseModel = listBinService.getBrandResponse(inputModel);
		
		return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
	}
}
