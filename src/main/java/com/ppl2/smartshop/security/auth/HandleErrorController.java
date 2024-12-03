package com.ppl2.smartshop.security.auth;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class HandleErrorController implements ErrorController {
  @RequestMapping("/error")
  public String handleError(HttpServletRequest request) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    System.out.println(status);
    if (status != null) {
      Integer statusCode = Integer.valueOf(status.toString());
      if (statusCode == HttpStatus.NOT_FOUND.value()) {
        return "error-page/404";
      } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
        return "error-page/500";
      } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
    	  return "error-page/403";
      } else if(statusCode==HttpStatus.BAD_REQUEST.value()){
    	  return "error-page/400";
      }
    }
    return "error";
  }
//  @Override
//  public String getErrorPath() {
//    return "/error";
//  }
}
