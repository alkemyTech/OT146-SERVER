package com.alkemy.ong.web;

import com.alkemy.ong.domain.models.Testimonial;
import com.alkemy.ong.domain.services.TestimonialService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testimonials")
public class TestimonialController {

    private TestimonialService testimonialService;

    public TestimonialController(TestimonialService testimonialService) {
        this.testimonialService = testimonialService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> getAll(@RequestBody TestimonialDTO testimonialInput) {
        try{
            if (testimonialInput == null)
                throw new Exception("not request content");
            if(testimonialInput.name == null || testimonialInput.name.equals(""))
                throw new Exception("name is invalid");
            if(testimonialInput.content == null || testimonialInput.content.equals(""))
                throw new Exception("content is invalid");

            Testimonial testimonial = testimonialService.save(convertToDomain(testimonialInput));

            TestimonialDTO testimonialOut = convertToOutput(testimonial);

            return ResponseEntity.status(HttpStatus.OK).body(testimonialOut);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponseBasic(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    private Testimonial convertToDomain(TestimonialDTO testimonialDTO){
        return new Testimonial(
                null,
                testimonialDTO.getName(),
                testimonialDTO.getImage(),
                testimonialDTO.getContent());
    }

    private TestimonialDTO convertToOutput(Testimonial testimonialDomain){
        return new TestimonialDTO(
                testimonialDomain.getId(),
                testimonialDomain.getName(),
                testimonialDomain.getImage(),
                testimonialDomain.getContent());
    }

    @Getter @Setter @AllArgsConstructor
    public static class TestimonialDTO {
        private Long id;
        private String name;
        private String image;
        private String content;
    }

    @Getter @Setter @AllArgsConstructor
    public static class ErrorResponseBasic{
        private int status;
        private String messaje;
    }


}

