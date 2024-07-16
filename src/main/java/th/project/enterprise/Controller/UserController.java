package th.project.enterprise.Controller;

import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import th.project.enterprise.Entity.*;
import th.project.enterprise.Service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/User")
public class UserController {


    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;


    @Autowired
    StepsService stepsService;

    @Autowired
    TeamService teamService;

    @GetMapping("/register")
    public String viewRgisterPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/register")
    public String Register(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "signup";
        }
        if (userService.isUserPresent(user.getEmail())) {
            model.addAttribute("exist", true);
            return "signup";
        }

        String teamName = user.getTeamName().toLowerCase();

        if (teamService.chechTeamIsAlreadyExisted(teamName)) {
            Team t = teamService.getTeamByName(teamName);
            user.setTeamName(teamName);
            user.setTeam(t);

        } else {
            teamService.addNewTeam(teamName);
            Team t = teamService.getTeamByName(teamName);
            user.setTeamName(teamName);
            user.setTeam(t);
        }
        userService.creatUser(user);
        model.addAttribute("success", true);
        try {
            emailService.registrationConfirmationEmail(user);
        } catch (MailException ignored) {

        }
        return "login";
    }

    @GetMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/User/allTeamsRank";
        }
        return "redirect:/Steps/addSteps";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {

        return "redirect:/Product/Home";
    }

    @GetMapping("/allTeamsRank")
    public String allTeamsRank(Model model, Principal principal, HttpServletRequest request) {
        List<RankDTO> teamRankList = stepsService.getStepsSumByTeam();

        User user1 = userService.findByEmail(principal.getName());
        String user1Team = user1.getTeamName();
        for (RankDTO rankDTO : teamRankList) {
            if (rankDTO.getTeamName().equals(user1Team)) {
                rankDTO.setVisuable(true);
            }
        }

        model.addAttribute("steps", teamRankList);
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "admin_dashboard";
        }
        return "dashboard";
    }

    @GetMapping("/memberRankInTeam")
    public String memberRankInTeam(Model model, Principal principal) {

        User user1 = userService.findByEmail(principal.getName());

        String user1Team = user1.getTeamName();
        String user1Email = user1.getEmail();
        List<RankDTO> teamRankList = stepsService.getStepsSumByUserInTeam(user1Team);

        for (RankDTO rankDTO : teamRankList) {
            System.out.println(rankDTO.toString());
            if (rankDTO.getMemberEmail().equals(user1Email)) {
                rankDTO.setVisuable(true);
            }
        }

        model.addAttribute("users", teamRankList);
        return "team_dashboard";
    }


 //   @Scheduled(cron = "0 0/2 * * * ?")
    @Scheduled(cron = "0 0 0 * * ?")
    public void userEmailAlert() {
        LocalDate date = LocalDate.now();
        emailService.emailAlertToSubmitSteps(userService.getAllUsersWhoDoesNotSubmitSteps(date), date);
    }



    @GetMapping("/showUpdateProfileForm")
    public String showUpdateProfileForm(Model model, HttpServletRequest request) {
        model.addAttribute("user", new User());
//        if (request.isUserInRole("ROLE_ADMIN")) {
//            return "admin_dashboard";
//        }
        return "profile";

    }


    @PostMapping("/updateUser")
    public String updateUser(@Param("image") MultipartFile image,  @Valid User user, Principal principal, BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            return "update";
        }
        if (userService.isUserPresent(user.getEmail())) {
            model.addAttribute("exist", true);
            return "update";
        }
        User user1 = userService.findByEmail(principal.getName());
        model.addAttribute("user1", user1);
        if (user1 == null) {
            return "redirect:/User/logout";
        } else {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            user1.setProfilePictureUrl("/images/" + fileName);

            String rootDir = System.getProperty("user.dir");
            String uploadDir = rootDir + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "images";
//            String uploadDir = "C:\\Users\\zaher\\IntelliJ_EE_Projecte\\enterprise\\src\\main\\resources\\static\\images";
            System.out.println(uploadDir);
            FileUploader.saveFile(uploadDir, fileName, image);
            user1.setFirstName(user.getFirstName());
            user1.setLastName(user.getLastName());
            user1.setPassword(user.getPassword());
            user1.setStepsTarget(user.getStepsTarget());
            userService.creatUser(user1);
            model.addAttribute("success", true);

            return "redirect:/User/showProfileDetails";
        }
    }

    @GetMapping("/showProfileDetails")
    public String showProfileDetails(Model model) {

        model.addAttribute("user", new User());

        return "profileDetails";
    }

}
