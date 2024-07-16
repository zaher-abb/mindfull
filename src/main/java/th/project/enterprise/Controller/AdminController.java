package th.project.enterprise.Controller;

import th.project.enterprise.Entity.FileUploader;

import th.project.enterprise.Entity.User;
import th.project.enterprise.Service.EmailService;
import th.project.enterprise.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("Admin")
public class AdminController {



    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;


    @GetMapping("/addUser")
    public String viewAddUSerForm(Model model) {
        model.addAttribute("user", new User());
        return "AdminAddUser";
    }

    @PostMapping("/addUser")
    public String addUserByAdmin(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "AdminAddUser";
        }

        if (userService.isUserPresent(user.getEmail())) {
            model.addAttribute("exist", true);
            return "AdminAddUser";
        }
        userService.creatUser(user);
        model.addAttribute("success", true);
        try {

            //        emailService.registrationConfirmationEmail(user);
        } catch (MailException ignored) {

        }
        return "redirect:/Admin/viewAdminPage";
    }



}
