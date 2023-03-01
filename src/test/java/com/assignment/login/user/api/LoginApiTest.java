package com.assignment.login.user.api;

import com.assignment.login.user.dto.PublishAuthenticatePhoneDto;
import com.assignment.login.user.dto.RegisterDto;
import com.assignment.login.user.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = LoginApi.class)
class LoginApiTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("회원가입할 때 이메일을 누락한다면 HTTP status code 400과 에러 메시지를 리턴한다.")
    void request_register_test1() throws Exception {
        //given
        RegisterDto registerDto = RegisterDto.builder()
                .name("남궁권")
                .phone("01080127226")
                .password("123123")
                .nickname("남궁원빈")
                .build();
        doNothing().when(loginService).register(any());

        //when
        String content = objectMapper.writeValueAsString(registerDto);
        ResultActions actions = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..status").hasJsonPath())
                .andExpect(jsonPath("$..message").hasJsonPath())
                .andReturn().getResponse().getContentAsString().contains("[이메일]은 필수값입니다.");
    }

    @Test
    @DisplayName("회원가입할 때 이메일 형식이 올바르지 않다면 HTTP status code 400과 에러 메시지를 리턴한다.")
    void request_register_test2() throws Exception {
        //given
        RegisterDto registerDto = RegisterDto.builder()
                .email("rndrnjs")
                .name("남궁권")
                .phone("01080127226")
                .password("123123")
                .nickname("남궁원빈")
                .build();
        doNothing().when(loginService).register(any());

        //when
        String content = objectMapper.writeValueAsString(registerDto);
        ResultActions actions = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..status").hasJsonPath())
                .andExpect(jsonPath("$..message").hasJsonPath())
                .andReturn().getResponse().getContentAsString().contains("올바른 이메일 형식이 아닙니다.");
    }

    @Test
    @DisplayName("회원가입할 때 비밀번호를 누락한다면 HTTP status code 400과 에러 메시지를 리턴한다.")
    void request_register_test3() throws Exception {
        //given
        RegisterDto registerDto = RegisterDto.builder()
                .email("rndrnjs2003@naver.com")
                .name("남궁권")
                .phone("01080127226")
                .nickname("남궁원빈")
                .build();
        doNothing().when(loginService).register(any());

        //when
        String content = objectMapper.writeValueAsString(registerDto);
        ResultActions actions = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..status").hasJsonPath())
                .andExpect(jsonPath("$..message").hasJsonPath())
                .andReturn().getResponse().getContentAsString().contains("[비밀번호]는 필수값입니다.");
    }

    @Test
    @DisplayName("회원가입할 때 사용자명을 누락한다면 HTTP status code 400과 에러 메시지를 리턴한다.")
    void request_register_test4() throws Exception {
        //given
        RegisterDto registerDto = RegisterDto.builder()
                .email("rndrnjs2003@naver.com")
                .password("123123")
                .phone("01080127226")
                .nickname("남궁원빈")
                .build();
        doNothing().when(loginService).register(any());

        //when
        String content = objectMapper.writeValueAsString(registerDto);
        ResultActions actions = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..status").hasJsonPath())
                .andExpect(jsonPath("$..message").hasJsonPath())
                .andReturn().getResponse().getContentAsString().contains("[사용자명]은 필수값입니다.");
    }

    @Test
    @DisplayName("회원가입할 때 핸드폰 번호를 누락한다면 HTTP status code 400과 에러 메시지를 리턴한다.")
    void request_register_test5() throws Exception {
        //given
        RegisterDto registerDto = RegisterDto.builder()
                .email("rndrnjs2003@naver.com")
                .password("123123")
                .name("남궁")
                .nickname("남궁원빈")
                .build();
        doNothing().when(loginService).register(any());

        //when
        String content = objectMapper.writeValueAsString(registerDto);
        ResultActions actions = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..status").hasJsonPath())
                .andExpect(jsonPath("$..message").hasJsonPath())
                .andReturn().getResponse().getContentAsString().contains("[핸드폰번호]는 필수값입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"010882313", "1010110", "013-0110-1010", "019-1010-2020", "02-364-4243"})
    @DisplayName("회원가입할 때 핸드폰 번호가 전화번호 형태가 아니라면 HTTP status code 400과 에러 메시지를 리턴한다.")
    void request_register_test6(String phoneNumber) throws Exception {
        //given
        RegisterDto registerDto = RegisterDto.builder()
                .email("rndrnjs2003@naver.com")
                .password("123123")
                .name("남궁권")
                .nickname("남궁원빈")
                .phone(phoneNumber)
                .build();
        doNothing().when(loginService).register(any());

        //when
        String content = objectMapper.writeValueAsString(registerDto);
        ResultActions actions = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..status").hasJsonPath())
                .andExpect(jsonPath("$..message").hasJsonPath())
                .andReturn().getResponse().getContentAsString().contains("올바른 전화번호 형식이 아닙니다. 010-1234-1234와 같은 형식으로 입력해 주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"010-8012-7226", "01080127226"})
    @DisplayName("회원가입할 때 HTTP status code 200을 리턴한다.")
    void request_register_test7(String phoneNumber) throws Exception {
        //given
        RegisterDto registerDto = RegisterDto.builder()
                .email("rndrnjs2003@naver.com")
                .password("123123")
                .name("남궁권")
                .nickname("남궁원빈")
                .phone(phoneNumber)
                .build();
        doNothing().when(loginService).register(any());

        //when
        String content = objectMapper.writeValueAsString(registerDto);
        ResultActions actions = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        actions.andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"010-8012-7226", "01080127226"})
    @DisplayName("번호 인증할 때 HTTP status code 200을 리턴한다.")
    void request_publishAuthenticatePhone_test1(String phoneNumber) throws Exception {
        //given
        PublishAuthenticatePhoneDto phoneDto = new PublishAuthenticatePhoneDto(phoneNumber);
        String authenticateNumber = "123456";
        given(loginService.publishAuthenticatePhone(any())).willReturn(authenticateNumber);

        //when
        String content = objectMapper.writeValueAsString(phoneDto);
        ResultActions actions = mockMvc.perform(post("/api/v1/auth/publish/authenticate/number")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        actions.andExpect(status().isOk());
    }
}
