package com.alkemy.ong.web.controllers;


import com.alkemy.ong.domain.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class EmailController {

    @Autowired
	private MailService mailService;
	
	@PostMapping("/sendemail")
	public ResponseEntity<String> sendemail(@RequestParam("to") String to, @RequestParam("subject") String subject, @RequestParam("body") String body) {
		
		Boolean email = mailService.sendMail(to, subject, body);

        if(email) {
            return new ResponseEntity<>("send successfully",HttpStatus.OK);
        } else {
            return new ResponseEntity<>("failed to sent",HttpStatus.NOT_FOUND);
        }	
			    
	} 

    
}
