package com.example.demo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	@Autowired
	HttpSession session;

	@Autowired
	UserDao userDao;
	
	@Autowired
	PlanDao planDao;
	
	@Autowired
	ScheduleDao scheduleDao;
	
	// マイページへの遷移
	@RequestMapping("/mypage")
	public ModelAndView mypage(
			ModelAndView mv) {

		String email = (String) session.getAttribute("email");

		if (email == null) {
			mv.setViewName("login");
			return mv;
		}

		List<User> userList = userDao.findByEmail(email);

		mv.addObject("userList", userList);
		mv.setViewName("mypage");
		return mv;
	}

	// アカウント情報編集画面へ遷移
	@RequestMapping("/mypage/edit")
	public ModelAndView mypageEdit(
			@RequestParam("userId") String userId,
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("accountName") String accountName,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("admissionDate") String admissionDate,
			ModelAndView mv) {

		mv.addObject("userId", userId);
		mv.addObject("firstName", firstName);
		mv.addObject("lastName", lastName);
		mv.addObject("accountName", accountName);
		mv.addObject("email", email);
		mv.addObject("password", password);
		mv.addObject("admissionDate", admissionDate);

		mv.setViewName("mypage_edit");

		return mv;
	}

	// アカウント情報の編集
	@RequestMapping("/mypage/edit/complete")
	public ModelAndView mypageEditComplete(
			@RequestParam("userId") String userId,
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("accountName") String accountName,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("admissionDate") String admissionDate,
			ModelAndView mv) {

		userDao.update(userId, firstName, lastName, accountName, email, password, admissionDate);
		List<User> userList = userDao.findByEmail(email);

		mv.addObject("userList", userList);
		mv.setViewName("mypage");
		return mv;
	}

	// アカウント削除画面へ遷移
	@RequestMapping("/mypage/delete")
	public ModelAndView delete(
			@RequestParam("userId") String userId,
			ModelAndView mv) {
		
		mv.addObject("userId", userId);
		mv.setViewName("mypage_delete");
		return mv;
	}

	//	アカウント削除
	@PostMapping("/mypage/delete/complete")
	public ModelAndView deleteComplete(
			@RequestParam("userId") String userId,
			ModelAndView mv) {
		
		List<String> planIdList = planDao.findPlanIdByCreator(userId);
		
		// アカウントに紐づくスケジュール削除
		for(int i = 0; i < planIdList.size(); i++) {
			scheduleDao.deleteByPlanId(planIdList.get(i));
		}
		
		// アカウントに紐づくプランの削除
		planDao.deleteByCreator(userId);
		
		// アカウント削除
		userDao.delete(userId);
		mv.setViewName("login");
		return mv;
	}

	// ログイン画面へ遷移
	@RequestMapping("/login")
	public ModelAndView login(ModelAndView mv) {
		mv.setViewName("login");
		return mv;
	}

	// ログイン
	@PostMapping("/login/complete")
	public ModelAndView loginComplete(
			ModelAndView mv,
			@RequestParam("email") String email,
			@RequestParam("password") String password
			) {
		
		List<User> userList = userDao.findByLogin(email, password);
		String userId = userDao.findUserIdByLoginInfo(email, password);
		
		// もし取得したログイン情報が存在しない場合
		if(userList == null || userList.isEmpty()) {
			
			String loginErrorMessage = "メールアドレス、またはパスワードが一致しません。";
			
			mv.addObject("loginErrorMessage", loginErrorMessage);
			mv.setViewName("login");
			
		}else {
			
			List<Plan> planList = planDao.findByCreator(userId);
			
			// ログイン情報をセッションスコープに格納
			session.setAttribute("email", email);
			session.setAttribute("password", password);
			
			// もしプランが存在していれば表示
			if (planList != null) {
			    mv.addObject("planList", planList);
			}
			
			mv.addObject("creator", userId);
			mv.setViewName("plan_top");
		}
		return mv;
	}

	// 新規登録画面へ遷移
	@RequestMapping("/signup")
	public ModelAndView signup(ModelAndView mv) {
		mv.setViewName("signup");
		return mv;
	}

	// 新規登録
	@PostMapping("/signup/complete")
	public ModelAndView signupComplete(
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("accountName") String accountName,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			ModelAndView mv) {
		
		int errorCount = 0;
		
		List<User> isEmail = userDao.findByEmail(email);
		List<User> isAccountName = userDao.findByAccountName(accountName);
		
		// メールアドレスがすでに存在している場合
		if(isEmail.size() > 0) {
			
			String emailExistsErrorMessage = "そのメールアドレスはすでに使われています。";
			
			mv.addObject("userExistsErrorMessage", emailExistsErrorMessage);
			mv.setViewName("signup");
			
			errorCount++;
		
		// アカウント名がすでに存在している場合
		}else if(isAccountName.size() > 0) {
			
			String accountNameExistsErrorMessage = "そのアカウント名はすでに使われています。";
			
			mv.addObject("accountNameExistsErrorMessage", accountNameExistsErrorMessage);
			mv.setViewName("signup");
			
			errorCount++;
		}
		
		// エラーカウントが0であれば
		if(errorCount == 0) {
			
			// ランダムで生成したUUIDをuserIdとして設定
			UUID uuid = UUID.randomUUID();
			String userId = uuid.toString();
			
			// 今の時間を登録日時として設定
			LocalDateTime now = LocalDateTime.now();
			String admissionDate = now.toString();

			userDao.insert(userId, firstName, lastName, accountName, email, password, admissionDate);

			// ユーザーIDからプランリスト全件を取得
			List<Plan> planList = planDao.findByCreator(userId);

			//ログイン情報をセッションスコープに格納
			session.setAttribute("email", email);
			session.setAttribute("password", password);

			mv.addObject("email", email);
			mv.addObject("password", password);
			mv.addObject("creator", userId);
			mv.addObject("planList", planList);
			mv.setViewName("plan_top");
		}

		return mv;
	}
}
