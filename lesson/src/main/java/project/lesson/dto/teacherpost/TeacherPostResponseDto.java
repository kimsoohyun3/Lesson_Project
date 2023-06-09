package project.lesson.dto.teacherpost;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import project.lesson.entity.commonEnum.OnOrOff;
import project.lesson.entity.member.Member;
import project.lesson.entity.teacherPost.TeacherPost;
import project.lesson.entity.commonEnum.Subject;

import javax.persistence.JoinColumn;

@Data
public class TeacherPostResponseDto {

    @ApiModelProperty(example = "PK", value = "PK")
    private Long id;

    @ApiModelProperty(example = "제목", value = "제목")
    private String title;

    @ApiModelProperty(example = "내용", value = "내용")
    private String content;

    @ApiModelProperty(example = "과목", value = "과목")
    private Subject subject;

    @ApiModelProperty(example = "지역", value = "지역")
    private String area;

    @ApiModelProperty(example = "과외 온 / 오프 여부", value = "과외 온 / 오프 여부")
    private OnOrOff onOrOff;

    @ApiModelProperty(example = "회원", value = "회원")
    private Member member;

    // entity 를 dto 변환
    public TeacherPostResponseDto(TeacherPost entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.subject = entity.getSubject();
        this.area = entity.getArea();
        this.onOrOff = entity.getOnOrOff();
        this.member = entity.getMember();
    }
}
