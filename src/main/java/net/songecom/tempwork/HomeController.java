package net.songecom.tempwork;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.songecom.tempwork.dao.StudentDao;
import net.songecom.tempwork.model.StudentDto;


@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	
	@Autowired //전체로 다 쓸거라 위에 선언 (생성자, 필드, 의존관계 주입이 전부 됨), 생성자에넣는 작업(직접 우리가 안해도 됨)
				//this. <- 이지랄 안해도됨
	private StudentDao studentDao;
	
	//ModelAndView -> String으로 바꿀때
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws IOException {  //String 말고 ModelAndView로 받음 - 수정
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		
		//읽어오기
		List<StudentDto> listStudent = studentDao.read();
		model.addAttribute("listStudent", listStudent); //liststudent를 liststudent에 담게 쿼리
		model.addAttribute("serverTime", formattedDate); //시간담음
		return "home";
	}
	
	//리스트 (redirect로 리턴받기위해)
	@RequestMapping("list")
	public String list(Locale locale, 
					   Model model, 
					   @RequestParam(value="msg", required=false) String msg)  //insertok에서 받은값을 msg로 담음 
	         throws IOException {  //String 말고 ModelAndView로 받음 - 수정
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		
		//읽어오기
		List<StudentDto> listStudent = studentDao.read();
		if(msg != null) { //msg로 받은값 출력
			model.addAttribute("msg", msg);
		}
		model.addAttribute("listStudent", listStudent); //liststudent를 liststudent에 담게 쿼리
		model.addAttribute("serverTime", formattedDate); //시간담음
		return "home";
	}
	
	/*  ModelAndView일때
	@Autowired //전체로 다 쓸거라 위에 선언 (생성자, 필드, 의존관계 주입이 전부 됨), 생성자에넣는 작업(직접 우리가 안해도 됨)
	//this. <- 이지랄 안해도됨
private StudentDao studentDao;

@RequestMapping(value = "/", method = RequestMethod.GET)
public ModelAndView home(Locale locale, ModelAndView model) throws IOException {  //String 말고 ModelAndView로 받음 - 수정
logger.info("Welcome home! The client locale is {}.", locale);

Date date = new Date();
DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
String formattedDate = dateFormat.format(date);

//읽어오기
List<StudentDto> listStudent = studentDao.read();
model.addObject("listStudent", listStudent); //liststudent를 liststudent에 담게 쿼리
model.addObject("serverTime", formattedDate); //시간담음
model.setViewName("home");
return model;
}
*/
	
	
	//content 보기 (받은값 request파라미터창으로 보내서 확인)
	@RequestMapping("content")
	public String content(HttpServletRequest request, Model model) {
		System.out.println("content() 실행됨");
		int id = Integer.parseInt(request.getParameter("id")); //파라미터로 보내는거라 db값이랑 다름
		StudentDto dto = studentDao.findStudentById(id); //db값이랑 맞는지 체크
		model.addAttribute("dto", dto); //model에 담고 - 컨텐트로 보내기
		return "content";
	}
	
	//StudentDaoImpl 매핑
	@RequestMapping("insert")
	public String insert() {
		System.out.println("insert() 실행");
		return "insert";
	}
	
	/*1. 받는방법 (직접적어주기)
		   insertok(Httpservletrequest request, Model model){
		  	String name = request.getParameter("name");
		  	String email = request.getParameter("email");
	        ...
		   }
	 *  2. 받는방법 (받아서 어노테이션으로 변환)
	 *  @RequestMapping(value="insertok", method = RequestMethod.POST )
			public String insertok(
			@RequestParam("name") String name,
			@RequestParam("email") String email,
			@RequestParam("course") String course,
			Model model) {  //modelandview 아니고 model로 하면 이렇게 
		System.out.println("insertOk()실행");
	 */
	
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
	@RequestMapping(value="insertok", method = RequestMethod.POST )
	public String insertok(
			@RequestParam("name") String name,
			@RequestParam("email") String email,
			@RequestParam("course") String course,
			Model model) {  //modelandview 아니고 model로 하면 이렇게 
		System.out.println("insertOk()실행");
		
		StudentDto dto = new StudentDto();
		dto.setStu_name(name);
		dto.setStu_email(email);
		dto.setStu_course(course);
		
		int rs = studentDao.create(dto);
		if(rs > 0) {
			model.addAttribute("msg", "새로운 학생을 추가했습니다."); //addObject = 추가한단뜻
		}else {
			model.addAttribute("msg", "새로운 학생을 추가하는데 실패했습니다.");
		}
		
		return "redirect:list";
	}
	
	//업데이트
	@RequestMapping("update")
	public String update(HttpServletRequest request, Model model) {
		System.out.println("update() 실행됨");
		int id = Integer.parseInt(request.getParameter("id")); //파라미터로 보내는거라 db값이랑 다름
		StudentDto dto = studentDao.findStudentById(id); //db값이랑 맞는지 체크
		model.addAttribute("dto", dto); //model에 담고 - 컨텐트로 보내기
		return "update";
	}

	//업데이트 ok (insertok와 비슷하게 - 참고)
	@RequestMapping(value="updateok", method=RequestMethod.POST)
	public String updateok(
			@RequestParam("name") String name,
			@RequestParam("email") String email,
			@RequestParam("course") String course,
			@RequestParam("id") String id,
			Model model) {
			
			StudentDto dto = new StudentDto();
			dto.setStu_id(Integer.parseInt(id));
			dto.setStu_name(name);
			dto.setStu_email(email);
			dto.setStu_course(course);
			int rs = studentDao.update(dto);
			if(rs > 0) {
				model.addAttribute("msg", "수정했습니다."); //addObject = 추가한단뜻
			}else {
				model.addAttribute("msg", "수정에 실패했습니다.");
			}
		return "redirect:list";
	}
	
	//delete구문
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, Model model) {
		System.out.println("delete() 실행");
		int id = Integer.parseInt(request.getParameter("id"));
		int rs = studentDao.delete(id);
		if(rs > 0) {
			model.addAttribute("msg", "삭제했습니다."); //addObject = 추가한단뜻
		}else {
			model.addAttribute("msg", "삭제에 실패했습니다.");
		}
		return "redirect:list";
	}
	
	
}
