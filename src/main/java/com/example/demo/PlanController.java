package com.example.demo;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
public class PlanController {

	@Autowired
	HttpSession session;

	@Autowired
	PlanDao planDao;

	@Autowired
	UserDao userDao;
	
	@Autowired
	ScheduleDao scheduleDao;

	// トップ画面（プラン一覧）
	@GetMapping("/")
	public ModelAndView top(
			ModelAndView mv) {
		
		// セッションからログイン情報を取得
		String email = (String) session.getAttribute("email");
		String password = (String) session.getAttribute("password");
		
		// セッションにログイン情報が無い場合はログイン画面に遷移
		if (email == null) {
			mv.setViewName("login");

		} else {
			// ログイン情報からユーザーＩＤを取得
			String userId = userDao.findUserIdByLoginInfo(email, password);
			// ユーザーIDからプランリスト全件を取得
			List<Plan> planList = planDao.findByCreator(userId);
			
			mv.addObject("email", email);
			mv.addObject("planList", planList);
			mv.addObject("creator", userId);
			mv.setViewName("plan_top");
		}

		return mv;
	}

	// ログインユーザーの作成したプラン一覧を表示
	@RequestMapping("/plan")
	public ModelAndView planTop(ModelAndView mv) {
		
		// セッションスコープからログイン情報を取得
		String email = (String) session.getAttribute("email");
		String password = (String) session.getAttribute("password");
		
		// ログイン情報からユーザーIDを取得
		String userId = userDao.findUserIdByLoginInfo(email, password);
		// ユーザーIDからプランリスト全件を取得
		List<Plan> planList = planDao.findByCreator(userId);
		
		//ログイン情報をセッションスコープに格納
		session.setAttribute("email", email);
		session.setAttribute("password", password);
		
		mv.addObject("planList", planList);
		mv.addObject("creator", userId);
		mv.setViewName("plan_top");
		return mv;
	}
	
	// プラン追加ページへの遷移
	@RequestMapping("/plan/add")
	public ModelAndView planAdd(
			ModelAndView mv,
			@RequestParam("creator") String creator) {

		mv.addObject("creator", creator);
		mv.setViewName("plan_add");

		return mv;
	}

	// プラン登録
	@RequestMapping("/plan/add/complete")
	public ModelAndView planAddComplete(
			@RequestParam("planTitle") String planTitle,
			@RequestParam("planMemo") String planMemo,
			@RequestParam("planStart") String planStart,
			@RequestParam("planEnd") String planEnd,
			@RequestParam("creator") String creator,
			ModelAndView mv) {
		
		// ランダムにUUIDを生成し、planIdに代入
		UUID uuid = UUID.randomUUID();
		String planId = uuid.toString();

		planDao.insert(planId, planTitle, planMemo, planStart, planEnd, creator);
		List<Plan> planList = planDao.findByCreator(creator);

		mv.addObject("planList", planList);
		mv.setViewName("plan_top");
		return mv;
	}
	
	// プラン編集画面への遷移
	@RequestMapping("/plan/edit")
	public ModelAndView planEdit(
			ModelAndView mv,
			@RequestParam("planId") String planId,
			@RequestParam("creator") String creator
			) {
		
		// プランIDからプランリストを取得
		List<Plan> planList = planDao.findByPlanId(planId);
		
		mv.addObject("planList", planList);
		mv.addObject("planId", planId);
		mv.addObject("creator", creator);
		mv.setViewName("plan_edit");

		return mv;
	}
	
	// プラン編集
	@RequestMapping("/plan/edit/complete")
	public ModelAndView planEditComplete(
			@RequestParam("planId") String planId,
			@RequestParam("planTitle") String planTitle,
			@RequestParam("planMemo") String planMemo,
			@RequestParam("planStart") String planStart,
			@RequestParam("planEnd") String planEnd,
			@RequestParam("creator") String creator,
			ModelAndView mv) {
		
		planDao.update(planId, planTitle, planMemo, planStart, planEnd, creator);
		
		List<Plan> planList = planDao.findByCreator(creator);

		mv.addObject("planList", planList);
		mv.setViewName("plan_top");

		return mv;
	}
	
	// プラン削除画面への遷移
	@RequestMapping("/plan/delete")
	public ModelAndView planDelete(
			ModelAndView mv,
			@RequestParam("planId") String planId,
			@RequestParam("creator") String creator) {

		mv.addObject("planId", planId);
		mv.addObject("creator", creator);
		mv.setViewName("plan_delete");

		return mv;
	}
	
	// プラン削除
	@RequestMapping("/plan/delete/complete")
	public ModelAndView planDeleteComplete(
			@RequestParam("planId") String planId,
			@RequestParam("creator") String creator,
			ModelAndView mv) {
		
		// プランに紐づくスケジュールの削除
		scheduleDao.deleteByPlanId(planId);
		
		// プランの削除
		planDao.delete(planId);
		
		List<Plan> planList = planDao.findByCreator(creator);

		mv.addObject("planList", planList);
		mv.setViewName("plan_top");

		return mv;
	}
}
