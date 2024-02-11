package ru.chirkov.issueServiceApp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chirkov.issueServiceApp.dto.IssueDto;
import ru.chirkov.issueServiceApp.model.Issue;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    Optional<List<Issue>> getIssuesByReaderId(Long readerId);

    Optional<List<Issue>> getIssuesByBookId(Long bookId);
}