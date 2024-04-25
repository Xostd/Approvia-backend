package com.Igris.ApplicationGestionAchat;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Igris.ApplicationGestionAchat.Entity.Article;
import com.Igris.ApplicationGestionAchat.Entity.DemandeAchat;
import com.Igris.ApplicationGestionAchat.Entity.Etat;
import com.Igris.ApplicationGestionAchat.Entity.LigneDemandeAchat;
import com.Igris.ApplicationGestionAchat.Entity.Region;
import com.Igris.ApplicationGestionAchat.Entity.Role;
import com.Igris.ApplicationGestionAchat.Entity.Service;
import com.Igris.ApplicationGestionAchat.Entity.Token;
import com.Igris.ApplicationGestionAchat.Entity.User;
import com.Igris.ApplicationGestionAchat.Service.ArticleService;
import com.Igris.ApplicationGestionAchat.Service.DemandeAchatService;
import com.Igris.ApplicationGestionAchat.Service.LigneDemandeAchatService;
import com.Igris.ApplicationGestionAchat.Service.TokenService;
import com.Igris.ApplicationGestionAchat.Service.UserService;

@SpringBootApplication
@ComponentScan(basePackages = {"com.Igris.ApplicationGestionAchat"})
public class ApplicationGestionAchatApplication {
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(ApplicationGestionAchatApplication.class, args);
		UserService userServ = ctx.getBean(UserService.class);
		PasswordEncoder passwordEncoder = ctx.getBean(PasswordEncoder.class);
//		User user = new User("Ben Foulen", "Foulen", passwordEncoder.encode("password"), Service.Informatique,
//				Region.Sfax, Role.ACHETEUR, userServ.getSequenceNextVal());
		User user2 = new User("Salah", "Mohamed", passwordEncoder.encode("passwordTest"), Service.Finance, Region.Sfax,
				Role.DEMANDEUR, userServ.getSequenceNextVal());
//		user.setRole(Role.ADMIN);
		//userServ.saveUser(user);
		userServ.saveUser(user2);
		System.out.println(user2);
		TokenService tokenService = ctx.getBean(TokenService.class);
		Token token = Token.builder().user(user2).loggedOut(false).token("testToken").build();
		System.out.println(token);
		tokenService.saveToken(token);
		Article article = Article.builder()
				.libelle("art-1")
				.unite("Kg").build();
		DemandeAchat demande = DemandeAchat.builder()
				.reference("DA-2024-00000001")
				.dateCreation(LocalDate.now())
				.user(user2)
				.etat(Etat.CREEE)
				.build();
		LigneDemandeAchat ligne = LigneDemandeAchat.builder()
				.article(article)
				.demandeAchat(demande)
				.quantite(10)
				.build();
		demande.setLignes(Set.of(ligne));
		ArticleService articleService = ctx.getBean(ArticleService.class);
		DemandeAchatService demandeService = ctx.getBean(DemandeAchatService.class);
		LigneDemandeAchatService ligneService = ctx.getBean(LigneDemandeAchatService.class);
		articleService.saveArtice(article);
		demandeService.saveDemandeAchat(demande);
		ligneService.saveLigneDemandeAchat(ligne);

	}
}
