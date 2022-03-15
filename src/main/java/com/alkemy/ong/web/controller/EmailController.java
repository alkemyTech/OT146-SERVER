package com.alkemy.ong.web.controller;

import com.alkemy.ong.data.gateways.SendGridMailGateway;
import com.alkemy.ong.domain.mail.EmailRequest;
import com.sendgrid.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@RestController
public class EmailController {

    @Autowired
	private SendGridMailGateway  sendGridMailGateway;
	
	@PostMapping("/sendemail")
	public ResponseEntity<String> sendemail(@RequestBody EmailRequest emailrequest) {
		
		Response response=sendGridMailGateway.sendemail(emailrequest);
		if(response.getStatusCode()==200||response.getStatusCode()==202)
			return new ResponseEntity<>("send successfully",HttpStatus.OK);
		return new ResponseEntity<>("failed to sent",HttpStatus.NOT_FOUND);
			    
	}


	@Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
	private static class EmailDto {

		@ApiModelProperty(required = true, example = "abc@gmail.com")
		private String to;

		@ApiModelProperty(required = true, example = "subject")
		private String subject;

		@ApiModelProperty(required = true, example = "body")
		private String body;


	}

    
}
