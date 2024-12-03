package com.ppl2.smartshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {
	@GetMapping("/policy")
	private String policyPage() {
		return "shop-privacy-policy";
	}

	@GetMapping("/contact")
	private String contactForm() {
		return "shop-contacts";
	}

	@GetMapping("/faq" )
	private String contact() {
		return "shop-faq";
	}
}
