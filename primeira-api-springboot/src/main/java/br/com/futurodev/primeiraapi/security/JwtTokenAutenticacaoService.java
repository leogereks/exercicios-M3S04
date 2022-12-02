package br.com.futurodev.primeiraapi.security;

import br.com.futurodev.primeiraapi.context.ApplicatonContextLoad;
import br.com.futurodev.primeiraapi.model.Usuario;
import br.com.futurodev.primeiraapi.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Service
@Component
public class JwtTokenAutenticacaoService {
    private static final long EXPERAION_TIME = 24 * 60 * 60 * 2;

    private static final String SECRET = "SenhaExtremamenteSecretaForte";

    private static final String TOKEN_PREFIX = "Bearer";

    private static final String HEADER_STRING = "Authorization";

    public void addAuthentication(HttpServletResponse response, String username) throws IOException{

        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPERAION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();


        String token = TOKEN_PREFIX + "  " + JWT;

        response.addHeader(HEADER_STRING, token);

        response.getWriter().write("{\"Authorization\": \""+token+"\"}");

    }
    public Authentication getAuthentication(HttpServletRequest request){

        String token = request.getHeader(HEADER_STRING);

        if(token != null){

            String user = Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody().getSubject();

            if(user != null){
                 Usuario usuario = ApplicatonContextLoad.getApplicationContext()
                         .getBean(UsuarioRepository.class).findUserByLogin(user);

                 System.out.println("Login do usuario: "+usuario.getLogin());

                 if(usuario != null){
                     return new UsernamePasswordAuthenticationToken(
                             usuario.getLogin(),
                             usuario.getSenha(),
                             usuario.getAuthorities());
                 }
            }
        }
        return null;
    }

}
