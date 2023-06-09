package project.lesson.dto.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import project.lesson.entity.member.AgeGroup;
import project.lesson.entity.member.Career;
import project.lesson.entity.member.Gender;
import project.lesson.entity.member.Member;
import project.lesson.entity.member.UserClassification;

@Getter
public class MemberSaveResponseDto {
	@ApiModelProperty(example = "아이디", value = "아이디")
	private String id;
	@ApiModelProperty(example = "비밀번호", value = "비밀번호")
	private String password;
	@ApiModelProperty(example = "이메일", value = "이메일")
	private String email;
	@ApiModelProperty(example = "닉네임", value = "닉네임")
	private String nickname;
	@ApiModelProperty(example = "성별", value = "성별")
	private Gender gender;
	@ApiModelProperty(example = "회원구분", value = "회원구분")
	private UserClassification userClassification;
	@ApiModelProperty(example = "연령대", value = "연령대")
	private AgeGroup ageGroup;

	@ApiModelProperty(example = "경력", value = "경력")
	private Career career;

	@Builder
	public MemberSaveResponseDto(
			String id, String password, String email,
			String nickname, Gender gender,
			UserClassification userClassification,
			AgeGroup ageGroup,
			Career career
	) {
		this.id = id;
		this.password = password;
		this.email = email;
		this.nickname = nickname;
		this.gender = gender;
		this.userClassification = userClassification;
		this.ageGroup = ageGroup;
		this.career = career;
	}

	public static MemberSaveResponseDto of(Member member) {
		return MemberSaveResponseDto.builder()
				.id(member.getId())
				.password(member.getPassword())
				.email(member.getEmail())
				.nickname(member.getNickname())
				.gender(member.getGender())
				.userClassification(member.getUserClassification())
				.ageGroup(member.getAgeGroup())
				.career(member.getCareer())
				.build();
	}
}
