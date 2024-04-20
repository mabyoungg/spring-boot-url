package org.example.springbooturl.global.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.springbooturl.domain.member.member.service.AuthTokenService;
import org.example.springbooturl.global.rq.Rq;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final Rq rq;
    private final AuthTokenService authTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String accessToken = authTokenService.genAccessToken(rq.getMember());
        String refreshToken = rq.getMember().getRefreshToken();

        String redirectUrlAfterSocialLogin = rq.getCookieValue("redirectUrlAfterSocialLogin", "");

        if (rq.isFrontUrl(redirectUrlAfterSocialLogin)) {
            rq.destroySecurityContextSession();
            rq.setCrossDomainCookie("accessToken", accessToken);
            rq.setCrossDomainCookie("refreshToken", refreshToken);
            rq.removeCookie("redirectUrlAfterSocialLogin");

            response.sendRedirect(redirectUrlAfterSocialLogin);
            return;
        } else {
            rq.destroySecurityContextSession();
            rq.setCrossDomainCookie("accessToken", accessToken);
            rq.setCrossDomainCookie("refreshToken", refreshToken);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
