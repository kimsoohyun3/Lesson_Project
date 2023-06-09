package project.lesson.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.lesson.entity.studentPost.StudentPost;
import project.lesson.entity.member.Member;

@Repository
public interface StudentPostRepository extends JpaRepository<StudentPost, Long> {
	List<StudentPost> findByMember(Member member);
}
