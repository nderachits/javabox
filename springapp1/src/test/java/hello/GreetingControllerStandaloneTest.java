package hello;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * User: nike
 * Date: 11/23/13
 */

public class GreetingControllerStandaloneTest {

    public static final int GREETING_ID = 123;
    public static final String GREETING_CONTENT = "Some content";
    private MockMvc mockMvc;
    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/");
        viewResolver.setSuffix(".no");

        mockMvc = MockMvcBuilders.standaloneSetup(new GreetingController())
                .setViewResolvers(viewResolver).build();
    }

    @Test
    public void testGreeting() throws Exception {
        mockMvc.perform(get("/greeting"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("name", "World"));

    }

    @Test
    public void testGreetingForm() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/greetingform"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("greeting"))
                .andExpect(view().name("greetingform"))
                .andReturn();
        Greeting greeting = ModelAndViewAssert.assertAndReturnModelAttributeOfType(
                        mvcResult.getModelAndView(), "greeting", Greeting.class);
        assertNotNull(greeting);

    }

    @Test
    public void testGreetingFormSubmit() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/greetingform")
                                    .param("id", ""+GREETING_ID)
                                    .param("content", GREETING_CONTENT))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andReturn();
        Greeting greetingResult = ModelAndViewAssert.assertAndReturnModelAttributeOfType(
                mvcResult.getModelAndView(), "greeting", Greeting.class);
        assertNotNull(greetingResult);
        assertEquals(GREETING_ID, greetingResult.getId());
        assertEquals(GREETING_CONTENT, greetingResult.getContent());

    }

    @Test
    public void validationErrorOnGreetingFormSubmit() throws Exception {

        mockMvc.perform(post("/greetingform")
                                    .param("id", ""+GREETING_ID)
                                    .param("content", ""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("greeting"))
                .andExpect(view().name("greetingform"));

    }

    @Test
    public void shouldPassValidation() throws Exception {
        mockMvc.perform(post("/person").param("age", "21"))
                .andExpect(status().isOk())
                .andExpect(flash().attributeCount(0))
                .andExpect(view().name("personresults"));

    }

    @Test
    public void shouldRejectPerson() throws Exception {
        mockMvc.perform(post("/person").param("age", "16"))
                .andExpect(status().isMovedTemporarily())
                .andExpect(flash().attribute("error", "must be greater than or equal to 18"))
                .andExpect(redirectedUrl("/person"));

    }
}
