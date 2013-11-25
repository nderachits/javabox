package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
    public String greetingSubmit(@Valid @ModelAttribute Greeting greeting, BindingResult bindingResult, Model model ) {
        model.addAttribute("greeting", greeting);
        if(bindingResult.hasErrors()) {
            return "greetingform";
        }
        return "result";
    }

    @RequestMapping(value="/person", method=RequestMethod.GET)
    public String showForm(Person person) {
        return "personform";
    }

    @RequestMapping(value="/person", method=RequestMethod.POST)
    public String enterAge(@Valid Person person, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", bindingResult.getFieldError().getDefaultMessage());
            return "redirect:/person";
        }
        return "personresults";
    }

}