package ru.kpfu.itis.bagautdinov.services.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.kpfu.itis.bagautdinov.dto.SignUpDto;
import ru.kpfu.itis.bagautdinov.models.User;
import ru.kpfu.itis.bagautdinov.repositories.UserRepository;
import ru.kpfu.itis.bagautdinov.security.details.UserSecurity;
import ru.kpfu.itis.bagautdinov.services.AuthorizationService;
import ru.kpfu.itis.bagautdinov.services.OAuthService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

@RequiredArgsConstructor
@Service
public class OAuthServiceImpl implements OAuthService {

    private final AuthorizationService authorizationService;
    private final UserRepository userRepository;

    @Override
    public void login(String code) {
        try {
            User user = getUser(code);


            authorizationService.oauth2Registration(SignUpDto.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .username(user.getUsername())
                    .build());
            ResponseEntity.status(HttpStatus.ACCEPTED).build();
            user = userRepository.findByUsername(user.getUsername()).orElseThrow();

            Authentication authentication = new UsernamePasswordAuthenticationToken(new UserSecurity(user), null,
                    AuthorityUtils.createAuthorityList("USER"));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private User getUser(String code) throws IOException {
        URL url = null;
        try {
            url = new URL("https://oauth.vk.com/access_token?client_id=8134165&client_secret=7E8vIe3lbeOZnhQYDcES&redirect_uri=http://localhost/oauth2/callback&code=" + code);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection httpConn = null;
        try {
            httpConn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            httpConn.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        httpConn.setRequestProperty("authority", "oauth.vk.com");
        httpConn.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpConn.setRequestProperty("accept-language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
        httpConn.setRequestProperty("cache-control", "max-age=0");
        httpConn.setRequestProperty("cookie", "remixlang=0; remixstlid=61059536513_m7u2UKO9Cerfft5HFsoQWzTFOrvh8g4zixdVpw8ABGw; remixstid=1684497581_b9oOiZtmtTcUata4H8Ow0xMgx1KPVwRXJ3b63RyMhI4; remixbdr=1; remixua=41%7C-1%7C194%7C3245631577; remixQUIC=1; remixflash=0.0.0; remixscreen_width=1536; remixscreen_height=864; remixscreen_dpr=1.25; remixscreen_depth=24; remixscreen_orient=1; remixscreen_winzoom=1; remixgp=6ef767420bd4a6bc2df0c950e60512aa; remixdt=0; remixdark_color_scheme=1; remixcolor_scheme_mode=auto; tmr_lvid=6151282de82762c874570f7a54e8ec4d; tmr_lvidTS=1647985982723; remixuas=NTJiM2Y2MGYyNDFjZWE5MTY1YWIwMGU1; remixdmgr_tmp=feb26a0e78f5922a3bb81378a2842351283e07d93d8aef180d2be4d0564ef7fa; remixdmgr=72668e601561e32ea5fec6b93a376338bd5866ba0829ea3258ed9adb4df025b9; remixnsid=bbb34cddeb46770636d34d36fbbc903a63afb59ebbe29b931ef811dc65aada9ac1f51992b96f15ff5be3c; remixsid=1_r6bM1W37qOA4evGj5Kjtd0YArkkyhKv5ZCoW3xOUP1dugwxE8zl2AP0lOBFsZvz8zVMwR2LUNRYuHkAymnN1LQ; tmr_reqNum=769; remixlgck=93a5673a1cad9edf37");
        httpConn.setRequestProperty("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"100\", \"Google Chrome\";v=\"100\"");
        httpConn.setRequestProperty("sec-ch-ua-mobile", "?0");
        httpConn.setRequestProperty("sec-ch-ua-platform", "\"Windows\"");
        httpConn.setRequestProperty("sec-fetch-dest", "document");
        httpConn.setRequestProperty("sec-fetch-mode", "navigate");
        httpConn.setRequestProperty("sec-fetch-site", "none");
        httpConn.setRequestProperty("sec-fetch-user", "?1");
        httpConn.setRequestProperty("upgrade-insecure-requests", "1");
        httpConn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.127 Safari/537.36");

        InputStream responseStream = null;
        try {
            responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        JSONObject obj = new JSONObject(response);
        String access_token = obj.getString("access_token");
        String email = obj.getString("email");
        Long user_id = obj.getLong("user_id");
        User user = getName(access_token, user_id);
        user.setUsername(email);
        return user;
    }

    private User getName(String access_token, Long user_id) throws IOException {
        URL url = new URL("https://api.vk.com/method/users.get");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");

        httpConn.setRequestProperty("authority", "api.vk.com");
        httpConn.setRequestProperty("accept", "*/*");
        httpConn.setRequestProperty("accept-language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
        httpConn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
        httpConn.setRequestProperty("origin", "https://dev.vk.com");
        httpConn.setRequestProperty("referer", "https://dev.vk.com/");
        httpConn.setRequestProperty("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"100\", \"Google Chrome\";v=\"100\"");
        httpConn.setRequestProperty("sec-ch-ua-mobile", "?0");
        httpConn.setRequestProperty("sec-ch-ua-platform", "\"Windows\"");
        httpConn.setRequestProperty("sec-fetch-dest", "empty");
        httpConn.setRequestProperty("sec-fetch-mode", "cors");
        httpConn.setRequestProperty("sec-fetch-site", "same-site");
        httpConn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.127 Safari/537.36");

        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
        writer.write("user_ids=" + user_id + "&access_token=" + access_token + "&v=5.131");
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        JSONObject obj = new JSONObject(response);
        System.out.println(response);
        JSONArray array = obj.getJSONArray("response");
        System.out.println(array);
        obj = array.getJSONObject(0);
        System.out.println(obj);
        String first_name = obj.getString("first_name");
        String last_name = obj.getString("last_name");
        return User.builder()
                .firstName(first_name)
                .lastName(last_name)
                .build();
    }


}
