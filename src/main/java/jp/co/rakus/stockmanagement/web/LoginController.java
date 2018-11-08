package jp.co.rakus.stockmanagement.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.rakus.stockmanagement.domain.Member;
import jp.co.rakus.stockmanagement.service.MemberService;

/**
 * ログイン関連処理を行うコントローラー.
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private HttpSession session;
	/**
	 * フォームを初期化します.
	 * @return フォーム
	 */
	@ModelAttribute
	public LoginForm setUpForm() {
		return new LoginForm();
	}

	/**
	 * ログイン画面を表示します.
	 * @return ログイン画面
	 */
	@RequestMapping
	public String index() {
		return "loginForm";
	}
	
	/**
	 * ログイン処理を行います.
	 * @param form　フォーム
	 * @param result　リザルト
	 * @param model　モデル
	 * @return　ログイン成功時：書籍リスト画面
	 */
	@RequestMapping(value = "/login")
	public String login(@Validated LoginForm form,
			BindingResult result, Model model) {
		
		if (result.hasErrors()){
			return index();
		}
		
		String mailAddress = form.getMailAddress();
		String password = form.getPassword();
		
		//メールアドレスから個人を特定し、ハッシュ化されたパスワードを取得
		Member member = memberService.findOneByMailAddress(mailAddress);
		
		//ハッシュ化されたパスワードと入力されたパスワードが合わなければログイン画面に戻す
		if(memberService.passwordIsMatch(member.getPassword(),password)==null ) {
			ObjectError error = new ObjectError("loginerror", "パスワードが違います。");
            result.addError(error);
			System.out.println("失敗");
			return index();
		}
		
		member = memberService.findOneByMailAddressAndPassword(mailAddress, member.getPassword());
		
		
		if (member == null) {
			ObjectError error = new ObjectError("loginerror", "メールアドレスまたはパスワードが違います。");
            result.addError(error);
			return index();
		}
		
		
		session.setAttribute("member", member);
		return "redirect:/book/list";
	}
}
