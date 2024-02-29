package com.example.demo;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
public class ScheduleController {

	@Autowired
	HttpSession session;

	@Autowired
	ScheduleDao scheduleDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	PlanDao planDao;
	
	// スケジュール画面へ遷移
	@RequestMapping("/schedule")
	public ModelAndView scheduleTop(
			ModelAndView mv, 
			@RequestParam("planId") String planId
			) {
		
		String email = (String) session.getAttribute("email");
		
		List<Schedule> scheduleList = scheduleDao.findByPlanId(planId);
		List<Plan> planList = planDao.findByPlanId(planId);
		
		// もしセッションにメールアドレスが存在しない場合、ログイン画面へ
		if (email == null) {
			
			mv.setViewName("login");
		
		} else {
			mv.addObject("planId", planId);
			mv.addObject("planList", planList);
			mv.addObject("scheduleList", scheduleList);
			mv.setViewName("schedule_top");
		}

		return mv;
	}

	// スケジュール追加画面へ遷移
	@RequestMapping("/schedule/add")
	public ModelAndView scheduleAdd(ModelAndView mv, @RequestParam("planId") String planId) {

		mv.addObject("planId", planId);
		mv.setViewName("schedule_add");

		return mv;
	}
	
	// スケジュール追加
	@RequestMapping("/schedule/add/complete")
	public ModelAndView scheduleAddComplete(
			ModelAndView mv,
			@RequestParam("planId") String planId,
			@RequestParam("scheduleTitle") String scheduleTitle,
			@RequestParam("scheduleStart") String scheduleStart,
			@RequestParam("scheduleEnd") String scheduleEnd,
			@RequestParam("scheduleMemo") String scheduleMemo,
			@RequestParam("cost") String cost) {

		// ランダムでUUIDを生成して、scheduleIdとして設定
		UUID uuid = UUID.randomUUID();
		String scheduleId = uuid.toString();

		scheduleDao.insert(scheduleId, scheduleTitle, scheduleStart, scheduleEnd, scheduleMemo, cost, planId);
		List<Schedule> scheduleList = scheduleDao.findByPlanId(planId);
		List<Plan> planList = planDao.findByPlanId(planId);

		mv.addObject("planId", planId);
		mv.addObject("planList", planList);
		mv.addObject("scheduleList", scheduleList);
		mv.setViewName("schedule_top");

		return mv;
	}
	
	// スケジュール編集画面へ遷移
	@RequestMapping("/schedule/edit")
	public ModelAndView scheduleEdit(
			ModelAndView mv,
			@RequestParam("scheduleId") String scheduleId) {
		
		List<Schedule> scheduleList = scheduleDao.findByScheduleId(scheduleId);
		
		mv.addObject("scheduleList", scheduleList);
		mv.addObject("scheduleId", scheduleId);
		mv.setViewName("schedule_edit");

		return mv;
	}

	// スケジュール編集
	@RequestMapping("/schedule/edit/complete")
	public ModelAndView scheduleEditComplete(
			ModelAndView mv,
			@RequestParam("scheduleId") String scheduleId,
			@RequestParam("planId") String planId,
			@RequestParam("scheduleTitle") String scheduleTitle,
			@RequestParam("scheduleStart") String scheduleStart,
			@RequestParam("scheduleEnd") String scheduleEnd,
			@RequestParam("scheduleMemo") String scheduleMemo,
			@RequestParam("cost") String cost) {

		scheduleDao.update(scheduleId, scheduleTitle, scheduleStart, scheduleEnd, scheduleMemo, cost);
		List<Schedule> scheduleList = scheduleDao.findByPlanId(planId);
		List<Plan> planList = planDao.findByPlanId(planId);
		
		mv.addObject("planList", planList);
		mv.addObject("scheduleList", scheduleList);
		mv.setViewName("schedule_top");

		return mv;
	}

	// スケジュール削除画面へ遷移
	@RequestMapping("/schedule/delete")
	public ModelAndView scheduleDelete(
			ModelAndView mv,
			@RequestParam("scheduleId") String scheduleId,
			@RequestParam("planId") String planId) {
		
		mv.addObject("scheduleId", scheduleId);
		mv.addObject("planId", planId);
		mv.setViewName("schedule_delete");

		return mv;
	}

	// スケジュール削除
	@RequestMapping("/schedule/delete/complete")
	public ModelAndView scheduleDeleteComplete(
			ModelAndView mv,
			@RequestParam("scheduleId") String scheduleId,
			@RequestParam("planId") String planId) {

		scheduleDao.delete(scheduleId);
		List<Schedule> scheduleList = scheduleDao.findByPlanId(planId);
		List<Plan> planList = planDao.findByPlanId(planId);
		
		mv.addObject("planList", planList);
		mv.addObject("scheduleList", scheduleList);
		mv.setViewName("schedule_top");

		return mv;
	}
}
