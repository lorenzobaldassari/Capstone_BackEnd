package LorenzoBaldassari.Capstone.Security;
import LorenzoBaldassari.Capstone.Entities.Pagina;
import LorenzoBaldassari.Capstone.Entities.Utente;
import LorenzoBaldassari.Capstone.Exceptions.UnauthorizedException;
import LorenzoBaldassari.Capstone.Repositories.UtenteRepository;
import LorenzoBaldassari.Capstone.Servicies.PaginaService;
import LorenzoBaldassari.Capstone.Servicies.UtenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class JWTAAutfilther extends OncePerRequestFilter {
    @Autowired
    private JWTTtools jwtTools;
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private PaginaService paginaService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException , IOException {
            String checkRequestToken=request.getHeader("Authorization");
        if(checkRequestToken==null){
            throw  new UnauthorizedException("token non presente");
        }else{
            String accessToken= checkRequestToken.substring(7);
            jwtTools.verifyToken(accessToken);


            String id = jwtTools.extractIdFromToken(accessToken);
            Optional<Utente> utente= utenteRepository.findById(UUID.fromString(id));
            if (!utente.isEmpty()){
                Utente utente1=utenteService.findByUUID(UUID.fromString(id));
            Authentication authentication= new UsernamePasswordAuthenticationToken(utente1,null,utente1.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
              Pagina pagina= paginaService.findByUUID((UUID.fromString(id)));
                Authentication authentication= new UsernamePasswordAuthenticationToken(pagina,null,null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }



            filterChain.doFilter(request,response);

        }
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }

}
