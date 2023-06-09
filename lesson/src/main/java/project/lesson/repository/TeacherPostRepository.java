package project.lesson.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.lesson.entity.member.Member;
import project.lesson.entity.teacherPost.TeacherPost;

@Repository
public interface TeacherPostRepository extends JpaRepository<TeacherPost, Long> {
	List<TeacherPost> findByMember(Member member);

}
