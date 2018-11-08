package jp.co.rakus.stockmanagement.web;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.rakus.stockmanagement.domain.Member;
import jp.co.rakus.stockmanagement.service.MemberService;

/**
 * メンバー関連処理を行うコントローラー.
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/member")
@Transactional
public class MemberController {

	@Autowired
	private MemberService memberService;

	
	/**
	 * フォームを初期化します.
	 * @return フォーム
	 */
	@ModelAttribute
	public MemberForm setUpForm() {
		return new MemberForm();
	}
	
	/*@ModelAttribute
	public LoginForm setUpForm1() {
		return new LoginForm();
	}*/
	
	/**
	 * メンバー情報登録画面を表示します.
	 * @return メンバー情報登録画面
	 */
	@RequestMapping(value = "form")
	public String form() {
		return "/member/form";
	}
	
	/**
	 * メンバー情報を登録します.
	 * @param form フォーム
	 * @param result リザルト
	 * @param model モデル
	 * @return ログイン画面
	 */
	@RequestMapping(value = "create")
	public String create(@Validated MemberForm form,BindingResult result, 
			Model model) {
		
		//System.out.println("a");
		Member member = new Member();
		BeanUtils.copyProperties(form, member);
		
		/*確認用パスワードと合致しなければメンバー情報登録画面に返し下記のエラーメッセージを返す*/
		if(!(member.getPassword().equals(member.getRePassword()))) {
			result.rejectValue("password", null , "パスワードが合いません");
			//return form();
		}
		
		/*メールアドレスがすでに登録されていればメンバー情報登録画面に返し下記のエラーメッセージを表示する*/
		if(memberService.findOneByMailAddress(member.getMailAddress())!=null) {
			result.rejectValue("mailAddress", null , "すでに登録されたメールアドレスです");
			//return form();
		}
		if(result.hasErrors()) {
			return form();
		}
		/*ハッシュ化されたパスワードを生成*/
		
		String digest = memberService.hash(member.getPassword());
		if(digest!=null) {
		System.out.println(digest);
		member.setPassword(digest);
		memberService.save(member);
			return "redirect:/";
		}else {
			return form();
		}
			
		
		
		
	}
	
}
