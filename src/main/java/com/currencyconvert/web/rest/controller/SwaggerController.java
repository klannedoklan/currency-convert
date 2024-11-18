package com.currencyconvert.web.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * This is to access the main swagger page by using "/swagger" without having
 * to know the html page name (/swagger-ui/index.html).
 */
@Controller
public class SwaggerController {

  @RequestMapping("/swagger")
  public ModelAndView home() {
    return new ModelAndView("redirect:/swagger-ui/index.html");
  }

}
