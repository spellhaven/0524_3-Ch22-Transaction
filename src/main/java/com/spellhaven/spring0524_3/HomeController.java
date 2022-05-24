package com.spellhaven.spring0524_3;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	// 아까처럼 전역 변수로 만드시는 게 template이 아니라 dao네. 이래도 되는 모양이다.
	private TicketDao dao;
	
	@Autowired
	public void setDao(TicketDao dao) {
		this.dao = dao;
	}
		
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/buy_ticket")
	public String buy_ticket() {
		return "buy_ticket";
	}
	
	@RequestMapping(value = "/buy_ticket_card")
	public String buy_ticket_card(TicketDto ticketDto, Model model) { // 거래 끝내주는 놈. (중의적 표현 🤣) Request 객체 대신 Dto를 통째로 실어도 된다. (저번에 했대 난 기억 안 나는데;;)
		
		dao.buyTicket(ticketDto); // Dto를 바로 실어버려 ㅋ
		
		model.addAttribute("ticketInfo", ticketDto); // 이 라인은 무슨 말이냐? 이해못함, ㅋ (원래 15시 17분쯤이면 다 그런 거지.)
		
		return "buy_ticket_end";
	}
	
}


















