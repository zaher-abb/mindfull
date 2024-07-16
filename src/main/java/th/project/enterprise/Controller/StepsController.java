package th.project.enterprise.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import th.project.enterprise.Entity.Steps;
import th.project.enterprise.Entity.User;
import th.project.enterprise.Service.StepsService;
import th.project.enterprise.Service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/Steps")
public class StepsController {

    @Autowired
    UserService userService;

    @Autowired
    StepsService stepsService;

    @GetMapping("/addSteps")
    public String viewAddStepsPage(Model model) {
        model.addAttribute("stp", new Steps());
        return "addSteps";
    }

    @GetMapping("/steps/today")
    public String getNumberOfStepsByDay(Principal principal, Model model){
        LocalDate today = LocalDate.now();
        User user1 = userService.findByEmail(principal.getName());
        int stepsSum = userService.getNumberOfStepsByDay(user1.getId(), today);
        model.addAttribute("steps",stepsSum);
        return "dashboard";
    }
    @GetMapping("/steps/week")
    public String getNumberOfStepsByWeek(Principal principal, Model model){
        LocalDate startDay = LocalDate.now();
        LocalDate endDay = LocalDate.now().plusDays(7);

        User user1 = userService.findByEmail(principal.getName());
        int stepsSum = userService.getNumberOfStepsByDifference(user1.getId(), startDay,endDay);

        model.addAttribute("steps",stepsSum);
        return "dashboard";
    }

    @PostMapping("/addSteps")
    public String addSteps(@Valid Steps stp , Principal principal) {

        User user1 = userService.findByEmail(principal.getName());
        if (user1 == null) {
            return "redirect:/User/logout";
        } else {

            String dateString = stp.getStringDate();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dateString, formatter);
            stp.setUser(user1);
            stp.setDate(localDate);
            stepsService.addNewSteps(stp);
            return "redirect:/User/allTeamsRank";
        }
    }
}
