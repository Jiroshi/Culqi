package com.listbin.culqi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.listbin.culqi.model.InputModel;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestWebApp {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testEmployee() throws Exception {
		InputModel inputModel = new InputModel();
		inputModel.setPan("4444333322221111");
		inputModel.setExp_month(10);
		inputModel.setExp_year(2020);
		Gson gson = new Gson();
		
		String json = gson.toJson(inputModel);
		mockMvc.perform(post("/tokens").contentType("application/json;charset=UTF-8").content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.brand").value("visa"));
	
	}
}
