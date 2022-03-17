package com.alkemy.ong.domain.users;

import com.alkemy.ong.domain.mail.MailService;
import com.alkemy.ong.web.utils.MailUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserGateway userGateway;
    private final MailService mailService;


    public UserService(UserGateway userGateway, MailService mailService) {
        this.userGateway = userGateway;
        this.mailService = mailService;
    }


    public List<User> findAll() {
        return userGateway.findAll();
    }


    public List<User> findByDeleted(boolean isDeleted) {
        return userGateway.findByDeleted(isDeleted);
    }


    public User findByEmail(String email) {
        return userGateway.findByEmail(email);
    }

    public User findById(Long id) {
        return userGateway.findById(id);
    }

    public User save(User user){
        mailService.sendMailWithTemplate(user.getEmail(), "Successful registration", MailUtils.TEMPLATE);
        return userGateway.create(user);
    }

    public void deleteById(Long id){
        userGateway.deleteById(id);
    }

    public boolean existsByEmail(String email){
        return userGateway.existsByEmail(email);
    }

    public User update(Long id, User user) {
        return userGateway.update(id, user);
    }
}
