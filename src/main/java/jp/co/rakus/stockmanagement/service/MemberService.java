package jp.co.rakus.stockmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.co.rakus.stockmanagement.domain.Member;
import jp.co.rakus.stockmanagement.repository.MemberRepository;

/**
 * メンバー関連サービスクラス.
 * @author igamasayuki
 *
 */
@Service
public class MemberService {

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
    PasswordEncoder passwordEncoder;
//	public List<Member> findAll(){
//		return memberRepository.findAll();
//	}
//	
//	public Member findOne(Integer id) {
//		return memberRepository.findOne(id);
//	}
	
	public Member findOneByMailAddressAndPassword(String mailAddress, String password){
		return memberRepository.findByMailAddressAndPassword(mailAddress, password);
	}
	
	public Member findOneByMailAddress(String mailAddress){
		return memberRepository.findByMailAddress(mailAddress);
	}
	
	public Member save(Member member){
		return memberRepository.save(member);
	}
	
	@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	public String hash(String pass) {
		
		//String password = pass;
        String digest = passwordEncoder.encode(pass);
        //System.out.println("ハッシュ値 = " + digest);

        
        if (passwordEncoder.matches(pass, digest)) {
            System.out.println("一致したよ");
            return digest;
        }
        System.out.println("一致しなかったよ");
		
		return null;
	}
	
	public String passwordIsMatch(String hashedPass, String password) {
		
		if(passwordEncoder.matches(password, hashedPass)) {
			return "パスワード一致";
		}
		return null;
	}
	
//	public Member update(Member member){
//		return memberRepository.save(member);
//	}
//	
//	public void delete(Integer id){
//		memberRepository.delete(id);
//	}
}
