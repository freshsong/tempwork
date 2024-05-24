package net.songecom.tempwork;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.songecom.tempwork.dao.StudentDao;
import net.songecom.tempwork.model.StudentDto;


@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	
	@Autowired //전체로 다 쓸거라 위에 선언 (생성자, 필드, 의존관계 주입이 전부 됨), 생성자에넣는 작업(직접 우리가 안해도 됨)
				//this. <- 이지랄 안해도됨
	private StudentDao studentDao;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	//StudentDaoImpl 매핑
	@RequestMapping("insert")
	public String insert() {
		System.out.println("insert() 실행");
		return "insert";
	}
	
	/*jdbc mvc (request로 받은걸 mvc model로 보내는 것)
	@RequestMapping("insertok")
	public String insertok(HttpServletRequest request, Model model) {
		System.out.println("insertOk()실행");
		StudentDto dto = new StudentDto();
		
		return "home";
	}
	*/
	
	//위에꺼랑 같음 (RequestParam의 매개변수로 받아서 처리 가능) 
	//즉 String name = reuqest.getParameter("name"); 이런작업 없이 밑에처럼 할 수 있음
	@RequestMapping("insertok")
	public ModelAndView insertok(
			@RequestParam("name") String name,
			@RequestParam("email") String email,
			@RequestParam("course") String course,
			ModelAndView mv) {
		System.out.println("insertOk()실행");
		
		StudentDto dto = new StudentDto();
		dto.setStu_name(name);
		dto.setStu_email(email);
		dto.setStu_course(course);
		
		int rs = studentDao.create(dto);
		if(rs > 0) {
			mv.addObject("msg", "새로운 학생을 추가했습니다."); //addObject = 추가한단뜻
		}else {
			mv.addObject("msg", "새로운 학생을 추가하는데 실패했습니다.");
		}
		mv.setViewName("home");
		
		return mv;
	}
}
