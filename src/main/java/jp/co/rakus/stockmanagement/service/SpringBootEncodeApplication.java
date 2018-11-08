/*package jp.co.rakus.stockmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SpringBootEncodeApplication implements CommandLineRunner {

	@Autowired
    PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {

		SpringApplication.run(SpringBootEncodeApplication.class, args);
		
	}

	@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	public String hash(String pass) {
		
		String password = pass;
        String digest = passwordEncoder.encode(password);
        //System.out.println("ハッシュ値 = " + digest);

        
        if (passwordEncoder.matches(password, digest)) {
            System.out.println("一致したよ");
            return digest;
        }
        System.out.println("一致しなかったよ");
		
		return null;
	}
	
    @Override
    public void run(String... args) {

        String password = "#fe?3d31";
        String digest = passwordEncoder.encode(password);
        System.out.println("ハッシュ値 = " + digest);

        if (passwordEncoder.matches(password, digest)) {
            System.out.println("一致したよ");
            return;
        }
        System.out.println("一致しなかったよ");
    }
	
}
*/