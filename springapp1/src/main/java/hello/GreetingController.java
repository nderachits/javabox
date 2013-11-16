package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping(value = "/greetingform", method = RequestMethod.GET)
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "greetingform";
    }

    @RequestMapping(value = "/greetingform", method = RequestMethod.POST)
    public String greetingSubmit(@ModelAttribute Greeting greeting, Model model ) {
        model.addAttribute("greeting", greeting);
        return "result";
    }

}