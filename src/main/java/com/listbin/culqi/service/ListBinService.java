package com.listbin.culqi.service;

import com.listbin.culqi.model.InputModel;
import com.listbin.culqi.model.InputValidMOdel;
import com.listbin.culqi.model.ResponseModel;
import com.listbin.culqi.model.ResponseValidModel;

public interface ListBinService {

	ResponseModel getBrandResponse(InputModel inputModel);

	ResponseValidModel getValidService(String cadena);

}
