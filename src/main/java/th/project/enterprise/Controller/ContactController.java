package th.project.enterprise.Controller;

import th.project.enterprise.Entity.User;
import th.project.enterprise.Service.EmailService;
import th.project.enterprise.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
@RequestMapping("/Contact")
public class ContactController {

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;


    @GetMapping("ContactUs")
    public String showContactPage() {
        return "contact";
    }

    @GetMapping("/ContactAdmin")
    public String sendEmailToAdmin(@Param("Emailsubject") String Emailsubject, @Param("EmailText") String EmailText, Principal principal) {
        User user1 = userService.findByEmail(principal.getName());
        if (user1 == null) {
            return "redirect:/User/logout";
        } else {
            //          emailService.userEmailToAdmin(user1, EmailText, Emailsubject);
            return "successSentEmail";
        }
    }
}
