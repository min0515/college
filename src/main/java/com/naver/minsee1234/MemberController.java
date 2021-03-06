package com.naver.minsee1234;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naver.minsee1234.dao.MemberDao;
import com.naver.minsee1234.entities.Member;
import com.naver.minsee1234.entities.Tb_professor;
import com.naver.minsee1234.entities.Tb_student;

@Controller
public class MemberController {

	@Autowired
	private SqlSession sqlSession;

	@Autowired
	Member member;

	@Autowired
	Tb_student tb_student;

	@Autowired
	Tb_professor tb_professor;

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		return "member/member_insert";
	}

	@RequestMapping(value = "/memberinsertsave", method = RequestMethod.POST)
	public String memberinsert(Model model, @ModelAttribute Member member) {
		// getMapper는 인터페이스를 받아옴
		MemberDao dao = sqlSession.getMapper(MemberDao.class);
		// 사진
		if (member.getPhoto() == null) {
			member.setPhoto("/images/noimage.png");
		}
		// 비밀번호 암호화
		String encodepassword = hashPassword(member.getPassword());
		member.setPassword(encodepassword);

		dao.insertRow(member);
		return "member/member_insert";
	}

	@RequestMapping(value = "/studentLoginUp", method = RequestMethod.POST)
	public String loginUp(Model model, @ModelAttribute Tb_student tb_student, HttpSession session) {
		MemberDao dao = sqlSession.getMapper(MemberDao.class);
		Tb_student data = dao.selectOne(tb_student.getStudent_no());
		if (data == null) {
			return "login/login";
		} else {
			boolean passchk = BCrypt.checkpw(tb_student.getStudent_pw(), data.getStudent_pw());
			if (passchk) {
				session.setAttribute("sessionMember_id", data.getStudent_no());
				session.setAttribute("sessionStudent_name", data.getStudent_name());
				session.setAttribute("sessionDepartment_no", data.getDepartment_no());
				session.setAttribute("sessionAbsence_yn", data.getAbsence_yn());
				return "index";
			} else {
				return "login/login";
			}

		}

	}

	@RequestMapping(value = "/professorLoginUp", method = RequestMethod.POST)
	public String loginUp(@ModelAttribute Tb_professor tb_professor, HttpSession session) {
		MemberDao dao = sqlSession.getMapper(MemberDao.class);
		Tb_professor data = dao.professorSelectOne(tb_professor.getProfessor_no());
		if (data == null) {
			return "login/login";
		} else {
			boolean passchk = BCrypt.checkpw(tb_professor.getProfessor_pw(), data.getProfessor_pw());
			if (passchk) {
				session.setAttribute("sessionMember_id", data.getProfessor_no());
				session.setAttribute("sessionProfessor_name", data.getProfessor_name());
				session.setAttribute("sessionDepartment_no", data.getDepartment_no());
				return "index";
			} else {
				return "login/login";
			}

		}

	}

	@RequestMapping(value = "/shoppingLogin", method = RequestMethod.GET)
	public String shoppingLogin(HttpSession session) {
		return "login/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpSession session) {
		return "login/login2";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session, Locale locale) {
		session.invalidate();
		return "index";
	}

	private String hashPassword(String plainTextPassword) {
		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}

//비밀번호암호화
//	@RequestMapping(value = "/login", method = RequestMethod.GET)
//	public String login() {
//		MemberDao dao = sqlSession.getMapper(MemberDao.class);
//		ArrayList<Tb_student> pwList = dao.selectAllStudent();
//		for (Tb_student pwStudent : pwList) {
//			String encodepassword = hashPassword(pwStudent.getStudent_pw());
//			tb_student.setStudent_pw(encodepassword);
//			tb_student.setStudent_no(pwStudent.getStudent_no());
//			dao.updateCodeRowStudent(tb_student);
//		}
//
//		return "login/login";
//	}

//	@RequestMapping(value = "/login", method = RequestMethod.GET)
//	public String login() {
//		MemberDao dao = sqlSession.getMapper(MemberDao.class);
//		ArrayList<Tb_professor> pwList = dao.selectAllProfessor();
//		for (Tb_professor pwProfessor : pwList) {
//			String encodepassword = hashPassword(pwProfessor.getProfessor_pw());
//			tb_professor.setProfessor_no(pwProfessor.getProfessor_no());
//			tb_professor.setProfessor_pw(encodepassword);
//			dao.updateCodeRowProfessor(tb_professor);
//		}
//		return "login/login";
//	}

}
