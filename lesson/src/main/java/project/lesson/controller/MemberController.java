package project.lesson.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import project.lesson.dto.member.MemberInfoResponseDto;
import project.lesson.dto.member.MemberSaveRequestDto;
import project.lesson.dto.member.MemberSaveResponseDto;
import project.lesson.dto.member.ModifyMemberPasswordRequestDto;
import project.lesson.dto.signin.OAuthKakaoSignInRequestDto;
import project.lesson.service.AuthMailService;
import project.lesson.service.MemberService;
import project.lesson.service.TokenProvider;

@Api(tags = {"회원 관련 API"})
@RestController
public class MemberController {
	private final MemberService memberService;
	private final TokenProvider tokenProvider;
	private final AuthMailService authMailService;

	@Autowired
	public MemberController(
		MemberService memberService,
		TokenProvider tokenProvider,
		AuthMailService authMailService
	) {
		this.memberService = memberService;
		this.tokenProvider = tokenProvider;
		this.authMailService = authMailService;
	}

	@ApiOperation(
		value = "회원가입",
		notes = "회원가입을 진행합니다."
	)
	@ApiResponses(
		{
			@ApiResponse(code = 200, message = "회원가입", response = MemberSaveResponseDto.class)
		}
	)
	@PostMapping("/member/join")
	public ResponseEntity<MemberSaveResponseDto> joinMember(
		@RequestBody @Valid MemberSaveRequestDto memberSaveRequestDto
	) {
		return ResponseEntity.ok().body(memberService.joinMember(memberSaveRequestDto));
	}

	@ApiOperation(
		value = "내정보 불러오기",
		notes = "내정보를 불러옵니다."
	)
	@ApiResponses(
		{
			@ApiResponse(code = 200, message = "내정보", response = MemberInfoResponseDto.class)
		}
	)
	@GetMapping("/member/info")
	public ResponseEntity<MemberInfoResponseDto> findMemberInfo(
		@RequestHeader("Authorization") String token) {
		return ResponseEntity.ok()
			.body(memberService.findMemberInfo(tokenProvider.validateAndGetUserId(token.substring(7))));
	}

	@ApiOperation(
		value = "아이디로 내정보 불러오기",
		notes = "아이디로 내정보를 불러옵니다."
	)
	@ApiResponses(
		{
			@ApiResponse(code = 200, message = "내정보", response = MemberInfoResponseDto.class)
		}
	)
	@GetMapping("/member/info/{memberId}")
	public ResponseEntity<MemberInfoResponseDto> findMemberInfoByMemberId(
		@PathVariable String memberId) {
		return ResponseEntity.ok()
			.body(memberService.findMemberInfo(memberId));
	}

	@ApiOperation(
		value = "비밀번호 변경",
		notes = "비밀번호를 변경합니다."
	)
	@ApiResponses(
		{
			@ApiResponse(code = 200, message = "변경된 사용자 아이디", response = String.class)
		}
	)
	@PutMapping("member/modify-password")
	public String modifyMemberPassword(
		@RequestBody @Valid ModifyMemberPasswordRequestDto modifyMemberPasswordRequestDto
	) {
		return memberService.modifyMemberPassword(modifyMemberPasswordRequestDto.getMemberId(),
			modifyMemberPasswordRequestDto);
	}

	@ApiOperation(
		value = "카카오 소셜로그인 후 닉네임,유저구분,나이대,커리어 정보 설정",
		notes = "카카오 소셜로그인 후 닉네임,유저구분,나이대,커리어 정보 설정합니다."
	)
	@ApiResponses(
		{
			@ApiResponse(code = 200, message = "변경된 사용자 아이디", response = String.class)
		}
	)
	@PutMapping("oauth/member/modify-member-info")
	public String modifyMemberInfoAfterOauthKakaoSignIn(
		@RequestHeader("Authorization") String token,
		@RequestBody @Valid OAuthKakaoSignInRequestDto oAuthKakaoSignInRequestDto
	) {
		return memberService.modifyMemberInfoAfterOauthKakaoSignIn(
			tokenProvider.validateAndGetUserId(token.substring(7)),
			oAuthKakaoSignInRequestDto);
	}

	@ApiOperation(
		value = "이메일로 아이디를 찾습니다.",
		notes = "이메일로 아이디를 찾습니다."
	)
	@ApiResponses(
		{
			@ApiResponse(code = 200, message = "반환값 없음")
		}
	)
	@GetMapping("/member/find/{email}")
	public void findMemberIdByEmail(@PathVariable @Email(message = "올바른 이메일 형식이 아닙니다.") String email) {
		try {
			authMailService.sendFindMemberIdMail(email);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
